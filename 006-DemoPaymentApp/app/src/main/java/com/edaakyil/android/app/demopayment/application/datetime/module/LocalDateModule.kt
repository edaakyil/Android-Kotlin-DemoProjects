package com.edaakyil.android.app.demopayment.application.module.datetime

import android.content.Context
import android.util.Log
import android.widget.Toast
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDate

@Module
@InstallIn(ActivityComponent::class)
object LocalDateModule {
    @Provides
    fun provideLocalDate(@ApplicationContext context: Context): LocalDate {
        Log.i("date-module", "Created provideLocalDate")
        Toast.makeText(context, "Created provideLocalDate", Toast.LENGTH_LONG).show()

        return LocalDate.now()
    }
}