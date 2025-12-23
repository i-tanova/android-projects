package com.tanovait.learnenglishwithfriends.data

import androidx.room.*

data class VideoUI(val url: String = "")

@Entity(tableName = "videos")
data class VideoDB(@PrimaryKey val url: String)

@Dao
interface VideoDAO {

    @Insert
    suspend fun insertAll(video: List<VideoDB>)

    @Query("SELECT * FROM videos")
    suspend fun loadAll(): List<VideoDB>

    @Query("DELETE FROM videos")
    suspend fun deleteAll()
}

@Database(entities = [VideoDB::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDAO
}

fun List<VideoDB>.mapToUI(): List<VideoUI> = map{
    VideoUI(it.url)
}

fun List<VideoUI>.mapToDB(): List<VideoDB> = map{
    VideoDB(it.url)
}