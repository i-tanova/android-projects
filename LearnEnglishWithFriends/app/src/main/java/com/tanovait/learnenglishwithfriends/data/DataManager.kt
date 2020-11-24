package com.tanovait.learnenglishwithfriends.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataManager(val context: Context) {

    private val firebaseDB = FirebaseFirestore.getInstance()
    private val collectionRef = firebaseDB.collection("video_urls")
    private val db = Room.databaseBuilder(context.applicationContext, VideoDatabase::class.java, "videos.db").build()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    suspend fun getDataFromFirestore(result: (List<VideoUI>) -> Unit){
        val items = getItems()
        if(items.isNullOrEmpty()){
            collectionRef.get().addOnSuccessListener { querySnapshot ->
                val videos = getData(querySnapshot)
                coroutineScope.launch {
                    storeItems(videos)
                }
                result(videos)
            }.addOnFailureListener {
                Log.d("FB", "onFailure ${it.toString()}")
                result(emptyList<VideoUI>() as ArrayList<VideoUI>)
            }
        }else{
            Log.d("DB", "SUCCESS")
            result(items)
        }
    }

    private fun getData(querySnapshot: QuerySnapshot): ArrayList<VideoUI> {
        val productsList = ArrayList<VideoUI>()
        for (query in querySnapshot) {
            val product  = query.toObject(VideoUI::class.java)
            product.let {
                productsList.add(it)
            }
        }
        Log.d("FB", "onSuccess")
        return productsList
    }

    suspend fun storeItems(videoUI: List<VideoUI>) {
        Log.d("DB", "storeItems")
       db.videoDao().insertAll(videoUI.mapToDB())
    }

    suspend fun getItems(): List<VideoUI>? {
        Log.d("DB", "getItems")
        return db.videoDao().loadAll().mapToUI()
    }
}