package com.errorsmasher.passworddrive

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Update_dialog(val database: PasswordDatabase, val itemData: Password) :
    AppCompatDialogFragment() {
    private var editTextUsername: EditText? = null
    private var editTextPassword: EditText? = null

    //    private var listener: ExampleDialogListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_dialog, null)
        val editTextUserName = view.findViewById<EditText>(R.id.edit_username)
        editTextUserName.setText(itemData.name)
        val editTextPass = view.findViewById<EditText>(R.id.edit_password)
        editTextPass.setText(itemData.pass)
        builder.setView(view)
            .setTitle("Details")
            .setNegativeButton("Delete",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    GlobalScope.launch {

                        database.contactDao()
                            .deletContact(Password(itemData.id, itemData.name, itemData.pass))
                    }
                    Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show()


                })
            .setPositiveButton("Update",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    val username = editTextUsername!!.text.toString()
                    val password = editTextPassword!!.text.toString()
                    GlobalScope.launch {
                        database.contactDao()
                            .updateContact(
                                Password(
                                    itemData.id,
                                    username,
                                    password
                                )
                            )
                    }
//                    listener!!.applyTexts(username, password)
                })
        editTextUsername = view.findViewById(R.id.edit_username)
        editTextPassword = view.findViewById(R.id.edit_password)
        return builder.create()
    }
}//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        listener = try {
//            context as ExampleDialogListener
//        } catch (e: ClassCastException) {
//            throw ClassCastException(
//                context.toString() +
//                        "must implement ExampleDialogListener"
//            )
//        }
//    }
//
//    interface ExampleDialogListener {
//        fun applyTexts(username: String?, password: String?)
//    }
//}