package com.edaakyil.android.kotlin.app.sqlite.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("products")
data class Product(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("product_id") val id: Long = 0,
    val code: String,
    val name: String,
    @ColumnInfo("unit_price") val unitPrice: Double
)