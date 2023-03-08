package com.example.orderistkitchen

import android.os.Parcelable
import android.os.Parcel as Parcel

//data class Orders (
//    val tableNo: Int = 0,
//    val orders: ArrayList<Food> = ArrayList<Food>(),
//    var id: String =""
//)

data class Orders(
    val tableNo: Int = 0,
    val orders: ArrayList<Food?> = ArrayList<Food?>(),
    var id: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.createTypedArrayList(Food.CREATOR)!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(tableNo)
        parcel.writeTypedList(orders)
        parcel.writeString(id)
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


