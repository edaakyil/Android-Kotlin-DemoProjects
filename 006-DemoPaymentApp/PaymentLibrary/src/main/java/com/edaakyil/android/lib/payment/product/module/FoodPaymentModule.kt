package com.edaakyil.android.lib.payment.product.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.edaakyil.android.lib.payment.product.Food
import com.edaakyil.android.lib.payment.product.IProductPayment
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class FoodPaymentModule {
    @Binds
    @Named("foodPayment")
    abstract fun bindFoodPayment(food: Food) : IProductPayment
}