package com.immutable.cocktaildb.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.immutable.cocktaildb.R
import com.immutable.cocktaildb.adapters.DrinksListAdapter
import com.immutable.cocktaildb.data.Category
import com.immutable.cocktaildb.viewmodels.DrinksListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_drinks_list.*

@AndroidEntryPoint
class DrinksListActivity : AppCompatActivity() {
    private val TAG = "CocktailsDB"
    private val viewModel: DrinksListViewModel by viewModels()
    private lateinit var drinksListAdapter: DrinksListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drinks_list)

        init()
        initAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_drinks_list_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_filter -> startActivityForResult(CategoriesActivity.newIntent(this), REQUEST_CODE_CATEGORIES)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(Activity.RESULT_OK != resultCode)return
        Log.d(TAG, "$data $requestCode")
        when(requestCode){
            REQUEST_CODE_CATEGORIES -> {
                data?.let {
                    var categoriesList = it.getParcelableArrayListExtra<Category>(CategoriesActivity.CATEGORIES)
                    categoriesList?.let { viewModel.setCategories(categoriesList) }
                }
            }
        }
    }

    private fun init() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title =  getString(R.string.toolbar_title_drinks_list_activity)
            it.elevation = 4f
        }
    }

    private fun initAdapter() {
        drinksListAdapter = DrinksListAdapter { viewModel.retry() }
        drinksList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        drinksList.adapter = drinksListAdapter
        viewModel.drinksList.observe(this, Observer {
            drinksListAdapter.submitList(it)
        })
    }

    companion object {
        private const val REQUEST_CODE_CATEGORIES = 1
    }
}