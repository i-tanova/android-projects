package com.example.firstfirestore

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.firstfirestore.data.DataManager
import com.example.firstfirestore.data.ProductUI
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {


    val dataManager = DataManager()
    lateinit var  productAdapter: ProductAdapter
    var searchView: SearchView? = null
    var originalData: List<ProductUI>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        //val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dataManager.getDataFromFirestore {
            originalData = it
            productAdapter = ProductAdapter(dataManager, it)
            productAdapter.setData(originalData)

            products_list.apply {
                adapter = productAdapter
                setHasFixedSize(true)
                // addItemDecoration(decoration)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        // close search view on back button pressed
        if (searchView?.isIconified() == false) {
            searchView!!.setIconified(true)
            return
        }
        super.onBackPressed()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView?.apply {
            setSearchableInfo(
                searchManager
                    .getSearchableInfo(componentName)
            )
            setMaxWidth(Int.MAX_VALUE)

            // listening to search query text change
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // filter recycler view when query submitted
                    productAdapter.getFilter().filter(query)
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    // filter recycler view when text is changed
                    productAdapter.getFilter().filter(query)
                    return false
                }
            })
        }
        return true
    }
}


class ProductAdapter(val manager: DataManager, val originalData: List<ProductUI>) : MyAdapter<ProductUI>(), Filterable {

    override fun bind(t: ProductUI, holder: MyViewHolder?) {
        val productTxtV = holder?.itemView?.findViewById<TextView>(R.id.product_lbl)
        productTxtV?.text = t.name

        val productCalsTxtV = holder?.itemView?.findViewById<TextView>(R.id.calories_lbl)
        productCalsTxtV?.text = t.calories.toString()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.product_list_item
    }

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString: String = charSequence.toString()
                val filterResults = FilterResults()
                if (charString.isBlank()) {
                    filterResults.values = originalData
                    //manager.getDataFromFirestore { filterResults.values = it }
                } else {
                    val filteredList: MutableList<ProductUI> = ArrayList()
                    for (row in originalData) {
                        if (row.name.toLowerCase()
                                .contains(charString.toLowerCase())
                        ) {
                            filteredList.add(row)
                        }
                    }
                    filterResults.values = filteredList
                }
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                filterResults?.let {
                    setData(it.values as? List<ProductUI>)
                }

            }
        }
        return filter
    }

}