package com.example.orderistkitchen

import android.app.Dialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_menu_management.*
import kotlinx.android.synthetic.main.row_menu_management.view.*


class MenuManagementFragment : Fragment() {

    val database = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Menus")
        val t = inflater.inflate(R.layout.fragment_menu_management, container, false)
        val menuList = ArrayList<Menu>()
        val menuManagementView = t.findViewById<RecyclerView>(R.id.menuManagementView)

        // Set up the RecyclerView adapter
        val adapter = MenuManagementAdapter(menuList)
        menuManagementView.layoutManager = LinearLayoutManager(context)
        menuManagementView.adapter = adapter


        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear the current list of menus
                menuList.clear()

                // Get list of Menu objects from the dataSnapshot
                for (childSnapshot in dataSnapshot.children) {
                    val menu = childSnapshot.getValue(Menu::class.java)
                    menu?.id = childSnapshot.key
                    menuList.add(menu!!)
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })


        val addMenuBtn = t.findViewById<Button>(R.id.addMenuBtn)
        addMenuBtn.setOnClickListener {
            // pop up dialog
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_addmenu,null)
            val orderDialog = Dialog(requireContext())

            orderDialog.setContentView(R.layout.dialog_addmenu)
            orderDialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            orderDialog.setCancelable(true)
            orderDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            orderDialog.show()

            val confirmAddButton = orderDialog.findViewById<Button>(R.id.confirmAddButton)
            val newMenuText = orderDialog.findViewById<TextView>(R.id.newMenuText)


            confirmAddButton.setOnClickListener {
                val name = newMenuText.text.toString()


                val myRef = database.getReference("Menus")
                val newMenuRef = myRef.push() // generate a new key for the new menu
                val newMenu = Menu(name,true)
                newMenuRef.setValue(newMenu)
//                menuList.add(newMenu)
                orderDialog.dismiss()


            }

        }


        return t
    }

    inner class MenuManagementHolder(view: View): RecyclerView.ViewHolder(view){
        val menuText = itemView.menuText
        val toggleShowBtn = itemView.toggleShowBtn
        val deleteBtn = itemView.deleteBtn

    }
    inner class MenuManagementAdapter (var menuList: ArrayList<Menu>): RecyclerView.Adapter<MenuManagementHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuManagementHolder {
            val view = layoutInflater.inflate(R.layout.row_menu_management, parent, false)
            return MenuManagementHolder(view)
        }


        override fun onBindViewHolder(holder: MenuManagementHolder, position: Int) {

//            holder.orderText.text = "Order Table " + orderList[position].tableNo.toString()
            holder.menuText.text = menuList[position].name

            if (menuList[position].isVisible == true) {
                holder.toggleShowBtn.setImageResource(R.drawable.show)
            } else {
                holder.toggleShowBtn.setImageResource(R.drawable.hide)
            }

            holder.toggleShowBtn.setOnClickListener {
                menuList[position].isVisible = !menuList[position].isVisible!!
                if (menuList[position].isVisible == true) {
                    holder.toggleShowBtn.setImageResource(R.drawable.show)
                    database.getReference("Menus/${menuList[position].id}/visible").setValue(true)

                } else {
                    holder.toggleShowBtn.setImageResource(R.drawable.hide)
                    database.getReference("Menus/${menuList[position].id}/visible").setValue(false)

                }
            }

            holder.deleteBtn.setOnClickListener {
                // pop up dialog
                val dialogBinding = layoutInflater.inflate(R.layout.dialog_confirmation,null)
                val orderDialog = Dialog(requireContext())

                val confirmationTitle = dialogBinding.findViewById<TextView>(R.id.confirmationTitle)
                val confirmationDescription = dialogBinding.findViewById<TextView>(R.id.confirmationDescription)

                confirmationTitle.text = "Delete Menu?"
                confirmationDescription.text = "Are you sure you want to delete " + menuList[position].name + "?"

                orderDialog.setContentView(dialogBinding)
                orderDialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                orderDialog.setCancelable(true)
                orderDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                orderDialog.show()

                val dismissButton = dialogBinding.findViewById<Button>(R.id.dismissButton)
                dismissButton.setOnClickListener {
                    orderDialog.dismiss()
                }
                val confirmButton = dialogBinding.findViewById<Button>(R.id.confirmAddButton)
                confirmButton.setOnClickListener {

                    val myRef = database.getReference("Menus/${menuList[position].id}")
                    myRef.removeValue()
//                    menuList.removeAt(position)
                    println("Complete")
                    orderDialog.dismiss()

                }
            }




        }
        override fun getItemCount(): Int {
            return menuList.size
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}