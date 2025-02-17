package com.edaakyil.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Build
import android.util.Log
import android.widget.Toast
import com.edaakyil.android.basicviews.constant.USER_INFO
import com.edaakyil.android.basicviews.constant.USERS_FORMAT
import com.edaakyil.android.basicviews.data.service.UserService
import com.edaakyil.android.basicviews.model.UserRegisterInfoModel
import com.edaakyil.data.exception.DataServiceException
import java.io.File

private const val USER_INFO_EXIST_LOG_TAG = "USER_INFO_EXIST"

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var mTextViewUsername: TextView
    private lateinit var mEditTextPassword: EditText
    private lateinit var mEditTextConfirmPassword: EditText
    private lateinit var mUserRegisterInfo: UserRegisterInfoModel
    private lateinit var mUserService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.registerPasswordActivityLinearLayoutMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialize()
    }

    private fun initialize() {
        mUserService = UserService(this)
        mUserRegisterInfo = when {
            Build.VERSION.SDK_INT < 33 -> intent.getSerializableExtra(USER_INFO) as UserRegisterInfoModel
            else -> intent.getSerializableExtra(USER_INFO, UserRegisterInfoModel::class.java)!!
        }

        initViews()
    }

    private fun initViews() {
        initTextViewUsername()
        mEditTextPassword = findViewById(R.id.registerPasswordActivityEditTextPassword)
        mEditTextConfirmPassword = findViewById(R.id.registerPasswordActivityEditTextConfirmPassword)
    }

    private fun initTextViewUsername() {
        mTextViewUsername = findViewById(R.id.registerPasswordActivityTextViewUsername)
        mTextViewUsername.text = resources.getString(R.string.register_password_activity_text_view_username).format(mUserRegisterInfo.username)
    }

    private fun userExists(): Boolean {
        var result = false

        try {
            result = mUserService.existsByUsername(mUserRegisterInfo.username)
        } catch (ex: DataServiceException) {
            Log.e(USER_INFO_EXIST_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(USER_INFO_EXIST_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }

        return result
    }

    private fun registerUser(password: String) {
        if (userExists()) {
            Toast.makeText(this, R.string.user_already_registered_prompt, Toast.LENGTH_SHORT).show()
            return
        }

        mUserRegisterInfo.password = password
        mUserService.registerUserInfo(mUserRegisterInfo)

        Toast.makeText(this, R.string.user_successfully_registered_prompt, Toast.LENGTH_SHORT).show()
    }

    fun onRegisterButtonClicked(view: View) {
        val password = mEditTextPassword.text.toString()
        val confirmPassword = mEditTextConfirmPassword.text.toString()

        if (password.isBlank()) {
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_alert_title)
                .setMessage(R.string.alert_dialog_empty_password_message)
                .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> mEditTextConfirmPassword.text.clear() }
                .create()
                .show()
            return
        }

        if (password == confirmPassword) {
            registerUser(password)
            File(filesDir, USERS_FORMAT.format("${mUserRegisterInfo.username}.txt")).delete()  // edaakyil
            finish()
        } else
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_alert_title)
                .setMessage(R.string.alert_dialog_confirm_password_message)
                .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> }
                .create()
                .show()

        mEditTextPassword.text.clear()
        mEditTextConfirmPassword.text.clear()
    }

    fun onCloseButtonClicked(view: View) = finish()
}