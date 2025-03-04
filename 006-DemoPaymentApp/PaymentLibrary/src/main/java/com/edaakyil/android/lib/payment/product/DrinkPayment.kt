package com.edaakyil.android.lib.payment.product

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import kotlin.random.Random

class DrinkPayment @Inject constructor(@ActivityContext var context: Context) : BaseProduct("Drink", Random.nextDouble(1.0, 100.0)), IProductPayment {
    override fun calculatePayment(amount: Double): Double {
        Toast.makeText(context, "Name: $name:\nAmount -> $amount,\nUnit Price -> $unitPrice", Toast.LENGTH_SHORT).show()

        return amount * unitPrice * 1.9
    }
}