package com.example.firstfirestore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder


/**
 * Generic adapter with [RecyclerView.ViewHolder] to be used in [RecyclerView]
 * @param <T> Object of the stored data
</T> */
abstract class MyAdapter<T> : RecyclerView.Adapter<ViewHolder>() {

    private var data: List<T>? = null
    private var onItemClickListener: OnItemClickListener<T>? = null

    interface OnItemClickListener<T> {
        fun onItemClick(item: T)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener<T>?) {
        this.onItemClickListener = onItemClickListener
    }

    fun setData(data: List<T>?) {
        this.data = data
        notifyDataSetChanged()
    }

    fun getData(): List<T>?{
        return this.data?.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(viewType, parent, false) as View
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener { v: View? ->
                    data!![position]
            }
        }
        bind(
                data!![position],
                holder as MyViewHolder
        )
    }

    protected abstract fun bind(t: T, holder: MyViewHolder?)

    override fun getItemCount(): Int {
        return if (data == null) {
            0
        } else {
            data?.size ?: 0
        }
    }
}