package com.edaakyil.android.lib.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.edaakyil.android.lib.payment.product.FoodPayment
import com.edaakyil.android.lib.payment.product.IProductPayment


@Module
@InstallIn(ActivityComponent::class)
abstract class FoodPaymentModule {
    @Binds
    abstract fun bindFoodPayment(foodPayment: FoodPayment) : IProductPayment
}