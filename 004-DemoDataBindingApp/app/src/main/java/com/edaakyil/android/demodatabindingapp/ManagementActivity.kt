package com.edaakyil.android.demodatabindingapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.demodatabindingapp.constant.PASSWORD
import com.edaakyil.android.demodatabindingapp.constant.USERNAME
import com.edaakyil.android.demodatabindingapp.databinding.ActivityManagementBinding

class ManagementActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    private fun initialize() {
        initBinding()
    }

    private fun initBinding() {
        enableEdgeToEdge()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_management)
        mBinding.activity = this
        initViews()
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.managementActivityLinearLayoutMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initViews() {
        mBinding.username = resources.getString(R.string.username, intent.getStringExtra(USERNAME) ?: resources.getString(R.string.anonymous))
        mBinding.password = resources.getString(R.string.password, intent.getStringExtra(PASSWORD) ?: resources.getString(R.string.anonymous))
    }

    fun onUsersButtonClicked() {
        Intent(this, UsersActivity::class.java).apply { startActivity(this) }
    }

    fun onUserOperationsButtonClicked() {
        TODO("Not yet implement!...")
    }

    fun onCloseButtonClicked() = finish()
}
