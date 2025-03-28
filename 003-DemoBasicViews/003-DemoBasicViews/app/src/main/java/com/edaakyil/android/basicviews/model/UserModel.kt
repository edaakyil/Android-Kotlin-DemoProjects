package com.edaakyil.android.basicviews.model

import java.io.Serializable

data class UserModel(
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var maritalStatus: Char = 'S',
    var lastEducationDegree: Int = 0,
) : Serializable {
    override fun toString() = username
}

