package com.edaakyil.android.lib.payment.product

import android.content.Context
import android.widget.Toast
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import kotlin.random.Random

@ActivityScoped
class Desert @Inject constructor(@ActivityContext var context: Context) : BaseProduct("Desert", Random.nextDouble(1.0, 100.0)) {
    override fun calculatePayment(amount: Double): Double {
        Toast.makeText(context, "Name: $name\nAmount -> $amount\nUnit Price -> $unitPrice", Toast.LENGTH_SHORT).show()

        return amount * unitPrice * 1.6
    }
}