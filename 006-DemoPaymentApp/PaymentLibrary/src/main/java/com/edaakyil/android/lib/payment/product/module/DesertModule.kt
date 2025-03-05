package com.edaakyil.android.lib.payment.product.module

import com.edaakyil.android.lib.payment.product.BaseProduct
import com.edaakyil.android.lib.payment.product.Desert
import com.edaakyil.android.lib.payment.product.constant.name.DESERT
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
abstract class DesertModule {
    @Binds
    @Named(DESERT)
    abstract fun bindDesertProduct(desert: Desert): BaseProduct
}