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
import com.edaakyil.android.basicviews.constant.USERS_FILE_PATH
import com.edaakyil.android.basicviews.constant.USERS_FORMAT
import com.edaakyil.android.basicviews.model.UserInfoModel
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

private const val REGISTER_USER_INFO_LOG_TAG = "REGISTER_USER_INFO"
private const val USER_INFO_EXIST_LOG_TAG = "USER_INFO_EXIST"

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
        mUserInfo = when {
            Build.VERSION.SDK_INT < 33 -> intent.getSerializableExtra(USER_INFO) as UserInfoModel
            else -> intent.getSerializableExtra(USER_INFO, UserInfoModel::class.java)!!
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
        mTextViewUsername.text = resources.getString(R.string.register_password_activity_text_view_username).format(mUserInfo.username)
    }

    private fun userExistsCallback(fis: FileInputStream): Boolean {
        var result = false

        try {
            // EOFException oluşana kadar döngüye gireceğiz ve her adımda readObject yapıcaz
            // EOFException, dosyanın sonuna gelmek yani EOFException ile dosyanın sonuna gelinip gelinmediği kontrol ediliyor
            // EOFException'nın fırlatılırsa bu dosyanın sonuna gelindi demektir yani okuma bitti demek
            while (true) {
                // Her adımda ObjectInputStream'in yaratılması gerekiyor
                val ois = ObjectInputStream(fis)
                val userInfo = ois.readObject() as UserInfoModel

                if (userInfo.username == mUserInfo.username) { // Eğer koşul doğruysa demek ki bizim user'ımız mevcut demektir yani bu user daha önce kaydedilmiş
                    result = true
                    break
                }
            }
        } catch (_: EOFException) {
            // Dosyanın sonuna gelindiğinde yani EOFException fırlatıldıdığında dönğü sonlanıcak
        }

        return result
    }

    private fun userExists(): Boolean {
        var result = false

        try {
            result = FileInputStream(File(filesDir, USERS_FILE_PATH)).use(::userExistsCallback)  // use, içerisindeki ifadenin değerine geri döner
        } catch (ex: IOException) {
            Log.e(USER_INFO_EXIST_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(USER_INFO_EXIST_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }

        return result
    }

    // Save işleminde Serialization kullanacağız
    private fun registerUserInfo() {
        try {
            // Her registerUserInfo çağrıldığında data'lar farklı ObjectOutputStream'ler kullanılarak yazılıyor.
            // Böylelikle her bir data farklı ObjectOutputStream'ler kullanılarak yaratılıyor.
            // Yani her registerUserInfo çağrısında yeni bir ObjectOutputStream yaratılıyor
            ObjectOutputStream(FileOutputStream(File(filesDir, USERS_FILE_PATH), true)).use { it.writeObject(mUserInfo) }
            File(filesDir, USERS_FORMAT.format("${mUserInfo.username}.txt")).delete()
        } catch (ex: IOException) {
            Log.e(REGISTER_USER_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(REGISTER_USER_INFO_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }

    private fun registerUser(password: String) {
        if (userExists()) {
            Toast.makeText(this, R.string.user_already_registered_prompt, Toast.LENGTH_SHORT).show()
            return
        }

        mUserInfo.password = password
        registerUserInfo()
        Toast.makeText(this, R.string.user_successfully_registered_prompt, Toast.LENGTH_SHORT).show()
    }

    fun onRegisterButtonClicked(view: View) {
        val password = mEditTextPassword.text.toString()
        val confirmPassword = mEditTextConfirmPassword.text.toString()

        if (password.isBlank()) {
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_title_alert)
                .setMessage(R.string.alert_dialog_empty_password_message)
                .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> mEditTextConfirmPassword.text.clear() }
                .create()
                .show()
            return
        }

        if (password == confirmPassword)
            registerUser(password)
        else
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_title_alert)
                .setMessage(R.string.alert_dialog_confirm_password_message)
                .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> }
                .create()
                .show()

        mEditTextPassword.text.clear()
        mEditTextConfirmPassword.text.clear()
    }

    fun onCloseButtonClicked(view: View) = finish()
}