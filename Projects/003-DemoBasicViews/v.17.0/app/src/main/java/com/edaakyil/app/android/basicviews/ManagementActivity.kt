package com.edaakyil.app.android.basicviews

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.edaakyil.app.android.basicviews.constant.*

class ManagementActivity : AppCompatActivity() {
    private lateinit var mTextViewUsername: TextView
    private lateinit var mTextViewPassword: TextView // edaakyil

    private fun initUserInfo() {
        mTextViewUsername = findViewById(R.id.managementActivityTextViewUsername)
        mTextViewUsername.text = "Username: " + (intent.getStringExtra(USERNAME) ?: resources.getText(R.string.anonymous))

        mTextViewPassword = findViewById(R.id.managementActivityTextViewPassword) // edaakyil
        mTextViewPassword.text = "Password: " + (intent.getStringExtra(PASSWORD) ?: resources.getText(R.string.anonymous)) // edaakyil
    }

    private fun initViews() {
        initUserInfo()
    }

    private fun initialize() {
        initViews()
    }

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
}