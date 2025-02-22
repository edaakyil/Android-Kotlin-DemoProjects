package com.edaakyil.android.demodatabindingapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.demodatabindingapp.constant.PASSWORD
import com.edaakyil.android.demodatabindingapp.constant.USERNAME
import com.edaakyil.android.demodatabindingapp.data.service.UserService
import com.edaakyil.android.demodatabindingapp.databinding.ActivityMainBinding
import com.edaakyil.android.demodatabindingapp.model.MainActivityActionModel
import com.edaakyil.android.demodatabindingapp.model.UserLoginInfoModel
import com.edaakyil.data.exception.DataServiceException

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mUserService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    private fun initialize() {
        initBinding()
        mUserService = UserService(this)
    }

    private fun initBinding() {
        enableEdgeToEdge()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initModels()
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.mainActivityLinearLayoutMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initModels() {
        mBinding.userLoginInfo = UserLoginInfoModel()
        mBinding.action = MainActivityActionModel(this)
        mBinding.nonAnonymousAreasEnable = true
        mBinding.loginAreaLayoutVisible = View.GONE
        mBinding.statusText = resources.getString(R.string.not_accepted_status_message)
        mBinding.statusSwitchText = resources.getString(R.string.main_activity_switch_accept_text)
    }

    /**
     * checkUser bir dosya açıcak
     */
    private fun checkUser() = mUserService.existsByUsernameAndPassword(mBinding.userLoginInfo!!.username.trim(), mBinding.userLoginInfo!!.password.trim())

    private fun doLogin() {
        try {
            mBinding.statusText = ""

            if (!mBinding.mainActivityCheckBoxAnonymous.isChecked) {
                if (checkUser())
                    Intent(this, ManagementActivity::class.java).apply {
                        putExtra(USERNAME, mBinding.userLoginInfo?.username?.trim())
                        putExtra(PASSWORD, mBinding.userLoginInfo?.password?.trim()) // edaakyil
                        startActivity(this)
                    }
                else
                    AlertDialog.Builder(this)
                        .setTitle(R.string.alert_dialog_user_login_problem_title)
                        .setMessage(R.string.alert_dialog_user_login_problem_message)
                        .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> }
                        .create()
                        .show()
            } else {
                Intent(this, ManagementActivity::class.java).apply { startActivity(this) }
            }
        } catch (ex: DataServiceException) {
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }

    // edaakyil
    fun onAnonymousCheckBoxCheckedChange(checked: Boolean) {
        mBinding.nonAnonymousAreasEnable = !checked
    }

    fun onLoginAreaToggleButtonCheckedChange(checked: Boolean) {
        mBinding.loginAreaLayoutVisible = if (checked) View.VISIBLE else View.GONE
    }

    fun onAcceptSwitchCheckedChange(checked: Boolean) {
        mBinding.loginButtonEnable = checked
        mBinding.statusText = resources.getString(if (checked) R.string.accepted_status_message else R.string.not_accepted_status_message)
        mBinding.statusSwitchText = resources.getString(if (checked) R.string.main_activity_switch_accepted_text else R.string.main_activity_switch_accept_text)
    }

    fun onLoginButtonClicked() {
        doLogin()
    }

    fun onTitleTextClicked() {
        val text: TextView = mBinding.mainActivityTextViewLoginTitle
        text.setTextColor(Color.parseColor("#DC7676"))

        Toast.makeText(this, R.string.main_activity_click_title_prompt, Toast.LENGTH_SHORT).show()
    }

    fun onClearUsernameTextButtonClicked() {
        mBinding.mainActivityEditTextUsername.text.clear()
        //mBinding.userLoginInfo = mBinding.userLoginInfo!!.copy(username = "") //mBinding.userLoginInfo = mBinding.userLoginInfo!!.copy().apply { username = "" }
    }

    fun onClearPasswordTextButtonClicked() {
        mBinding.mainActivityEditTextPassword.text.clear()
        //mBinding.userLoginInfo = mBinding.userLoginInfo!!.copy(password = "") //mBinding.userLoginInfo = mBinding.userLoginInfo!!.copy().apply { password = "" }
    }

    fun onClearAllButtonClicked() {
        onClearUsernameTextButtonClicked()
        onClearPasswordTextButtonClicked()
    }

    fun onRegisterButtonClicked() {
        Intent(this, RegisterInfoActivity::class.java).apply { startActivity(this) }
    }

    fun onCloseButtonClicked() {
        Toast.makeText(this, resources.getString(R.string.close_prompt), Toast.LENGTH_SHORT).show()
        finish()
    }
}

//android:text="@string/not_accepted_status_message"/>