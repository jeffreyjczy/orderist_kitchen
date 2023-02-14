package com.example.orderistkitchen

import android.os.Parcelable
import android.os.Parcel as Parcel

//class Orders (
//    val tableNo: Int = 0,
//    val orders: ArrayList<Food>
//)

class Orders(var tableNo: Int, var orders: ArrayList<Food>): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readArrayList(Food::class.java.classLoader) as ArrayList<Food>
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(tableNo)
        parcel.writeList(orders)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Orders> {
        override fun createFromParcel(parcel: Parcel): Orders {
            return Orders(parcel)
        }

        override fun newArray(size: Int): Array<Orders?> {
            return arrayOfNulls(size)
        }
    }
}
