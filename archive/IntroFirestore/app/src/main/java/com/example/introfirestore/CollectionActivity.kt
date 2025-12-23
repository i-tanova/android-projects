package com.example.introfirestore

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_main.*

class CollectionActivity : AppCompatActivity() {

    val firebaseDB = FirebaseFirestore.getInstance()
    val collectionRef = firebaseDB.collection("Products")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        save_btn.setOnClickListener {
            saveDataToFirestore()
        }

        show_data_btn.setOnClickListener {
            getDataFromFirestore()
        }

        update_data_btn.setOnClickListener {
            updateDataFromFirestore()
        }

        delete_data_btn.setOnClickListener {
            deleteDataFromFirestore()
        }
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
        showDataTxtV.text = ""
    }

    private fun deleteDataFromFirestore() {
        TODO("Not yet implemented")
    }

    private fun updateDataFromFirestore() {
        TODO("Not yet implemented")
    }

    private fun getDataFromFirestore() {
        collectionRef.get().addOnSuccessListener { querySnapshot ->
            showDataTxtV.text = ""
            showData(querySnapshot)
        }
            .addOnFailureListener { Log.d("FIREBASE", "onFailure ${it.toString()}") }
    }

    private fun showData(querySnapshot: QuerySnapshot) {
        for (query in querySnapshot) {
            val product: Product? = query.toObject(Product::class.java)
            val text = "Product ${product?.product}. Description: ${product?.description}"
            val oldText = showDataTxtV.text
            showDataTxtV.text = oldText.toString() + "\n" + text
        }
        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
        Log.d("FIREBASE", "onSuccess")
    }

    private fun saveDataToFirestore() {
        val product = productName.text.toString().trim()
        val description = productDescription.text.toString().trim()

        val productObj = Product(product, description)
        collectionRef
            .add(productObj)
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                Log.d("FIREBASE", "onSuccess")
            }
            .addOnFailureListener { Log.d("FIREBASE", "onFailure ${it.toString()}") }

    }
}