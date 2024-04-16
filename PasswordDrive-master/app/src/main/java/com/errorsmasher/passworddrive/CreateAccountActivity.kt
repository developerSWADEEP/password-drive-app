package com.errorsmasher.passworddrive

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.errorsmasher.passworddrive.utils.Prefs
import com.errorsmasher.passworddrive.utils.from


class CreateAccountActivity : AppCompatActivity() {
    var newPss: EditText? = null
    var btSave: Button? = null
    var cnfPass: EditText? = null
    var etTitle : TextView? = null
    var llSkip : LinearLayout? = null
    var linkedin: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        newPss = findViewById(R.id.new_pass)
        cnfPass = findViewById(R.id.confi_pass)
        etTitle = findViewById(R.id.signincv)
        llSkip = findViewById(R.id.skip)
        linkedin = findViewById<View>(R.id.ldinn) as ImageView


        btSave = findViewById(R.id.save)
        Prefs.init(this)
        if (from == "Splash") {
            if (Prefs.getString("sPassword", "null") != "null") {
                val intent = Intent(this@CreateAccountActivity, LoginActivity::class.java)
                finish()
                startActivity(intent)

            }
        }else if(from == "Main"){
            btSave?.setText("Save")
            etTitle?.setText("Change Password")
        }
        btSave?.setOnClickListener {
            val newPass = newPss!!.text.toString()
            val cnfPass = cnfPass!!.text.toString()
            if (newPass.isEmpty() && cnfPass.isEmpty()) {
                newPss!!.error = "Password Empty"
                newPss!!.error = "Password Empty"
            } else {
                if (newPass == cnfPass) {
                    if (newPass.length >= 5) {
                        confirmDialog {
                            Prefs.setString("sPassword", newPass)
                            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                            openACtivity()
                        }

                    } else {
                        Toast.makeText(this, "Password should greater than 5", Toast.LENGTH_SHORT)
                            .show()

                    }
                } else {
                    Toast.makeText(this, "Password not matched", Toast.LENGTH_SHORT).show()

                }

            }
        }
        linkedin!!.setOnClickListener {
            openLinkedInProfile()
        }
        llSkip!!.setOnClickListener {
            openACtivity()
        }
    }

    fun openACtivity() {
        val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
        finish()
        startActivity(intent)
    }
    @SuppressLint("QueryPermissionsNeeded")
    private fun openLinkedInProfile() {
        val linkedinId =
            "4a71a3206" // Replace with the LinkedIn profile ID or URL you want to open
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://profile/$linkedinId"))
        // If the LinkedIn app is not installed, open the LinkedIn website instead
        if (intent.resolveActivity(packageManager) == null) {
            intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://www.linkedin.com/in/rahul-tanwar-4a71a3206")
            )
        }
        startActivity(intent)
    }

    fun confirmDialog(savePass: () -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("We take security very seriously and therefore do not have access to your password. For this reason, we are unable to retrieve or reset your password. Please ensure that you keep your password safe and secure.")
            .setTitle("Alert!")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                savePass()
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
                // User cancelled the dialog
            })
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}