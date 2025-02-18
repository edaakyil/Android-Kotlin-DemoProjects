package com.edaakyil.android.demodatabindingapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edaakyil.android.demodatabindingapp.constant.PASSWORD
import com.edaakyil.android.demodatabindingapp.constant.USERNAME

class ManagementActivity : AppCompatActivity() {
    private lateinit var mTextViewUsername: TextView
    private lateinit var mTextViewPassword: TextView // edaakyil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_management)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.managementActivityLinearLayoutMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initialize()
    }

    private fun initialize() {
        initViews()
    }

    private fun initViews() {
        initUserInfo()
    }

    private fun initUserInfo() {
        mTextViewUsername = findViewById(R.id.managementActivityTextViewUsername)
        mTextViewUsername.text = resources.getString(R.string.username, intent.getStringExtra(USERNAME) ?: R.string.anonymous)
        //mTextViewUsername.text = "Username: " + (intent.getStringExtra(USERNAME) ?: resources.getText(R.string.anonymous))

        mTextViewPassword = findViewById(R.id.managementActivityTextViewPassword) // edaakyil
        mTextViewPassword.text = resources.getString(R.string.password, intent.getStringExtra(PASSWORD) ?: R.string.anonymous) // edaakyil
        //mTextViewPassword.text = "Password: " + (intent.getStringExtra(PASSWORD) ?: resources.getText(R.string.anonymous)) // edaakyil
    }

    fun onUsersButtonClicked(view: View) {
        Intent(this, UsersActivity::class.java).apply { startActivity(this) }
    }

    fun onUserOperationsButtonClicked(view: View) {

    }

    fun onCloseButtonClicked(view: View) = finish()
}
