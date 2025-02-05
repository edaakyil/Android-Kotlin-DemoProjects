package com.edaakyil.android.basicviews.data.service

import android.content.Context
import com.edaakyil.android.basicviews.constant.USERS_FORMAT
import com.edaakyil.android.basicviews.model.UserRegisterInfoModel
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

private const val DELIMITER = ":"

class UserService(context: Context) {
    private val mContext = context

    private fun writeUserRegisterInfo(bw: BufferedWriter, userRegisterInfo: UserRegisterInfoModel) {
        bw.write("${userRegisterInfo.username}$DELIMITER")
        bw.write("${userRegisterInfo.name}$DELIMITER")
        bw.write("${userRegisterInfo.email}$DELIMITER")
        bw.write("${userRegisterInfo.maritalStatus}$DELIMITER")
        bw.write("${userRegisterInfo.lastEducationDegree}")
    }

    fun saveUserData(userRegisterInfo: UserRegisterInfoModel) {
        val username = userRegisterInfo.username
        BufferedWriter(OutputStreamWriter(FileOutputStream(File(mContext.filesDir, USERS_FORMAT.format("$username.txt"))), StandardCharsets.UTF_8))
            .use { writeUserRegisterInfo(it, userRegisterInfo) }
    }

    private fun readRegisteringUserInfo(br: BufferedReader, username: String): UserRegisterInfoModel {
        val str = br.readLine() ?: throw IOException()  // line(satır) okuma
        val info = str.split(DELIMITER)  // line'ı split etme

        return UserRegisterInfoModel(username, info[1], info[2], info[3][0], info[4].toInt())
    }

    fun findByUsername(username: String): UserRegisterInfoModel? {
        val file = File(mContext.filesDir, USERS_FORMAT.format("$username.txt"))

        return if (file.exists()) BufferedReader(InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8)).use { readRegisteringUserInfo(it, username) } else null
    }

    fun registerUser(userRegisterInfo: UserRegisterInfoModel) {

    }

    fun existsByUsername(username: String): Boolean {
        TODO()
    }

    fun existsByUsernameAndPassword(username: String, password: String): Boolean {
        TODO()
    }
}