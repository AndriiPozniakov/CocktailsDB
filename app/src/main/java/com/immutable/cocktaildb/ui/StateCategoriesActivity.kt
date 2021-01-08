package com.immutable.cocktaildb.ui

import com.immutable.cocktaildb.data.ResponseCategory

sealed class StateCategoriesActivity {
    class Success(val response: ResponseCategory): StateCategoriesActivity()
    object Loading : StateCategoriesActivity()
    object Error : StateCategoriesActivity()
}