package com.example.firstfirestore

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.firstfirestore.data.DataManager
import com.example.firstfirestore.data.ProductUI
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {


    val dataManager = DataManager()
    lateinit var productAdapter: ProductAdapter
    var searchView: SearchView? = null
    var originalData: List<ProductUI>? = null
    val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    var tracker: SelectionTracker<Long>? = null

    class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
        ItemDetailsLookup<Long>() {

        override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
            val view = recyclerView.findChildViewUnder(event.x, event.y)
            if (view != null) {
                return (recyclerView.getChildViewHolder(view) as MyViewHolder).getItemDetails()
            }
            return null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        //val decoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        setupList()

        add_button.setOnClickListener { view ->
            val selectedUIItems = arrayListOf<ProductUI>()
            val selectedItems = tracker?.selection
            selectedItems?.forEach {itemId ->
                val item = productAdapter.originalData.find { it.id == itemId }
                item?.let { selectedUIItems.add(item)  }
            }
            coroutineScope.launch {
                dataManager.storeItems(selectedUIItems)
            }
        }
    }

    private fun setupList() {
        dataManager.getDataFromFirestore {
            originalData = it
            productAdapter = ProductAdapter(dataManager, it)

            products_list.apply {
                adapter = productAdapter
                setHasFixedSize(true)
                // addItemDecoration(decoration)
            }


            tracker = SelectionTracker.Builder(
                "mySelection",
                products_list,
                ProductAdapter.MyItemKeyProvider(productAdapter),
                MyItemDetailsLookup(products_list),
                StorageStrategy.createLongStorage()
            ).withSelectionPredicate(
                SelectionPredicates.createSelectAnything()
            ).build()

            productAdapter.tracker = tracker

            tracker?.addObserver(
                object : SelectionTracker.SelectionObserver<Long>() {
                    override fun onSelectionChanged() {
                        super.onSelectionChanged()
                        val selectedItems = tracker?.selection
                        if (selectedItems?.size() ?: 0 > 0) {
                            supportActionBar?.title = "Selected  ${selectedItems?.size()}"
                            add_button.visibility = View.VISIBLE
                        } else {
                            supportActionBar?.title = getString(R.string.app_name)
                            add_button.visibility = View.GONE
                        }
                    }
                })

            productAdapter.setData(originalData)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        tracker?.onSaveInstanceState(outState)
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


class ProductAdapter(val manager: DataManager, val originalData: List<ProductUI>) :
    MyAdapter<ProductUI>(), Filterable {

    // This is needed for Recycle View - multiple selection
    init {
        setHasStableIds(true)
    }

    override fun bind(t: ProductUI, holder: MyViewHolder?, isSelected: Boolean) {
        val productTxtV = holder?.itemView?.findViewById<TextView>(R.id.product_lbl)
        productTxtV?.text = t.name

        val productCalsTxtV = holder?.itemView?.findViewById<TextView>(R.id.calories_lbl)
        productCalsTxtV?.text = t.calories.toString()

        val isSelectedIcon = holder?.itemView?.findViewById<ImageView>(R.id.product_selected)
        isSelectedIcon?.visibility = if (isSelected) View.VISIBLE else View.GONE
    }

    class DiffCallback : DiffUtil.ItemCallback<ProductUI>() {
        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.name == newItem.name
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.product_list_item
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getFilter(): Filter {
        val filter = object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charString: String = charSequence.toString()
                val filterResults = FilterResults()
                if (charString.isBlank()) {
                    filterResults.values = originalData
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

    class MyItemKeyProvider(private val adapter: ProductAdapter) :
        ItemKeyProvider<Long>(SCOPE_CACHED) {
        override fun getKey(position: Int): Long? =
            adapter.getData()?.get(position)?.id

        override fun getPosition(key: Long): Int =
            adapter.getData()?.indexOfFirst { it.id == key } ?: -1
    }

}