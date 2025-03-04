package com.edaakyil.android.lib.payment.product

interface IProductPayment {
    fun calculatePayment(amount: Double): Double
}