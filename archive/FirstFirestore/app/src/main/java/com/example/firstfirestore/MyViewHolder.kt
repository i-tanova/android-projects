package com.example.firstfirestore

import android.view.View
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Generic [RecyclerView.ViewHolder] to be used in [MyAdapter]
 */
abstract class MyViewHolder(itemView: View) : ViewHolder(itemView){
    abstract fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long>
}