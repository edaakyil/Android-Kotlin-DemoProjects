package com.edaakyil.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edaakyil.android.basicviews.constant.REGISTER_INFO
import com.edaakyil.android.basicviews.model.UserInfoModel

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var mTextViewUsername: TextView
    private lateinit var mEditTextPassword: EditText
    private lateinit var mEditTextConfirmPassword: EditText
    private lateinit var mUserInfo: UserInfoModel

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
        mUserInfo = if (android.os.Build.VERSION.SDK_INT < 33 ) intent.getSerializableExtra(REGISTER_INFO) as UserInfoModel else intent.getSerializableExtra(REGISTER_INFO, UserInfoModel::class.java)!!
        initViews()
    }

    private fun initViews() {
        initTextViewUsername()
        mEditTextPassword = findViewById(R.id.registerPasswordActivityEditTextPassword)
        mEditTextConfirmPassword = findViewById(R.id.registerPasswordActivityEditTextConfirmPassword)
    }

    private fun initTextViewUsername() {
        mTextViewUsername = findViewById(R.id.registerPasswordActivityTextViewUsername)
        mTextViewUsername.text = resources.getString(R.string.register_password_activity_text_view_username).format(mUserInfo.username)
    }

    fun onRegisterButtonClicked(view: View) {}

    fun onCloseButtonClicked(view: View) = finish()
}