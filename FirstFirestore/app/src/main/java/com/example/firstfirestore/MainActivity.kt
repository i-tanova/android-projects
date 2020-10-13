package com.example.firstfirestore

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firstfirestore.data.ProductResource
import com.example.firstfirestore.data.ProductUI
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val productAdapter = ProductAdapter()


    val firebaseDB = FirebaseFirestore.getInstance()
    val collectionRef = firebaseDB.collection("Products")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        products_list.apply {
            adapter = productAdapter
            setHasFixedSize(true)
        }

        getDataFromFirestore()
    }

    private fun getDataFromFirestore() {
        collectionRef.get().addOnSuccessListener { querySnapshot ->
            showData(querySnapshot)
        }.addOnFailureListener { Log.d("FIREBASE", "onFailure ${it.toString()}") }
    }


    /**
     *
     *     Listen for Firestore data changes
     *
     */
    override fun onStart() {
        super.onStart()
        collectionRef.addSnapshotListener(this) { value, error ->
            if (error != null) {
                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
            } else if (value?.isEmpty == false) {
                showData(value)
            } else {
                cleanData()
            }
        }
    }

    private fun cleanData() {
        productAdapter.setData(emptyList())
    }

    private fun showData(querySnapshot: QuerySnapshot) {
        val productsList = ArrayList<ProductUI>()
        for (query in querySnapshot) {
            val product: ProductResource? = query.toObject(ProductResource::class.java)
            product?.let {
                productsList.add(ProductUI(product.name, product.calories))
            }
        }
        Log.d("FIREBASE", "onSuccess")

        productAdapter.setData(productsList)
    }


}


class ProductAdapter : MyAdapter<ProductUI>() {

    override fun bind(t: ProductUI, holder: MyViewHolder?) {
        val productTxtV = holder?.itemView?.findViewById<TextView>(R.id.product_lbl)
        productTxtV?.text = t.name

        val productCalsTxtV = holder?.itemView?.findViewById<TextView>(R.id.calories_lbl)
        productCalsTxtV?.text = t.calories.toString()
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.product_list_item
    }

}