package com.example.orderistkitchen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.dialog_confirmation.*
import kotlinx.android.synthetic.main.row_eachorder.view.*
import kotlinx.android.synthetic.main.row_eachorder1.view.*
import kotlinx.android.synthetic.main.row_receivedorders.view.eachOrderQuantity


class EachOrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val t = inflater.inflate(R.layout.fragment_each_order, container, false)

        val order = arguments?.getParcelable<Orders>("order")
//        println(order)

//        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
//        val jsonTutPretty: String = gsonPretty.toJson(order)
//        println(jsonTutPretty)

        val eachOrderText = t.findViewById<TextView>(R.id.eachOrderText)
        if (order != null) {
            eachOrderText.text = "Order Table " + order.tableNo.toString()
        }


        if (order != null) {


            // Inflate the layout for this fragment
            val receivedOrdersView = t.findViewById<RecyclerView>(R.id.eachOrderView)
            receivedOrdersView.layoutManager = LinearLayoutManager(context)
            val database = FirebaseDatabase.getInstance()
            val myRef = database.getReference("Orders/${order.id}/orders")
            val foodList = ArrayList<Food?>()

            // Set up the RecyclerView adapter
            val adapter = EachOrdersAdapter(foodList)
            receivedOrdersView.adapter = adapter

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    // Clear the current list of menus
                    foodList.clear()

                    // Get list of Menu objects from the dataSnapshot
                    for (childSnapshot in dataSnapshot.children) {
                        val food = childSnapshot.getValue(Food::class.java)
                        food?.id = childSnapshot.key.toString()
                        foodList.add(food!!)
                    }

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged()
                }


                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                }
            })


        }


        return t
    }


    inner class EachOrdersHolder(view: View): RecyclerView.ViewHolder(view){

        val eachOrderQuantity = itemView.eachOrderQuantity
        val eachOrderMenu = itemView.eachOrderMenu
        val completeAct = itemView.eachOrderAction1
        val rejectAct = itemView.eachOrderAction2

    }

    inner class EachOrdersHolder1(view: View): RecyclerView.ViewHolder(view){

        val eachOrderQuantity1 = itemView.eachOrderQuantity1
        val eachOrderMenu1 = itemView.eachOrderMenu1
        val eachOrderStatus1 = itemView.eachOrderStatus1

    }


    inner class EachOrdersAdapter(var orderList: ArrayList<Food?>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//            val view = layoutInflater.inflate(R.layout.row_eachorder, parent, false)
//            return EachOrdersHolder(view)
            return when (viewType) {
                0 -> {
                    val view = layoutInflater.inflate(R.layout.row_eachorder, parent, false)
                    EachOrdersHolder(view)
                }
                else -> {
                    val view = layoutInflater.inflate(R.layout.row_eachorder1, parent, false)
                    EachOrdersHolder1(view)
                }
            }

        }
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is EachOrdersHolder -> {
                    val database = FirebaseDatabase.getInstance()
                    val order = arguments?.getParcelable<Orders>("order")

                    holder.eachOrderQuantity.text = orderList[position]!!.quantity.toString()
                    holder.eachOrderMenu.text = orderList[position]!!.name

                    holder.completeAct.setOnClickListener{
                        // pop up dialog
                        val dialogBinding = layoutInflater.inflate(R.layout.dialog_confirmation,null)
                        val orderDialog = Dialog(requireContext())


                        orderDialog.setContentView(R.layout.dialog_confirmation)
                        orderDialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                        orderDialog.setCancelable(true)
                        orderDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                        val confirmationTitle = orderDialog.confirmationTitle
                        val confirmationDescription = orderDialog.confirmationDescription

                        confirmationTitle.text = "Menu Complete?"
                        confirmationDescription.text = "Are you sure the " + orderList[position]!!.name + " is completed?"


                        orderDialog.show()

                        val dismissButton = orderDialog.dismissButton
                        dismissButton.setOnClickListener {
                            orderDialog.dismiss()
                        }
                        val confirmButton = orderDialog.confirmAddButton
                        confirmButton.setOnClickListener {
                            println("Complete")
                            if (order != null) {
                                database.getReference("Orders/${order.id}/orders/${position}/status").setValue("Ready to Serve")
                                orderDialog.dismiss()
                            }
                        }


                    }

                    holder.rejectAct.setOnClickListener {
                        val database = FirebaseDatabase.getInstance()
                        val order = arguments?.getParcelable<Orders>("order")
                        // pop up dialog
                        val dialogBinding = layoutInflater.inflate(R.layout.dialog_confirmation,null)
                        val orderDialog = Dialog(requireContext())

                        val confirmationTitle = dialogBinding.findViewById<TextView>(R.id.confirmationTitle)
                        val confirmationDescription = dialogBinding.findViewById<TextView>(R.id.confirmationDescription)

                        confirmationTitle.text = "Reject Menu"
                        confirmationDescription.text = "Are you sure you want to reject " + orderList[position]!!.name + "?"


                        orderDialog.setContentView(R.layout.dialog_confirmation)
                        orderDialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
                        orderDialog.setCancelable(true)
                        orderDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        orderDialog.show()

                        val dismissButton = orderDialog.dismissButton
                        dismissButton.setOnClickListener {
                            orderDialog.dismiss()
                        }
                        val confirmButton = orderDialog.confirmAddButton
                        confirmButton.setOnClickListener {
                            println("Complete")
                            if (order != null) {
                                database.getReference("Orders/${order.id}/orders/${position}/status").setValue("Rejected")
                                orderDialog.dismiss()
                            }
                        }
                    }
                }
                is EachOrdersHolder1 -> {
                    holder.eachOrderQuantity1.text = orderList[position]!!.quantity.toString()
                    holder.eachOrderMenu1.text = orderList[position]!!.name
                    holder.eachOrderStatus1.text = orderList[position]!!.status
                }
            }


        }
        override fun getItemViewType(position: Int): Int {
            return if (orderList[position]?.status == "Pending") 0 else 1
        }
        override fun getItemCount(): Int {
            return orderList.size
        }
    }


}
