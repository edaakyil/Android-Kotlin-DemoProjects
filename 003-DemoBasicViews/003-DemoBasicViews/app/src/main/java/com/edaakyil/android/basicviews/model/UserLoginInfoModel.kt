package com.edaakyil.android.basicviews.model

import java.time.LocalDateTime

data class UserLoginInfoModel(
    var username: String,
    var password: String,
    var loginDateTime: LocalDateTime = LocalDateTime.now()
)