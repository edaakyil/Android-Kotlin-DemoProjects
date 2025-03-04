package com.edaakyil.android.lib.payment.product

import javax.inject.Inject
import kotlin.random.Random

class FoodPayment @Inject constructor() : BaseProduct("", Random.nextDouble(0.0, 100.0)), IProductPayment {
    override fun calculatePayment(amount: Double) =  amount * unitPrice * 1.6
}