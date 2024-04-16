package com.errorsmasher.passworddrive

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class PasswordAdapter(val database: PasswordDatabase, val fragmentManager: FragmentManager) :
    RecyclerView.Adapter<PasswordAdapter.ViewHolder>() {
    var mList: List<Password> = arrayListOf()
    val mSelectedList = mutableListOf<Password>()
    lateinit var context: Context
    var isSelected = false
    lateinit var onClick: OnClick
    lateinit var updateDialog: Update_dialog
    //  lateinit var fragmentManager: FragmentManager


    fun initOnClick(onClick: OnClick) {
        this.onClick = onClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card, parent, false)
        context = view.context

        return ViewHolder(view)
    }
//    fun setFragmentManager(fragmentManager:FragmentManager){
//        fragmentManager = fragmentManager.this
//
//    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(mList: List<Password>) {
        this.mList = mList
        notifyDataSetChanged()
    }


    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = mList[position]
        holder.tvName.text = itemData.name
        holder.tvPass.text = itemData.pass
//        holder.itemView.setOnClickListener {
//            Toast.makeText(context, "Edit Dialog", Toast.LENGTH_SHORT).show()
//
//           // saveData(database, itemData)
//        }
        holder.itemView.setOnClickListener {
//            if (isSelected) {
//                if (mSelectedList.contains(itemData)) {
//                    mSelectedList.remove(itemData)
//                    onClick.onSelect(mSelectedList)
//                    holder.imgPro.setImageResource(R.drawable.icon)
//                    if (mSelectedList.isEmpty()) {
//                        isSelected = false
//                        Toast.makeText(context, "Select mode off", Toast.LENGTH_SHORT).show()
//                    }
//                    Toast.makeText(context, mSelectedList.toString(), Toast.LENGTH_SHORT).show()
//                } else {
//                    holder.imgPro.setImageResource(R.drawable.ic_baseline_check_24)
//                    mSelectedList.add(itemData)
//                    onClick.onSelect(mSelectedList)
//                    Toast.makeText(context, mSelectedList.toString(), Toast.LENGTH_SHORT).show()
//
//                }
//            } else {
                updateDialog = Update_dialog(database, itemData);
                updateDialog.show(fragmentManager, "Update")
//            }
        }
//        holder.itemView.setOnLongClickListener {
//            if (!isSelected) {
//                isSelected = true
//                Toast.makeText(context, "Select mode on", Toast.LENGTH_SHORT).show()
//                // onClick.onSelect(true)
//            }
//            true
//
//        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_user_name)
        val tvPass: TextView = itemView.findViewById(R.id.tv_user_pass)
       // val imgPro: ImageView = itemView.findViewById(R.id.imageView)
    }

//
//    @SuppressLint("SetTextI18n")
//    @OptIn(DelicateCoroutinesApi::class)
//    private fun saveData(database: PasswordDatabase, itemData: Password) {
//        val dialog = Dialog(context)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCancelable(false)
//        dialog.setContentView(R.layout.custom_layout)
//        val name = dialog.findViewById(R.id.name) as EditText
//        val pass = dialog.findViewById(R.id.pass) as EditText
//        val submit = dialog.findViewById(R.id.sumb) as Button
//        val cnsl = dialog.findViewById(R.id.cnsl) as Button
//        cnsl.text = "Delete"
//        dialog.setCanceledOnTouchOutside(true);
//        submit.setOnClickListener {
//            GlobalScope.launch {
//                database.contactDao()
//                    .updateContact(
//                        Password(
//                            itemData.id,
//                            name.text.toString(),
//                            pass.text.toString()
//                        )
//                    )
//            }
//            dialog.dismiss()
//        }
//
//        cnsl.setOnClickListener {
//            GlobalScope.launch {
//
//                database.contactDao()
//                    .deletContact(Password(itemData.id, itemData.name, itemData.pass))
//            }
//            Toast.makeText(context, "Delete Success", Toast.LENGTH_SHORT).show()
//            dialog.dismiss()
//        }
//        dialog.show()
//
//    }
}
