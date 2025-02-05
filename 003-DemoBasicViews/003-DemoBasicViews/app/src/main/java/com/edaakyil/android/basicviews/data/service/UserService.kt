package com.edaakyil.android.basicviews.data.service

import android.content.Context
import com.edaakyil.android.basicviews.constant.USERS_FORMAT
import com.edaakyil.android.basicviews.model.UserRegisterInfoModel
import com.edaakyil.data.exception.DataServiceException
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
        try {
            val username = userRegisterInfo.username
            BufferedWriter(OutputStreamWriter(FileOutputStream(File(mContext.filesDir, USERS_FORMAT.format("$username.txt"))), StandardCharsets.UTF_8))
                .use { writeUserRegisterInfo(it, userRegisterInfo) }
        } catch (ex: IOException) {
            throw DataServiceException("UserService.saveUserData", ex)
        }
    }

    private fun readRegisteringUserInfo(br: BufferedReader, username: String): UserRegisterInfoModel {
        val str = br.readLine() ?: throw IOException()  // line(satır) okuma
        val info = str.split(DELIMITER)  // line'ı split etme

        return UserRegisterInfoModel(username, info[1], info[2], info[3][0], info[4].toInt())
    }

    fun findByUsername(username: String): UserRegisterInfoModel? {
        try {
            val file = File(mContext.filesDir, USERS_FORMAT.format("$username.txt"))

            return if (file.exists()) BufferedReader(InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8)).use { readRegisteringUserInfo(it, username) } else null
        } catch (ex: IOException) {
            throw DataServiceException("UserService.findByUsername", ex)
        }
    }

    fun registerUser(userRegisterInfo: UserRegisterInfoModel) {
        try {
            TODO("Not yet implemented!...")
        } catch (ex: IOException) {
            throw DataServiceException("UserService.registerUser", ex)
        }
    }

    fun existsByUsername(username: String): Boolean {
        try {
            return File(mContext.filesDir, USERS_FORMAT.format("$username.txt")).exists()
        } catch (ex: IOException) {
            throw DataServiceException("UserService.existsByUsername", ex)
        }
    }

    fun existsByUsernameAndPassword(username: String, password: String): Boolean {
        try {
            TODO("Not yet implemented!...")
        } catch (ex: IOException) {
            throw DataServiceException("UserService.existsByUsernameAndPassword", ex)
        }
    }
}