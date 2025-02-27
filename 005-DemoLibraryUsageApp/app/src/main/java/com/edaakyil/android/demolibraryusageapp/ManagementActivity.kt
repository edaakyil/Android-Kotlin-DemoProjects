package com.edaakyil.android.demolibraryusageapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.demolibraryusageapp.constant.PASSWORD
import com.edaakyil.android.demolibraryusageapp.constant.USERNAME
import com.edaakyil.android.demolibraryusageapp.databinding.ActivityManagementBinding
import com.edaakyil.android.library.util.datetime.DateTimeFormatterUtil
import java.time.LocalDateTime

class ManagementActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityManagementBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.managementActivityLinearLayoutMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_management)
        initModels()
    }

    private fun initModels() {
        mBinding.activity = this
        val now = LocalDateTime.now()
        mBinding.date = resources.getString(R.string.date_text).format(DateTimeFormatterUtil.DATE_FORMATTER_TR.format(now))
        mBinding.time = resources.getString(R.string.time_text).format(DateTimeFormatterUtil.TIME_FORMATTER_TR.format(now))
        mBinding.dateTime = resources.getString(R.string.date_time_text).format(DateTimeFormatterUtil.DATETIME_FORMATTER_TR.format(now))
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
