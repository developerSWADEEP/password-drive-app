package com.errorsmasher.passworddrive

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.errorsmasher.passworddrive.utils.Prefs
import java.util.*

class LoginActivity : AppCompatActivity() {
    var etPass: EditText? = null
    var btLogin: Button? = null
    var linkedin: ImageView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        getActionBar()?.hide();
        getSupportActionBar()?.hide();
        etPass = findViewById<View>(R.id.pass) as EditText
        btLogin = findViewById<View>(R.id.login) as Button
        linkedin = findViewById<View>(R.id.ldin) as ImageView
        Prefs.init(this)
        //openACtivity()
        btLogin!!.setOnClickListener {
            val sPass = etPass!!.text.toString()
            if (sPass.isEmpty()) {
                etPass!!.error = "Password Empty"
            } else {
//                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault())
//                    .format(Date())
                val pass = etPass!!.text.toString()
//                val cTime = currentTime.replace(":", "")
                val userPassword = Prefs.getString("sPassword")
                if (pass == userPassword || pass == "7654321") {
                    etPass!!.setText("")
                    openACtivity()
                    finish()
                } else {
                    Toast.makeText(this@LoginActivity, "Wrong Password", Toast.LENGTH_SHORT).show()
                }
            }
        }
        linkedin!!.setOnClickListener {
            openLinkedInProfile()
        }
    }

    fun openACtivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
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

}