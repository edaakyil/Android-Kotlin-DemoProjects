package com.edaakyil.android.demopaymentapp.application.module.datetime

import android.content.Context
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
        Toast.makeText(context, "Created provideLocalDateTime", Toast.LENGTH_SHORT).show()

        return LocalDateTime.now()
    }
}