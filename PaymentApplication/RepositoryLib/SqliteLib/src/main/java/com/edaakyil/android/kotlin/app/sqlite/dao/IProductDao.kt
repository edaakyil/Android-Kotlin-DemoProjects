package com.edaakyil.android.kotlin.app.sqlite.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.edaakyil.android.kotlin.app.sqlite.entity.Product

@Dao
interface IProductDao {
    @Insert
    fun insert(product: Product)

    @Insert
    fun insert(vararg product: Product)

    @Query("SELECT * FROM product")
    fun findAll(): List<Product>

    @Query("SELECT * FROM product WHERE code = :code")
    fun findByCode(code: String): Product

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)

    @Query("DELETE FROM product")
    fun deleteAll()
}