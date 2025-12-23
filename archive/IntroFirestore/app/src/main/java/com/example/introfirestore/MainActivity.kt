package com.example.introfirestore

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val firebaseDB = FirebaseFirestore.getInstance()
    val documentRef = firebaseDB.collection("Products").document("First")

    // OR
    // firebaseDB.document("Products/"First")

    val PRODUCT_KEY = "product"
    val DESCR_KEY = "description"

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
     *  Delete data from Firestore
     */
    private fun deleteDataFromFirestore() {
        documentRef.update(PRODUCT_KEY, FieldValue.delete())
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                Log.d("FIREBASE", "onSuccess")
            }
            .addOnFailureListener { Log.d("FIREBASE", "onFailure ${it.toString()}") }
    }

    private fun deleteAll(){
        documentRef.delete()
    }

    /**
     *
     *   Update data from Firestore
     *
     */
    private fun updateDataFromFirestore() {
        val product = productName.text.toString().trim()
        documentRef.update(mapOf(PRODUCT_KEY to product))
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                Log.d("FIREBASE", "onSuccess")
            }
            .addOnFailureListener { Log.d("FIREBASE", "onFailure ${it.toString()}") }
    }

    /**
     *
     *   Get data from Firestore
     *
     */
    private fun getDataFromFirestore() {
        documentRef.get().addOnSuccessListener {
            if (it.exists()) {
                showData(it)
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
        }
    }


    /**
     *
     *   Save to Firestore
     *
     */
    private fun saveDataToFirestore() {

        val product = productName.text.toString().trim()
        val description = productDescription.text.toString().trim()
        //You can use object
        val productObj = Product(product, description)
        documentRef
            .set(productObj)
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
                Log.d("FIREBASE", "onSuccess")
            }
            .addOnFailureListener { Log.d("FIREBASE", "onFailure ${it.toString()}") }

        /// You can use map
//        documentRef
//            .set(mapOf(PRODUCT_KEY to product, DESCR_KEY to description))
//            .addOnSuccessListener {
//                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show()
//                Log.d("FIREBASE", "onSuccess")
//            }
//            .addOnFailureListener { Log.d("FIREBASE", "onFailure ${it.toString()}") }
    }

    private fun showData(it: DocumentSnapshot) {
        //You can use object
        val product: Product? = it.toObject(Product::class.java)
        val text = "Product ${product?.product}. Description: ${product?.description}"
        showDataTxtV.text = text

        /// You can use map
//        val product = it.getString(PRODUCT_KEY)
//        val description = it.getString(DESCR_KEY)
//        showDataTxtV.text = "Product $product. Description: $description"
    }


    /**
     *
     *     Listen for Firestore data changes
     *
     */
    override fun onStart() {
        super.onStart()
        documentRef.addSnapshotListener(this) { value, error ->
            if (error != null) {
                Toast.makeText(this, "Failure", Toast.LENGTH_LONG).show()
            } else if (value?.exists() == true) {
                showData(value)
            }else{
                    cleanData()
            }
        }
    }

    private fun cleanData() {
        showDataTxtV.text = ""
    }
}