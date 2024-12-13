package com.edaakyil.app.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edaakyil.app.android.basicviews.constant.REGISTER_INFO
import com.edaakyil.app.android.basicviews.model.RegisterInfoModel

class RegisterPasswordActivity : AppCompatActivity() {
    private lateinit var mTextViewInfo: TextView
    private lateinit var mEditTextPassword: EditText
    private lateinit var mEditTextConfirmPassword: EditText
    private lateinit var mRegisterInfo: RegisterInfoModel

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
        // Versiyon kontrolü yapmak için Build sınıfını kullanıyoruz
        mRegisterInfo = if (android.os.Build.VERSION.SDK_INT < 33) intent.getSerializableExtra(REGISTER_INFO) as RegisterInfoModel
            else intent.getSerializableExtra(REGISTER_INFO, RegisterInfoModel::class.java)!!

        initViews()
    }

    private fun initViews() {
        mEditTextPassword = findViewById(R.id.registerPasswordActivityEditTextPassword)
        mEditTextConfirmPassword = findViewById(R.id.registerPasswordActivityEditTextConfirmPassword)
        initTexViewInfo()
    }

    private fun initTexViewInfo() {
        mTextViewInfo = findViewById(R.id.registerPasswordActivityTextViewInfo)
        mTextViewInfo.text = resources.getString(R.string.register_password_activity_text_view_info).format(mRegisterInfo.username)
    }

    // edaakyil
    private fun confirmPasswordAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_close_title)
            .setMessage(R.string.alert_dialog_confirm_password_message)
            .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> mEditTextPassword.text.clear(); mEditTextConfirmPassword.text.clear()}
            .create()
            .show()
    }

    fun onRegisterButtonClicked(view: View) {
        val password = mEditTextPassword.text.toString()
        val confirmPassword = mEditTextConfirmPassword.text.toString()
        if (password == confirmPassword) {
            mRegisterInfo.password = password
            Toast.makeText(this, mRegisterInfo.toString(), Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, R.string.alert_dialog_confirm_password_message, Toast.LENGTH_SHORT).show()
            confirmPasswordAlertDialog()
            //return
        }
    }

    fun onCloseButtonClicked(view: View) = finish()
}