package com.edaakyil.android.demopaymentapp.application.module.datetime

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime

@Module
@InstallIn(ActivityComponent::class)
object LocalDateTimeModule {
    @Provides
    fun provideLocalDateTime(@ApplicationContext context: Context): LocalDateTime {
        Log.i("datetime-module", "Created provideLocalDateTime")
        Toast.makeText(context, "Created provideLocalDateTime", Toast.LENGTH_LONG).show()

        return LocalDateTime.now()
    }
}