package com.edaakyil.android.kotlin.app.randomuser.api.di

import com.edaakyil.android.kotlin.app.randomuser.api.randomuser.me.dto.RandomUserInfo
import com.edaakyil.android.kotlin.app.randomuser.api.randomuser.me.service.IRandomUserInfoService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Callback
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object RandomUserServiceModule {
    fun provideRandomUserService(retrofit: Retrofit): IRandomUserInfoService = retrofit.create(Callback<RandomUserInfo>)
}