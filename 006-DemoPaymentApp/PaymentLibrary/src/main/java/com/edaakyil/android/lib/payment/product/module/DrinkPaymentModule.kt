package com.edaakyil.android.lib.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.edaakyil.android.lib.payment.product.Drink
import com.edaakyil.android.lib.payment.product.IProductPayment
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class DrinkPaymentModule {
    @Binds
    @Named("drinkPayment")
    abstract fun bindDrinkPayment(drink: Drink): IProductPayment
}