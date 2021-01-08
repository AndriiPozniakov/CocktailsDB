package com.immutable.cocktaildb.ui

import com.immutable.cocktaildb.data.ResponseDrinks

sealed class StateDrinksActivity {
    object Loading: StateDrinksActivity()
    class Error(val message: String): StateDrinksActivity()
    class Success(val response: ResponseDrinks): StateDrinksActivity()
}