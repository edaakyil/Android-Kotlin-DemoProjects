package com.edaakyil.android.demopaymentapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import javax.inject.Inject
import com.edaakyil.android.demopaymentapp.databinding.ActivityPaymentBinding
import com.edaakyil.android.lib.payment.product.IProductPayment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Named

@AndroidEntryPoint
class PaymentActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityPaymentBinding

    @Inject
    @Named("foodPayment")
    lateinit var paymentFood: IProductPayment

    @Inject
    @Named("drinkPayment")
    lateinit var paymentDrink: IProductPayment

    @Inject
    @Named("menuPayment")
    lateinit var paymentMenu: IProductPayment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()

        Toast.makeText(this, "Food -> ${paymentFood.calculatePayment(200.9)}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Drink -> ${paymentDrink.calculatePayment(1.0)}", Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Menu -> ${paymentMenu.calculatePayment(2.0)}", Toast.LENGTH_SHORT).show()
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.paymentActivityMainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
    }
}