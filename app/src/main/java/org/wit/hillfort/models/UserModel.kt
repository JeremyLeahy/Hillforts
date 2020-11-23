package org.wit.hillfort.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(var userId: Long = 0,
                     var firstName: String = "",
                     var lastName: String = "",
                     var email: String = "",
                     var password: String = "",
                     var hillforts: MutableList<HillfortModel> = mutableListOf() ) : Parcelable