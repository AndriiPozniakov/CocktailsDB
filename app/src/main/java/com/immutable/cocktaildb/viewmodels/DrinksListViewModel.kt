package com.immutable.cocktaildb.viewmodels

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.immutable.cocktaildb.api.NetworkService
import com.immutable.cocktaildb.data.Category
import com.immutable.cocktaildb.data.Drink
import com.immutable.cocktaildb.data.DrinksDataSourceFactory
import io.reactivex.disposables.CompositeDisposable

class DrinksListViewModel @ViewModelInject constructor(networkService: NetworkService) : ViewModel() {
    var drinksList: LiveData<PagedList<Drink>>

    private val TAG = "CocktailsDB"
    private val compositeDisposable = CompositeDisposable()
    private val pageSize = 5
    private val drinksDataSourceFactory: DrinksDataSourceFactory

    init {
        drinksDataSourceFactory = DrinksDataSourceFactory(compositeDisposable, networkService)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setEnablePlaceholders(false)
            .build()

        drinksList = LivePagedListBuilder(drinksDataSourceFactory, config).build()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun retry() {
        drinksDataSourceFactory.drinksDataSourceLiveData.value?.retry()
    }

    fun setCategories(list: List<Category>) {
        Log.d(TAG, "$list")
        drinksDataSourceFactory.setCategories(list)
    }

    fun listIsEmpty(): Boolean {
        return drinksList.value?.isEmpty() ?: true
    }
}