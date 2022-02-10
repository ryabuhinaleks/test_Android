package com.example.testtask.model

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@Parcelize
@IgnoreExtraProperties
data class Card(
    var image: String = "default",
    var price: Long = 0,
    var area: Int = 0,
    var place: String = "",
    var count: Int? = null,
    var price2: Int? = null,
    var floor: Int? = null,
    var id: String? = "",
) :
    Parcelable
