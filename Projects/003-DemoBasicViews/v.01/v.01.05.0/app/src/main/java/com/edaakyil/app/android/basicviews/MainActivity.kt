package com.edaakyil.app.android.basicviews

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var mButtonLogin: Button
    private lateinit var mButtonRegister: Button
    private lateinit var mEditTextUsername: EditText
    private lateinit var mEditTextPassword: EditText
    private lateinit var mTextViewAcceptStatus: TextView
    private lateinit var mLinearLayoutLoginArea: LinearLayout
    private lateinit var mToggleButtonOpenLoginArea: ToggleButton
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private lateinit var mSwitchAccept: Switch

    private fun doLogin() {
        mTextViewAcceptStatus.text = ""

        val username = mEditTextUsername.text.toString()
        val password = mEditTextPassword.text.toString()

        Toast.makeText(this, R.string.login_prompt, Toast.LENGTH_LONG).show()
        Toast.makeText(this, "$username, $password", Toast.LENGTH_LONG).show()

        Intent(this, ManagementActivity::class.java).apply { startActivity(this) }
    }

    private fun loginButtonClickedCallback() {
        doLogin()
    }

    private fun acceptSwitchCheckedChangeCallback(isChecked: Boolean) {
        mButtonLogin.isEnabled = isChecked
        mTextViewAcceptStatus.text = resources.getText(if (isChecked) R.string.accepted_status_message else R.string.not_accepted_status_message)
        mSwitchAccept.text = resources.getText(if (isChecked) R.string.main_activity_switch_accepted_text else R.string.main_activity_switch_accept_text)
    }

    private fun openLoginAreaToggleButtonCheckedChangeCallback(isChecked: Boolean) {
        //Toast.makeText(this, isChecked.toString(), Toast.LENGTH_SHORT).show()
        mLinearLayoutLoginArea.visibility = if (isChecked) View.VISIBLE else View.GONE
    }

    private fun initOpenLoginAreaToggleButton() {
        mToggleButtonOpenLoginArea = findViewById(R.id.mainActivityToggleButtonOpenLoginArea)
        mToggleButtonOpenLoginArea.setOnCheckedChangeListener { _, isChecked -> openLoginAreaToggleButtonCheckedChangeCallback(isChecked) }
    }

    private fun initLoginButton() {
        mButtonLogin = findViewById(R.id.mainActivityButtonLogin)
        mButtonLogin.setOnClickListener { loginButtonClickedCallback() }
    }

    private fun initAcceptSwitch() {
        mSwitchAccept = findViewById(R.id.mainActivitySwitchAccept)
        mSwitchAccept.setOnCheckedChangeListener { _, isChecked -> acceptSwitchCheckedChangeCallback(isChecked) }
    }

    private fun initViews() {
        mButtonRegister = findViewById<Button?>(R.id.mainActivityButtonRegister)
        mEditTextUsername = findViewById(R.id.mainActivityEditTextUsername)
        mEditTextPassword = findViewById(R.id.mainActivityEditTextPassword)
        mTextViewAcceptStatus = findViewById(R.id.mainActivityTextViewAcceptStatus)
        mLinearLayoutLoginArea = findViewById<LinearLayout?>(R.id.mainActivityLinearLayoutLoginArea).apply { visibility = View.GONE }

        initOpenLoginAreaToggleButton()
        initAcceptSwitch()
        initLoginButton()
    }

    //private fun initialize() = initViews()
    private fun initialize() {
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainActivityLinearLayoutMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialize()
    }

    fun onTitleTextClicked(view: View) {
        val text: TextView = findViewById(R.id.mainActivityTextViewLoginTitle)
        text.setTextColor(Color.parseColor("#DC7676"))

        Toast.makeText(this, R.string.main_activity_click_title_prompt, Toast.LENGTH_SHORT).show()
    }

    //fun onClearUsernameTextButtonClicked(view: View) = mEditTextUsername.setText("")
    fun onClearUsernameTextButtonClicked(view: View) {
        //mEditTextUsername.setText("")
        mEditTextUsername.text.clear()
    }

    //fun onClearPasswordTextButtonClicked(view: View) = mEditTextPassword.setText("")
    fun onClearPasswordTextButtonClicked(view: View) {
        //mEditTextPassword.text.clear()
        mEditTextPassword.setText("")
    }

    fun onClearAllButtonClicked(view: View) {
        onClearUsernameTextButtonClicked(view)
        onClearPasswordTextButtonClicked(view)
        mTextViewAcceptStatus.text = ""
    }

    fun onRegisterButtonClicked(vew: View) {
        Intent(this,RegisterActivity::class.java).apply { startActivity(this) }
    }

    fun onCloseButtonClicked(view: View) {
        // Toast.makeText(this, R.string.close_prompt, Toast.LENGTH_LONG).show()
        Toast.makeText(this, resources.getString(R.string.close_prompt), Toast.LENGTH_LONG).show()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Destroying...", Toast.LENGTH_LONG).show()
    }
}