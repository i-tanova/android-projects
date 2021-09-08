package com.tanovait.makekerstinhappy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun onClick(view: View){
        findViewById<View>(R.id.okbutton).visibility = View.GONE
    }
}