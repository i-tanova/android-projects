package com.tanovait.learnenglishwithfriends.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class DataManager {

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val collectionRef = firebaseDB.collection("video_urls")

    public fun getDataFromFirestore(result: (ArrayList<VideoUI>) -> Unit) {
        collectionRef.get().addOnSuccessListener { querySnapshot ->
            result(getData(querySnapshot))
        }.addOnFailureListener {
            Log.d("FIREBASE", "onFailure ${it.toString()}")
            result(emptyList<VideoUI>() as ArrayList<VideoUI>)
        }
    }

    private fun getData(querySnapshot: QuerySnapshot): ArrayList<VideoUI> {
        val productsList = ArrayList<VideoUI>()
        for (query in querySnapshot) {
            val product  = query.toObject(VideoUI::class.java)
            product?.let {
                productsList.add(it)
            }
        }
        Log.d("FIREBASE", "onSuccess")
        return productsList
    }

//    suspend fun storeItems(productUI: List<ProductUI>) {
//        FirstApplication.instance?.db?.productDao()
//            ?.insertAll(productUI.map { ProductDB(it.getId(), it.name, it.calories) })
//        Log.d("DB", "storeItems")
//    }
//
//    suspend fun getItems(): List<ProductUI>? {
//        return FirstApplication.instance?.db?.productDao()
//            ?.getAll()?.map { ProductUI(it.id, it.name, it.calories) }
//    }
}