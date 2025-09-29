package com.edaakyil.android.kotlin.app.sqlite.di

import com.edaakyil.android.kotlin.app.sqlite.dao.IProductDao
import com.edaakyil.android.kotlin.app.sqlite.db.PaymentLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductDaoModule {
    @Provides
    @Singleton
    fun provideProductDao(db: PaymentLocalDatabase): IProductDao = db.productDao()
}