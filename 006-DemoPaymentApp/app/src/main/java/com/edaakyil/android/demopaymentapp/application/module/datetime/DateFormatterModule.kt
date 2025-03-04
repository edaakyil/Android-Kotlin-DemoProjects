package com.edaakyil.android.demopaymentapp.application.module.datetime

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.edaakyil.android.demopaymentapp.application.module.datetime.annotation.DateFormatterInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.time.format.DateTimeFormatter
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DateFormatterModule {
    @Provides
    @Singleton
    @DateFormatterInterceptor
    fun provideDateFormatter(@ApplicationContext context: Context): DateTimeFormatter {
        Log.i("date-formatter-module", "Created provideDateFormatter")
        Toast.makeText(context, "Created provideDateFormatter", Toast.LENGTH_SHORT).show()

        return DateTimeFormatter.ofPattern("dd/MM/yyyy")
    }
}