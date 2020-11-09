package com.example.firstfirestore.data

import androidx.room.*
import com.example.firstfirestore.MyAdapterItem
import java.util.*

data class ProductResource(var id: Long = 0L, var name: String = "", var calories: Int = 0)

class ProductUI(val id: Long?, val name: String, val calories: Int) : MyAdapterItem {
    override fun getId(): Long {
        return id ?: 0
    }
}
//
//@Entity
//data class User(
//    @PrimaryKey val uid: Int,
//    @ColumnInfo(name = "first_name") val firstName: String?,
//    @ColumnInfo(name = "last_name") val lastName: String?
//)

class MyTime(val date: Date) {
    val id: String
        get() {
            val calenar = Calendar.getInstance()
            calenar.time = date
            return "${calenar.get(Calendar.DAY_OF_MONTH)}/ ${calenar.get(Calendar.DAY_OF_MONTH)}/${
                calenar.get(Calendar.DAY_OF_MONTH)
            }"
        }
}


@Entity(tableName = "product")
class ProductDB(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "calories") val calories: Int
)

@Entity(tableName = "product")
class DayList(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "day") val day: String,
    @ColumnInfo(name = "product_id") val productId: Long
)

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getAll(): List<ProductDB>

    @Query("SELECT * FROM product WHERE id IN (:productIds)")
    suspend fun loadAllByIds(productIds: IntArray): List<ProductDB>

    @Query(
        "SELECT * FROM product WHERE name LIKE :name LIMIT 1"
    )
    suspend fun findByName(name: String): ProductDB

    @Insert
    suspend fun insertAll(products: List<ProductDB>)

    @Delete
    suspend fun delete(product: ProductDB)
}

@Dao


@Database(entities = arrayOf(ProductDB::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}