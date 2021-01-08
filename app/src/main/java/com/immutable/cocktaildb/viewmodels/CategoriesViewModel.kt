package com.immutable.cocktaildb.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.immutable.cocktaildb.api.NetworkService
import com.immutable.cocktaildb.data.Category
import com.immutable.cocktaildb.ui.StateCategoriesActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CategoriesViewModel @ViewModelInject constructor(networkService: NetworkService) : ViewModel() {
    private val TAG = "CocktailsDB"
    private val compositeDisposable = CompositeDisposable()
    private val state = MutableLiveData<StateCategoriesActivity>()
    private val categories: MutableList<Category> = ArrayList()

    init {
        state.postValue(StateCategoriesActivity.Loading)

        compositeDisposable.add(
            networkService.getCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        state.postValue(StateCategoriesActivity.Success(it))
                        categories.clear()
                        categories.addAll(it.categories)
                    },
                    {
                        state.postValue(StateCategoriesActivity.Error)
                        Log.d(TAG, "${it.message}")
                    }
                )
        )
    }

    fun getCategories() : LiveData<StateCategoriesActivity> = state

    fun onCheckChange(position: Int, isChecked: Boolean) {
        categories[position].isChecked = isChecked
    }

    fun getCurrentCategories(): ArrayList<Category> {
        val currentCategories = ArrayList<Category>()
        categories.let {
            for (i in 0 until it.size) {
                if (it[i].isChecked) currentCategories.add(it[i])
            }
        }

        return currentCategories
    }
}