package com.example.firstfirestore.data

import androidx.room.*
import com.example.firstfirestore.MyAdapterItem
import com.google.firebase.firestore.auth.User

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

@Entity(tableName = "product")
class ProductDB(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "calories") val calories: Int
)

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<ProductDB>

    @Query("SELECT * FROM product WHERE id IN (:productIds)")
    fun loadAllByIds(productIds: IntArray): List<ProductDB>

    @Query(
        "SELECT * FROM product WHERE name LIKE :name LIMIT 1"
    )
    fun findByName(name: String): ProductDB

    @Insert
    fun insertAll(products: List<ProductDB>)

    @Delete
    fun delete(product: ProductDB)
}

@Database(entities = arrayOf(ProductDB::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}