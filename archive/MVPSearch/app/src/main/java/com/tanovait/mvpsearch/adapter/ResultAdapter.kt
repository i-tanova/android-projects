package com.tanovait.mvpsearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tanovait.mvpsearch.databinding.ItemResultBinding

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {
    private var results: List<SearchResult> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemResultBinding.inflate(layoutInflater, parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = results[position]
        holder.binding.titleTextView.text = result.title
        holder.binding.descriptionTextView.text = result.description
    }

    override fun getItemCount(): Int {
        return results.size
    }

    fun setResults(results: List<SearchResult>) {
        this.results = results
        notifyDataSetChanged()
    }

    inner class ResultViewHolder(val binding: ItemResultBinding)
        :RecyclerView.ViewHolder(binding.root)
}

data class SearchResult(val title: String, val description: String)