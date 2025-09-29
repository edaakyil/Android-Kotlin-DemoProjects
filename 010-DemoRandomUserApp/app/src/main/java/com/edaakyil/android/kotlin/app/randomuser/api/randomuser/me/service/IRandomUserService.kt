package com.edaakyil.android.kotlin.app.randomuser.api.randomuser.me.service

import com.edaakyil.android.kotlin.app.randomuser.api.randomuser.me.dto.Info
import com.edaakyil.android.kotlin.app.randomuser.api.randomuser.me.dto.RandomUser
import com.edaakyil.android.kotlin.app.randomuser.api.randomuser.me.dto.RandomUserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRandomUserInfoService {
    @GET
    fun getRandomUserInfo(
        @Query("results") users: List<RandomUser>,
        @Query("info") info: Info
    ): Call<RandomUserInfo>
}