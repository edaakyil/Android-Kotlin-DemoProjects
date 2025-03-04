package com.edaakyil.android.lib.payment.product

import javax.inject.Inject
import kotlin.random.Random

class MenuPayment @Inject constructor(private var foodPayment: FoodPayment, private var drinkPayment: DrinkPayment) : BaseProduct("Menu", Random.nextDouble(1.0, 100.0)), IProductPayment {
    override fun calculatePayment(amount: Double): Double {
        return amount * (foodPayment.calculatePayment(1.0) + drinkPayment.calculatePayment(1.0))
    }
}