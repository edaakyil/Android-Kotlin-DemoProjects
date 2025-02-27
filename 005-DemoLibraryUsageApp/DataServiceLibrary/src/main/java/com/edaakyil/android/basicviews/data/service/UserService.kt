package com.edaakyil.android.basicviews.data.service

import android.content.Context
import android.util.Log
import com.edaakyil.android.basicviews.data.service.constant.USERS_FILE_PATH
import com.edaakyil.android.basicviews.data.service.constant.USERS_FORMAT
import com.edaakyil.android.basicviews.data.service.model.UserRegisterInfoModel
import com.edaakyil.android.basicviews.data.service.model.UserModel
import com.edaakyil.java.data.exception.service.DataServiceException
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

private const val REGISTER_USER_INFO_LOG_TAG = "REGISTER_USER_INFO"
private const val DELIMITER = ":"

class UserService(context: Context) {
    private val mContext = context

    /**
     * For Register user
     *
     * It controls whether user was saved
     */
    fun isUserSaved(username: String): Boolean {
        try {
            return File(mContext.filesDir, USERS_FORMAT.format("$username.txt")).exists()
        } catch (ex: IOException) {
            Log.e(REGISTER_USER_INFO_LOG_TAG, ex.message ?: "")
            throw DataServiceException("UserService.existsByUsername", ex)
        }
    }

    private fun userFilterCallback(fis: FileInputStream, predicate: (UserRegisterInfoModel) -> Boolean): Boolean {
        var result = false

        try {
            while (true) {
                val ois = ObjectInputStream(fis)
                val userInfo = ois.readObject() as UserRegisterInfoModel

                if (predicate(userInfo)) {
                    result = true
                    break
                }
            }
        } catch (_: EOFException) {

        }

        return result
    }

    /**
     * It filters users by their qualification desired
     */
    private fun findUsersBy(fis: FileInputStream, predicate: (UserRegisterInfoModel) -> Boolean): List<UserRegisterInfoModel> {
        val users = ArrayList<UserRegisterInfoModel>()

        try {
            while (true) {
                val ois = ObjectInputStream(fis)
                val ri = ois.readObject() as UserRegisterInfoModel

                if (predicate(ri))
                    users.add(ri)
            }
        } catch (_: EOFException) {

        }

        return users
    }

    fun findByUsername(username: String): UserRegisterInfoModel? {
        try {
            val file = File(mContext.filesDir, USERS_FORMAT.format("$username.txt"))

            return if (file.exists()) BufferedReader(InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8)).use { readRegisteringUserInfo(it, username) } else null
        } catch (_: FileNotFoundException) {
            return null
        } catch (ex: IOException) {
            throw DataServiceException("UserService.findByUsername", ex)
        }
    }

    fun findUsers(count: Int): List<UserModel> {
        val users = ArrayList<UserModel>()
        var n = 0

        if (count <= 0)
            return users

        try {
            val fis = FileInputStream(File(mContext.filesDir, USERS_FILE_PATH))

            while (true) {
                val ois = ObjectInputStream(fis)
                val ri = ois.readObject() as UserRegisterInfoModel

                if (n++ == count)
                    break

                users.add(UserModel().apply { username = ri.username; name = ri.name; email = ri.email; maritalStatus = ri.maritalStatus; lastEducationDegree = ri.lastEducationDegree })
            }
        } catch (_: FileNotFoundException) {

        } catch (_: EOFException) {

        }

        return users
    }

    /**
     * For Register user
     */
    fun existsByUsername(username: String): Boolean {
        return try {
            FileInputStream(File(mContext.filesDir, USERS_FILE_PATH)).use { userFilterCallback(it) { user -> user.username == username } }
        } catch (_: FileNotFoundException) {
            false
        } catch (ex: IOException) {
            throw DataServiceException("UserService.existsByUsername", ex)
        }
    }

    /**
     * For Login
     */
    fun existsByUsernameAndPassword(username: String, password: String): Boolean {
        return try {
            FileInputStream(File(mContext.filesDir, USERS_FILE_PATH)).use { userFilterCallback(it) { user -> user.username == username && user.password == password } }
        } catch (_: FileNotFoundException) {
            false
        } catch (ex: IOException) {
            throw DataServiceException("UserService.existsByUsernameAndPassword", ex)
        }
    }

    private fun readRegisteringUserInfo(br: BufferedReader, username: String): UserRegisterInfoModel {
        val str = br.readLine() ?: throw IOException()  // line(satır) okuma
        val info = str.split(DELIMITER)  // line'ı split etme

        return UserRegisterInfoModel(info[1], username, info[2], info[3][0], info[4].toInt())
    }

    private fun writeUserRegisterInfo(bw: BufferedWriter, userRegisterInfo: UserRegisterInfoModel) {
        bw.write("${userRegisterInfo.username.trim()}$DELIMITER")
        bw.write("${userRegisterInfo.name.trim()}$DELIMITER")
        bw.write("${userRegisterInfo.email.trim()}$DELIMITER")
        bw.write("${userRegisterInfo.maritalStatus}$DELIMITER")
        bw.write("${userRegisterInfo.lastEducationDegree}")
    }

    fun saveUserData(userRegisterInfo: UserRegisterInfoModel) {
        try {
            val username = userRegisterInfo.username.trim()
            BufferedWriter(OutputStreamWriter(FileOutputStream(File(mContext.filesDir, USERS_FORMAT.format("$username.txt"))), StandardCharsets.UTF_8))
                .use { writeUserRegisterInfo(it, userRegisterInfo) }
        } catch (ex: IOException) {
            throw DataServiceException("UserService.saveUserData", ex)
        }
    }

    fun registerUserInfo(userRegisterInfo: UserRegisterInfoModel) {
        try {
            ObjectOutputStream(FileOutputStream(File(mContext.filesDir, USERS_FILE_PATH), true)).use { it.writeObject(userRegisterInfo) }
            File(mContext.filesDir, USERS_FORMAT.format("${userRegisterInfo.username}.txt")).delete()
        } catch (ex: IOException) {
            throw DataServiceException("UserService.registerUser", ex)
        }
    }

    fun updateUser(user: UserRegisterInfoModel) {
        TODO("Not yet implemented!...")
    }
}