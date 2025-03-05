package com.edaakyil.android.lib.payment.product.module

import com.edaakyil.android.lib.payment.product.BaseProduct
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.edaakyil.android.lib.payment.product.Drink
import com.edaakyil.android.lib.payment.product.constant.name.DRINK
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class DrinkModule {
    @Binds
    @Named(DRINK)
    abstract fun bindDrinkProduct(drink: Drink): BaseProduct
}