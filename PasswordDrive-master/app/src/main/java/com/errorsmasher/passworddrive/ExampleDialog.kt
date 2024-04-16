package com.errorsmasher.passworddrive

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment


class ExampleDialog : AppCompatDialogFragment() {
    private var editTextUsername: EditText? = null
    private var editTextPassword: EditText? = null
    private var listener: ExampleDialogListener? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_dialog, null)
        builder.setView(view)
            .setTitle("Details")
            .setNegativeButton("cancel",
                DialogInterface.OnClickListener { dialogInterface, i -> })
            .setPositiveButton("ok",
                DialogInterface.OnClickListener { dialogInterface, i ->
                    val username = editTextUsername!!.text.toString()
                    val password = editTextPassword!!.text.toString()
                    listener!!.applyTexts(username, password)
                })
        editTextUsername = view.findViewById(R.id.edit_username)
        editTextPassword = view.findViewById(R.id.edit_password)
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as ExampleDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                context.toString() +
                        "must implement ExampleDialogListener"
            )
        }
    }

    interface ExampleDialogListener {
        fun applyTexts(username: String?, password: String?)
    }
}