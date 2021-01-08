package com.immutable.cocktaildb.data

import com.google.gson.annotations.SerializedName
import com.immutable.cocktaildb.utils.ViewType
import java.util.*

data class ResponseDrinks(
    @field:SerializedName("drinks") val drinks: MutableList<Drink>
)

data class Drink(
    @field:SerializedName("idDrink") val id: Long = Random().nextLong(),
    @field:SerializedName("strDrink") val title: String = "",
    @field:SerializedName("strDrinkThumb") val image: String = "",
    @field:SerializedName("viewType") val viewType: ViewType = ViewType.DATA_VIEW_TYPE
)