package com.example.external

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

   // private lateinit var controllerView: ViewContorller<MainActivitySate>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
//
//        controllerView =
//            object : ViewContorller<MainActivitySate>(null) {
//                override fun onStateChanged() {
//                    controllerView.getState().registrationState
//                }
//
//            }
//        controllerView.registerLifecycle(lifecycle)
    }
}

//class MainActivitySate: ViewState
