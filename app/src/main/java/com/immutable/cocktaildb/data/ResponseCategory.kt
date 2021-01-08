package com.immutable.cocktaildb.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseCategory(
    @field:SerializedName("drinks") val categories: List<Category>
)

@Parcelize
data class Category(
    @field:SerializedName("strCategory") val strCategory: String = "",
    @field:SerializedName("isChecked") var isChecked: Boolean = true
) : Parcelable