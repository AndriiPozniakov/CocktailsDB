package com.immutable.cocktaildb.data

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.immutable.cocktaildb.api.NetworkService
import io.reactivex.disposables.CompositeDisposable

class DrinksDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val networkService: NetworkService
) : DataSource.Factory<Int, Drink>() {

    private val categories: MutableList<Category> = ArrayList()
    private lateinit var drinksDataSource: DrinksDataSource
    val drinksDataSourceLiveData = MutableLiveData<DrinksDataSource>()

    override fun create(): DataSource<Int, Drink> {
        drinksDataSource = DrinksDataSource(networkService, compositeDisposable, categories)
        drinksDataSourceLiveData.postValue(drinksDataSource)
        return drinksDataSource
    }

    fun setCategories(list: List<Category>) {
        categories.clear()
        categories.addAll(list)
        drinksDataSource.invalidate()
    }

}