package com.edaakyil.android.kotlin.app.sqlite.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "product_id") val id: Long = 0,
    val code: String,
    val name: String,
    val unitPrice: Double
)