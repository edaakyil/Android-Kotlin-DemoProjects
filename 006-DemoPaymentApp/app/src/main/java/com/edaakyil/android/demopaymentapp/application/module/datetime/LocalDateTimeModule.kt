package com.edaakyil.android.demopaymentapp.application.module.datetime

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import java.time.LocalDateTime

@Module
@InstallIn(ActivityComponent::class)
object LocalDateTimeModule {
    @Provides
    fun provideLocalDateTime(): LocalDateTime = LocalDateTime.now()
}