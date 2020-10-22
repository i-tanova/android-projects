package com.example.firstfirestore.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.concurrent.Flow

class DataManager{

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val collectionRef = firebaseDB.collection("Products")

    public fun getDataFromFirestore(result: (ArrayList<ProductUI>) -> Unit){
        collectionRef.get().addOnSuccessListener { querySnapshot ->
            result(getData(querySnapshot))
        }.addOnFailureListener {
            Log.d("FIREBASE", "onFailure ${it.toString()}")
            result(emptyList<ProductUI>() as ArrayList<ProductUI>)
        }
    }

    private fun getData(querySnapshot: QuerySnapshot): ArrayList<ProductUI> {
        val productsList = ArrayList<ProductUI>()
        for (query in querySnapshot) {
            val product: ProductResource? = query.toObject(ProductResource::class.java)
            product?.let {
                productsList.add(ProductUI(product.id, product.name, product.calories))
            }
        }
        Log.d("FIREBASE", "onSuccess")
        return productsList
    }
}