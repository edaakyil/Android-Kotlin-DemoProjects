package com.edaakyil.android.demolibraryusageapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.basicviews.data.service.UserService
import com.edaakyil.android.basicviews.data.service.model.UserRegisterInfoModel
import com.edaakyil.android.demolibraryusageapp.constant.USER_INFO
import com.edaakyil.android.demolibraryusageapp.databinding.ActivityRegisterPasswordBinding
import com.edaakyil.java.data.exception.service.DataServiceException

private const val USER_INFO_EXIST_LOG_TAG = "USER_INFO_EXIST"

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityRegisterPasswordBinding
    private lateinit var mUserService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.registerPasswordActivityLinearLayoutMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        mUserService = UserService(this)
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_password)
        initModels()
    }

    private fun initModels() {
        mBinding.activity = this
        mBinding.userRegisterInfo = when {
            Build.VERSION.SDK_INT < 33 -> intent.getSerializableExtra(USER_INFO) as UserRegisterInfoModel
            else -> intent.getSerializableExtra(USER_INFO, UserRegisterInfoModel::class.java)!!
        }
        mBinding.username = resources.getString(R.string.register_password_activity_text_view_username).format(mBinding.userRegisterInfo!!.username)
    }

    private fun userExists(): Boolean {
        var result = false

        try {
            result = mUserService.existsByUsername(mBinding.userRegisterInfo!!.username)
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

        mBinding.userRegisterInfo?.password = password
        mUserService.registerUserInfo(mBinding.userRegisterInfo!!)

        Toast.makeText(this, R.string.user_successfully_registered_prompt, Toast.LENGTH_SHORT).show()

        //finish()  // oguzkaran
    }

    fun onRegisterButtonClicked() {
        val password = mBinding.userRegisterInfo?.password?.trim()
        val confirmPassword = mBinding.confirmPassword?.trim()

        if (password!!.isBlank()) {
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_alert_title)
                .setMessage(R.string.alert_dialog_empty_password_message)
                .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> mBinding.registerPasswordActivityEditTextPassword.text.clear(); mBinding.confirmPassword = "" }
                .create()
                .show()
            return
        }

        if (password == confirmPassword) {
            registerUser(password)
            finish()
        } else
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_alert_title)
                .setMessage(R.string.alert_dialog_confirm_password_message)
                .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> }
                .create()
                .show()

        mBinding.userRegisterInfo?.password = ""
        mBinding.registerPasswordActivityEditTextPassword.text.clear()
        mBinding.confirmPassword = ""
    }

    fun onCloseButtonClicked() = finish()
}