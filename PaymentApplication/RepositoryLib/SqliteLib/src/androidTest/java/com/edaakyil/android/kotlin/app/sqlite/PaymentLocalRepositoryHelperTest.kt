package com.edaakyil.android.kotlin.app.sqlite

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.edaakyil.android.kotlin.app.sqlite.dal.PaymentLocalRepositoryHelper
import com.edaakyil.android.kotlin.app.sqlite.db.PaymentLocalDatabase
import com.edaakyil.android.kotlin.app.sqlite.entity.Product

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class PaymentLocalRepositoryHelperTest {
    private lateinit var mHelper: PaymentLocalRepositoryHelper

    @Before
    fun setUp() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val db = Room.databaseBuilder(appContext, PaymentLocalDatabase::class.java, "payment-db-test")
            .allowMainThreadQueries()
            .build()

        mHelper = PaymentLocalRepositoryHelper(db.productDao())
    }

    @Test
    fun findAll_whenCalled_thenSizeSuccessful() {
        mHelper.deleteAllProducts()

        mHelper.saveProduct(Product(code = "test-1", name = "Test Product 1", unitPrice = 100.0))
        mHelper.saveProduct(Product(code = "test-2", name = "Test Product 2", unitPrice = 100.0))
        mHelper.saveProduct(Product(code = "test-3", name = "Test Product 3", unitPrice = 100.0))

        val expectedSize = 3
        val products = mHelper.findAllProducts()

        assertEquals(expectedSize, products.size)
    }
}