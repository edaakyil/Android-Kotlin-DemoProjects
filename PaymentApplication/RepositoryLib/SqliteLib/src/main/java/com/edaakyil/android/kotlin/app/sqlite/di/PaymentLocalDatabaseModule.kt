package com.edaakyil.android.kotlin.app.sqlite.di

import android.content.Context
import androidx.room.Room
import com.edaakyil.android.kotlin.app.sqlite.db.PaymentLocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PaymentLocalDatabaseModule {
    @Provides
    @Singleton
    fun providePaymentLocalDatabase(@ApplicationContext context: Context): PaymentLocalDatabase =
        Room.databaseBuilder(context, PaymentLocalDatabase::class.java, "payment-db").build()
}