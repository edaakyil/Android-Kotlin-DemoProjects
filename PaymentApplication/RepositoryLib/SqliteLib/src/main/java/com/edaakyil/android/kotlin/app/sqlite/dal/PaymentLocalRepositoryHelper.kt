package com.edaakyil.android.kotlin.app.sqlite.dal

import com.edaakyil.android.kotlin.app.sqlite.dao.IProductDao
import com.edaakyil.android.kotlin.app.sqlite.entity.Product

class PaymentLocalRepositoryHelper(val productDao: IProductDao) {
    fun saveProduct(product: Product) {
        try {
            productDao.insert(product)
        } catch (ex: RuntimeException) {
            TODO("Not yet implemented!...")
        }
    }

    fun findAllProducts(): List<Product> {
        try {
            return productDao.findAll()
        } catch (ex: RuntimeException) {
            TODO("Not yet implemented!...")
        }
    }

    fun findProductByCode(code: String): Product {
        try {
            return productDao.findByCode(code)
        } catch (ex: RuntimeException) {
            TODO("Not yet implemented!...")
        }
    }

    fun updateProduct(product: Product) {
        try {
            return productDao.update(product)
        } catch (ex: RuntimeException) {
            TODO("Not yet implemented!...")
        }
    }

    fun deleteProduct(product: Product) {
        try {
            return productDao.delete(product)
        } catch (ex: RuntimeException) {
            TODO("Not yet implemented!...")
        }
    }

    fun deleteAllProducts() {
        try {
            productDao.deleteAll()
        } catch (ex: RuntimeException) {
            TODO("Not yet implemented!...")
        }
    }
}