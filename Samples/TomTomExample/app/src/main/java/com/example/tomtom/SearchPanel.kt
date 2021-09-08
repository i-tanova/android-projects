package com.example.tomtom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.tomtom.databinding.SearchLayoutBinding
import android.util.Log.d as d1

class SearchPanel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private val viewModel = SearchPanelViewModel()

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        require(childCount == 1) {
            "Only a single child is permitted under the SearchPanel"
        }

        getChildAt(0)?.layout(0, 0, width, height)
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupBinding()
    }

    private fun setupBinding() {
        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding = DataBindingUtil.inflate<SearchLayoutBinding>(
            inflater, R.layout.search_layout, this, true
        )
        binding.searchPanelViewModel = viewModel
        binding.searchView.findViewById<SearchView>(R.id.search_close_btn)?.setOnClickListener{
            clicked()
        }
    }

    private fun clicked() {
        d1("TAG","Click")
    }
}