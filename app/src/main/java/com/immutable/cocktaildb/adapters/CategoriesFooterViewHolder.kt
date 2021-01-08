package com.immutable.cocktaildb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.immutable.cocktaildb.R
import com.immutable.cocktaildb.utils.OnFilterAccept
import kotlinx.android.synthetic.main.item_footer_category.view.*

class CategoriesFooterViewHolder(view: View, private val listener: OnFilterAccept) : RecyclerView.ViewHolder(view) {

    fun bind() {
        itemView.btnApplyFilter.setOnClickListener {
            listener.onAccept()
        }
    }

    companion object {
        fun create(parent: ViewGroup, listener: OnFilterAccept): CategoriesFooterViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_footer_category, parent, false)
            return CategoriesFooterViewHolder(view, listener = listener)
        }
    }
}