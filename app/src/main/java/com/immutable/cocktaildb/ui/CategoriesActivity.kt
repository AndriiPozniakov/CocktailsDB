package com.immutable.cocktaildb.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.immutable.cocktaildb.R
import com.immutable.cocktaildb.adapters.CategoriesAdapter
import com.immutable.cocktaildb.data.Category
import com.immutable.cocktaildb.utils.OnFilterAccept
import com.immutable.cocktaildb.utils.ToggleListener
import com.immutable.cocktaildb.viewmodels.CategoriesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_drinks_list.toolbar
import kotlinx.android.synthetic.main.activity_categories.*

@AndroidEntryPoint
class CategoriesActivity : AppCompatActivity(), ToggleListener, OnFilterAccept {
    private val TAG = "CocktailsDB"
    private val adapter = CategoriesAdapter(this, this)
    private val viewModel: CategoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        init()
        initToolbar()
    }

    override fun onToggle(position: Int, isChecked: Boolean) {
        viewModel.onCheckChange(position, isChecked)
    }

    override fun onAccept() {
        var intent = Intent().apply {
            putExtra(CATEGORIES, viewModel.getCurrentCategories())
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun init() {
        viewModel.getCategories().observe(this, Observer {
            when(it){
                is StateCategoriesActivity.Error -> Log.e(TAG, "Something went wrong!! Whoooops...")
                is StateCategoriesActivity.Loading -> { progressBar.visibility = View.VISIBLE }
                is StateCategoriesActivity.Success -> {
                    progressBar.visibility = View.GONE
                    initAdapter(it.response.categories)
                }
            }
        })
    }

    private fun initAdapter(list: List<Category>) {
        filterList.adapter = adapter
        filterList.layoutManager = LinearLayoutManager(this)
        adapter.setUpdateInfo(categories = list)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title =  getString(R.string.toolbar_title_drinks_filter_activity)
            it.elevation = 4f
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    companion object {
        const val CATEGORIES = "com.immutable.android.coctaildb.drinks"

        fun newIntent(packageContext: Context): Intent {
            return Intent(packageContext, CategoriesActivity::class.java)
        }
    }
}