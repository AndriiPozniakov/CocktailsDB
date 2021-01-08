package com.immutable.cocktaildb.data

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.immutable.cocktaildb.api.NetworkService
import com.immutable.cocktaildb.utils.ViewType
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class DrinksDataSource (
    private val networkService: NetworkService,
    private val compositeDisposable: CompositeDisposable,
    private val categories: MutableList<Category>
): PageKeyedDataSource<Int, Drink>(){

    private val TAG = "CocktailsDB"
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Drink>) {
        if (categories.isEmpty()) {
            compositeDisposable.add(
                networkService.getCategory()
                    .flatMap {
                        categories.addAll(it.categories)
                        networkService.getCocktails(category = categories[0].strCategory)
                    }
                    .subscribe(
                        { response ->
                            response.drinks.add(
                                0,
                                Drink(
                                    title = categories[0].strCategory,
                                    viewType = ViewType.ANOTHER_VIEW_TYPE
                                )
                            )
                            callback.onResult(response.drinks, null, 1)
                        },
                        {
                            setRetry(Action { loadInitial(params, callback) })
                        }
                    )
            )
        } else {
            compositeDisposable.add(
                networkService.getCocktails(category = categories[0].strCategory)
                .subscribe({ response ->
                    response.drinks.add(
                        0,
                        Drink(
                            title = categories[0].strCategory,
                            viewType = ViewType.ANOTHER_VIEW_TYPE
                        )
                    )
                    callback.onResult(response.drinks, null, 1)
                },{
                    Log.d(TAG, "${it.message}")
                })
            )
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Drink>) {
        if(categories.size > params.key) {
            compositeDisposable.add(
                networkService.getCocktails(category = categories[params.key].strCategory)
                    .subscribe(
                        { response ->
                            response.drinks.add(0, Drink(title = categories[params.key].strCategory, viewType = ViewType.ANOTHER_VIEW_TYPE))
                            callback.onResult(response.drinks, params.key + 1)
                        },
                        {
                            setRetry(Action { loadAfter(params, callback) })
                        }
                    )
            )
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Drink>) {

    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}