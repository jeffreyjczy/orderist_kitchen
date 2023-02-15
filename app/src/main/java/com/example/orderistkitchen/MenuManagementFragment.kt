package com.example.orderistkitchen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.dialog_addmenu.*
import kotlinx.android.synthetic.main.fragment_menu_management.*
import kotlinx.android.synthetic.main.fragment_menu_management.view.*
import kotlinx.android.synthetic.main.row_menu_management.view.*


class MenuManagementFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val menuList = listOf(
            Menu("Chicken Fried Rice"),
            Menu("Tomyam Kung"),
            Menu("SomTam Thai"),
            Menu("Masala Chai"),
            Menu("Kulche"),
            Menu("Dhoka"),
            Menu("Jalebi"),
            Menu("Pizza Hawaiian")
        )

        val t = inflater.inflate(R.layout.fragment_menu_management, container, false)

        val menuManagementView = t.findViewById<RecyclerView>(R.id.menuManagementView)
        menuManagementView.layoutManager = LinearLayoutManager(context)
        menuManagementView.adapter = MenuManagementAdapter(menuList)

        val addMenuBtn = t.findViewById<Button>(R.id.addMenuBtn)
        addMenuBtn.setOnClickListener {
            // pop up dialog
            val dialogBinding = layoutInflater.inflate(R.layout.dialog_addmenu,null)
            val orderDialog = Dialog(requireContext())

//            orderDialog.setContentView(dialogBinding)

            orderDialog.setContentView(R.layout.dialog_addmenu)
            orderDialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
            orderDialog.setCancelable(true)
            orderDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            orderDialog.show()

            val confirmAddButton = dialogBinding.findViewById<Button>(R.id.confirmAddButton)

            confirmAddButton.setOnClickListener {
                println("hello")
            }

        }


        return t
    }

    inner class MenuManagementHolder(view: View): RecyclerView.ViewHolder(view){
        val menuText = itemView.menuText
        val toggleShowBtn = itemView.toggleShowBtn
        val deleteBtn = itemView.deleteBtn

    }
    inner class MenuManagementAdapter (var menuList: List<Menu>): RecyclerView.Adapter<MenuManagementHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuManagementHolder {
            val view = layoutInflater.inflate(R.layout.row_menu_management, parent, false)
            return MenuManagementHolder(view)
        }
        override fun onBindViewHolder(holder: MenuManagementHolder, position: Int) {

//            holder.orderText.text = "Order Table " + orderList[position].tableNo.toString()
            holder.menuText.text = menuList[position].name

            if (menuList[position].isVisible) {
                holder.toggleShowBtn.setImageResource(R.drawable.show)
            } else {
                holder.toggleShowBtn.setImageResource(R.drawable.hide)
            }

            holder.toggleShowBtn.setOnClickListener {
                menuList[position].isVisible = !menuList[position].isVisible
                if (menuList[position].isVisible) {
                    holder.toggleShowBtn.setImageResource(R.drawable.show)
                } else {
                    holder.toggleShowBtn.setImageResource(R.drawable.hide)
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


                orderDialog.setContentView(R.layout.dialog_confirmation)
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
                    println("Complete")
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