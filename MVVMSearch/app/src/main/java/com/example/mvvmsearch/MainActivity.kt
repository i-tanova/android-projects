package com.example.mvvmsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.mvvmsearch.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn

class MainActivity : AppCompatActivity() {
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: ResultAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        adapter = ResultAdapter()

        // Set up RecyclerView with the adapter
        binding.recyclerView.adapter = adapter

        // Set up the search button click listener
        binding.searchButton.setOnClickListener {
            val query = binding.searchView.text.toString()
            searchViewModel.search(query)
        }

        binding.backButton.setOnClickListener {
            searchViewModel.onBackButtonPressed()
        }

        binding.searchView.doOnTextChanged { text, start, before, count ->
            searchViewModel.onSearchQueryChanged(text.toString())
        }

        binding.categorySearch.setOnClickListener {
            searchViewModel.categorySearch()
        }

        // Observe the searchResults LiveData and update the adapter when the
        // results change
        searchViewModel.searchResults.observe(this, Observer { results ->
            adapter.setResults(results)
        })

        searchViewModel.updateQueryText.observe(this) {
            binding.searchView.setText(it ?: "")
        }

        searchViewModel.backButtonVisibility.observe(this){
            binding.backButton.visibility = if(it == true) View.VISIBLE else View.GONE
        }

        lifecycleScope.launchWhenStarted {
            searchViewModel.searchInVisibleMapAreaFlow.collect{
                binding.searchInVisibleMapArea.text = it
            }
        }

        binding.searchInVisibleMapAreaButton.setOnClickListener{
            searchViewModel.visibleMapSearch()
        }
    }
}