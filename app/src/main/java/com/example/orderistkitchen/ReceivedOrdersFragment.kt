package com.example.orderistkitchen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.row_receivedorders.view.*


class ReceivedOrdersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val database = FirebaseDatabase.getInstance()
        val myOrdersRef = database.getReference("Orders")
        val myMenuRef = database.getReference("Menus")

        val t = inflater.inflate(R.layout.fragment_received_orders, container, false)

        // History table
        val orderList = mutableListOf <Orders>()
//        orderList.add(Orders(1, arrayListOf(Food("Chicken Fried Rice", 1))))
//        orderList.add(Orders(2, arrayListOf(Food("Beef Noodle Soup", 2), Food("Egg Fried Rice", 1))))
//        orderList.add(Orders(3, arrayListOf(Food("Vegetable Stir Fry", 3), Food("Steamed Rice", 1),Food("Beef Noodle Soup", 2))))
//        println(orderList.size)



//        val receivedOrdersView = t.findViewById<RecyclerView>(R.id.receivedOrdersView)
//        receivedOrdersView.layoutManager = LinearLayoutManager(context)
//        receivedOrdersView.adapter = ReceivedOrdersAdapter(orderList)

        val adapter = ReceivedOrdersAdapter(orderList)
        val receivedOrdersView = t.findViewById<RecyclerView>(R.id.receivedOrdersView)
        receivedOrdersView.layoutManager = LinearLayoutManager(context)
        receivedOrdersView.adapter = adapter

        myOrdersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Clear the current list of menus
                orderList.clear()

                // Get list of Menu objects from the dataSnapshot
                for (childSnapshot in dataSnapshot.children) {
                    val order = childSnapshot.getValue(Orders::class.java)
                    if (order != null) {
                        order.id = childSnapshot.key.toString()
                        if (order.orders.any { it?.status == "Pending" }) {
                            orderList.add(order)
                        }
                    }
                }

                // Notify the adapter that the data has changed
                adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                // Handle the error
            }
        })


        return t
    }

    inner class ReveivedOrdersHolder(view: View): RecyclerView.ViewHolder(view){
        val orderText = itemView.eachOrderQuantity
        val orderTableNo = itemView.orderRow

    }
    inner class ReceivedOrdersAdapter (var orderList: List<Orders>): RecyclerView.Adapter<ReveivedOrdersHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReveivedOrdersHolder {
            val view = layoutInflater.inflate(R.layout.row_receivedorders, parent, false)
            return ReveivedOrdersHolder(view)
        }
        override fun onBindViewHolder(holder: ReveivedOrdersHolder, position: Int) {

            holder.orderText.text = "Order Table " + orderList[position].tableNo.toString()
            holder.orderTableNo.setOnClickListener {

//                val gsonPretty = GsonBuilder().setPrettyPrinting().create()
//                val jsonTutPretty: String = gsonPretty.toJson(orderList[position])
//                println(jsonTutPretty)
                // Move to next fragment

                val bundle = Bundle().apply {
                    putParcelable("order", orderList[position])
                }
                findNavController().navigate(R.id.nextAction, bundle)
            }

        }
        override fun getItemCount(): Int {
            return orderList.size
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
    }


}


