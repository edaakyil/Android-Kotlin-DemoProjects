package com.edaakyil.android.basicviews.data.service.model

import java.time.LocalDateTime

data class UserLoginInfoModel(
    var username: String = "",
    var password: String = "",
    var loginDateTime: LocalDateTime = LocalDateTime.now()
)