package com.heymaster.heymaster.model.user_booking

import android.os.Parcel
import android.os.Parcelable


data class UActiveBookingM(
    var idEmp: Int, var jobType: String?, var profileImg: Int, var nameEmp: String?,
    var orderDate: String?, var phoneNumber: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idEmp)
        parcel.writeString(jobType)
        parcel.writeInt(profileImg)
        parcel.writeString(nameEmp)
        parcel.writeString(orderDate)
        parcel.writeString(phoneNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UActiveBookingM> {
        override fun createFromParcel(parcel: Parcel): UActiveBookingM {
            return UActiveBookingM(parcel)
        }

        override fun newArray(size: Int): Array<UActiveBookingM?> {
            return arrayOfNulls(size)
        }
    }
}