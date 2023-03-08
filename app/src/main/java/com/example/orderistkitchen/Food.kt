package com.example.orderistkitchen

import android.os.Parcel
import android.os.Parcelable

//class Food (
//    val name: String = "",
//    val quantity: Int = 0,
//    val status: String = "Pending"
//)

class Food(
    val name: String = "",
    val quantity: Int = 0,
    val status: String = "Pending",
    var id: String = ""
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(quantity)
        parcel.writeString(status)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
}