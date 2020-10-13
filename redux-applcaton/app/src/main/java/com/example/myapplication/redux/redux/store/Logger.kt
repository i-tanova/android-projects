package nl.virtualaffairs.bankingright.redux.store

import android.util.Log


interface Logger{
    fun log(tag: String, message: String)
}

class TimberLogger : Logger{
    override fun log(tag: String, message: String) {
       Log.d(tag, message)
    }
}