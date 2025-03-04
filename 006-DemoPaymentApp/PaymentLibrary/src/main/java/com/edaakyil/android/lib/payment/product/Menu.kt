package com.edaakyil.android.lib.payment.product

import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

class Menu @Inject constructor(@Named("food") var product1: BaseProduct, @Named("drink") var product2: BaseProduct, @Named("desert") var product3: BaseProduct) : BaseProduct("Menu", Random.nextDouble(1.0, 100.0)), IProductPayment {
    override fun calculatePayment(amount: Double): Double {
        return amount * (product1.calculatePayment(1.0) + product2.calculatePayment(1.0) + product3.calculatePayment(1.0))
    }
}