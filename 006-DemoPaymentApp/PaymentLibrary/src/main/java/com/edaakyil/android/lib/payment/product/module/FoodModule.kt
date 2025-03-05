package com.edaakyil.android.lib.payment.product.module

import com.edaakyil.android.lib.payment.product.BaseProduct
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.edaakyil.android.lib.payment.product.Food
import com.edaakyil.android.lib.payment.product.constant.name.FOOD
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class FoodModule {
    @Binds
    @Named(FOOD)
    abstract fun bindFood(food: Food) : BaseProduct
}