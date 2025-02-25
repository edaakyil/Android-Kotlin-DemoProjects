package com.edaakyil.android.basicviews.data.service.model

import java.io.Serializable

data class UserRegisterInfoModel(
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var maritalStatus: Char = 'S',
    var lastEducationDegree: Int = 0,
    var password: String = ""
) : Serializable {
    override fun toString() = username
}
