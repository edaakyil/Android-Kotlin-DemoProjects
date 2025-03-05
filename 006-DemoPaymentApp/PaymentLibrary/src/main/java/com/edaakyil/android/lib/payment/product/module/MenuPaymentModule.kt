package com.edaakyil.android.lib.payment.product.module

import com.edaakyil.android.lib.payment.product.IProductPayment
import com.edaakyil.android.lib.payment.product.Menu
import com.edaakyil.android.lib.payment.product.constant.name.MENU_PAYMENT
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class MenuPaymentModule {
    @Binds
    @Named(MENU_PAYMENT)
    abstract fun bindMenuPayment(menu: Menu): IProductPayment
}