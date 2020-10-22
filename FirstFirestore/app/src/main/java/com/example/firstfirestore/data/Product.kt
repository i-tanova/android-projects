package com.example.firstfirestore.data

import com.example.firstfirestore.MyAdapterItem

data class ProductResource(var id: Long = 0L, var name: String = "", var calories: Int = 0)

class ProductUI(val id: Long?, val name: String, val calories: Int) : MyAdapterItem {
    override fun getId(): Long {
        return id ?: 0
    }
}