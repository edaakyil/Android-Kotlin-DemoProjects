package com.edaakyil.app.android.basicviews.model

import java.time.LocalDateTime

data class LoginInfoModel(var username: String, var password: String, var loginDateTime: LocalDateTime = LocalDateTime.now())
