package com.example.firstfirestore

import android.app.Application
import androidx.room.Room
import com.example.firstfirestore.data.AppDatabase

class FirstApplication : Application() {

    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    companion object{
        var instance: FirstApplication? = null
    }

}