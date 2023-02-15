package com.example.orderistkitchen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.row_eachorder.view.*
import kotlinx.android.synthetic.main.row_receivedorders.view.eachOrderQuantity


class EachOrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val t = inflater.inflate(R.layout.fragment_each_order, container, false)

        val order = arguments?.getParcelable<Orders>("order")

        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val jsonTutPretty: String = gsonPretty.toJson(order)
        println(jsonTutPretty)

        val eachOrderText = t.findViewById<TextView>(R.id.eachOrderText)
        if (order != null) {
            eachOrderText.text = "Order Table " + order.tableNo.toString()

        }

        val receivedOrdersView = t.findViewById<RecyclerView>(R.id.eachOrderView)
        receivedOrdersView.layoutManager = LinearLayoutManager(context)
        if (order != null) {
            receivedOrdersView.adapter = EachOrdersAdapter(order.orders)
        }


        return t
    }


    inner class EachOrdersHolder(view: View): RecyclerView.ViewHolder(view){

        val eachOrderQuantity = itemView.eachOrderQuantity
        val eachOrderMenu = itemView.eachOrderMenu
        val completeAct = itemView.eachOrderAction1
        val rejectAct = itemView.eachOrderAction2


    }
    inner class EachOrdersAdapter (var orderList: List<Food>): RecyclerView.Adapter<EachOrdersHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EachOrdersHolder {
            val view = layoutInflater.inflate(R.layout.row_eachorder, parent, false)
            return EachOrdersHolder(view)
        }
        override fun onBindViewHolder(holder: EachOrdersHolder, position: Int) {

            holder.eachOrderQuantity.text = orderList[position].quantity.toString()
            holder.eachOrderMenu.text = orderList[position].name

            holder.completeAct.setOnClickListener{
                // pop up dialog
                val dialogBinding = layoutInflater.inflate(R.layout.dialog_confirmation,null)
                val orderDialog = Dialog(requireContext())

                val confirmationTitle = dialogBinding.findViewById<TextView>(R.id.confirmationTitle)
                val confirmationDescription = dialogBinding.findViewById<TextView>(R.id.confirmationDescription)

                confirmationTitle.text = "Menu Complete?"
                confirmationDescription.text = "Are you sure the " + orderList[position].name + " is completed?"

                orderDialog.setContentView(dialogBinding)
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

            holder.rejectAct.setOnClickListener {

                // pop up dialog
                val dialogBinding = layoutInflater.inflate(R.layout.dialog_confirmation,null)
                val orderDialog = Dialog(requireContext())

                val confirmationTitle = dialogBinding.findViewById<TextView>(R.id.confirmationTitle)
                val confirmationDescription = dialogBinding.findViewById<TextView>(R.id.confirmationDescription)

                confirmationTitle.text = "Reject Menu"
                confirmationDescription.text = "Are you sure you want to reject " + orderList[position].name + "?"

                orderDialog.setContentView(dialogBinding)
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
            return orderList.size
        }
    }


}
