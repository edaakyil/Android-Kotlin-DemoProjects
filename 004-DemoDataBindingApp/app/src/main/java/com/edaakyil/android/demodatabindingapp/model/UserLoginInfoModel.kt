package com.edaakyil.android.demodatabindingapp.model

import java.time.LocalDateTime

data class UserLoginInfoModel(
    var username: String = "",
    var password: String = "",
    var loginDateTime: LocalDateTime = LocalDateTime.now()
)