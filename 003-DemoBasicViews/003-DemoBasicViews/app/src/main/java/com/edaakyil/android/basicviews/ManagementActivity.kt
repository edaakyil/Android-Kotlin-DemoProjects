package com.edaakyil.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edaakyil.android.basicviews.constant.PASSWORD
import com.edaakyil.android.basicviews.constant.USERNAME

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
        mTextViewUsername.text = "Username: " + (intent.getStringExtra(USERNAME) ?: resources.getText(R.string.anonymous))

        mTextViewPassword = findViewById(R.id.managementActivityTextViewPassword) // edaakyil
        mTextViewPassword.text = "Password: " + (intent.getStringExtra(PASSWORD) ?: resources.getText(R.string.anonymous)) // edaakyil
    }

    fun onUsersButtonClicked(view: View) {

    }

    fun onUserOperationsButtonClicked(view: View) {

    }

    fun onCloseButtonClicked(view: View) = finish()
}
