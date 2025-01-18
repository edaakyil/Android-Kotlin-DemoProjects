package com.edaakyil.app.android.basicviews.model

data class RegisterInfoModel(
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var maritalStatus: Char = 'S',
    var lastEducationDegree: Int = 0,
    var password: String = ""
)
