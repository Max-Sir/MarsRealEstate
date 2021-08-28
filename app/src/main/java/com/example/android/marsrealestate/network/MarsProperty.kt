package com.example.android.marsrealestate.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MarsProperty(
    val id: String,
    @Json(name = "img_src") val imgSrcUrl: String,
    val type: String,
    val price: Double
) : Parcelable {
    val isRental
        get() = type == "rent"

    override fun toString(): String {
        return "I would like to $type this place for ${
            when (type) {
                MarsApiFilter.SHOW_BUY.value ->"$${price.toInt()}"
                MarsApiFilter.SHOW_RENT.value->"$${price.toInt()} /month"
                else->""
            }
        } if you would like to see it i attach this link below this message:\n\n\n$imgSrcUrl"
    }


}
