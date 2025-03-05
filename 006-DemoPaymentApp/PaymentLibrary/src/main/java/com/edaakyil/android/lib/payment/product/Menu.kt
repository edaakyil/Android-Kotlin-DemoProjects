package com.edaakyil.android.lib.payment.product

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Named
import kotlin.random.Random

@ActivityScoped
class Menu @Inject constructor(
    @ActivityContext var context: Context,
    @Named("food") var product1: BaseProduct,
    @Named("drink") var product2: BaseProduct,
    @Named("desert") var product3: BaseProduct
) : BaseProduct("Menu", Random.nextDouble(1.0, 100.0)) {
    override fun calculatePayment(amount: Double): Double {
        Toast.makeText(context, "Menu created", Toast.LENGTH_SHORT).show()

        return amount * (product1.calculatePayment(1.0) + product2.calculatePayment(1.0) + product3.calculatePayment(1.0))
    }
}