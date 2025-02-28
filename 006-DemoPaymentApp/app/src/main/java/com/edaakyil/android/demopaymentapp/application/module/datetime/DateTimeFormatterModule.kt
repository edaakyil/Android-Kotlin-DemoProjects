package com.edaakyil.android.demopaymentapp.application.module.datetime

import android.content.Context
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateTimeFormatterModule {
    @Provides
    @Singleton
    fun provideDateTimeFormatter(@ApplicationContext context: Context): DateTimeFormatter {
        Toast.makeText(context, "Created provideDateTimeFormatter", Toast.LENGTH_SHORT).show()

        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    }
}