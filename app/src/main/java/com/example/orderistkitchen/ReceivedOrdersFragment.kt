package com.example.orderistkitchen

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.row_receivedorders.view.*


class ReceivedOrdersFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val t = inflater.inflate(R.layout.fragment_received_orders, container, false)

        // History table
        val orderList = mutableListOf <Orders>()
        orderList.add(Orders(1, arrayListOf(Food("Chicken Fried Rice", 1))))
        orderList.add(Orders(2, arrayListOf(Food("Beef Noodle Soup", 2), Food("Egg Fried Rice", 1))))
        orderList.add(Orders(3, arrayListOf(Food("Vegetable Stir Fry", 3), Food("Steamed Rice", 1),Food("Beef Noodle Soup", 2))))

        val gsonPretty = GsonBuilder().setPrettyPrinting().create()
        val jsonTutPretty: String = gsonPretty.toJson(orderList)
        println(jsonTutPretty)
        println(orderList.size)


        val receivedOrdersView = t.findViewById<RecyclerView>(R.id.receivedOrdersView)
        receivedOrdersView.layoutManager = LinearLayoutManager(context)
        receivedOrdersView.adapter = ReceivedOrdersAdapter(orderList)

        return t
    }

    inner class ReveivedOrdersHolder(view: View): RecyclerView.ViewHolder(view){
        val orderText = itemView.orderText
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

                    // go to next fragment

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