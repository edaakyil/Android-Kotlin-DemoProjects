package com.edaakyil.android.demopaymentapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.demopaymentapp.application.module.datetime.annotation.DateTimeFormatterInterceptor
import com.edaakyil.android.demopaymentapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    @Inject
    lateinit var dateTime: LocalDateTime

    @Inject
    @DateTimeFormatterInterceptor
    lateinit var dateTimeFormatter: DateTimeFormatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.mainActivityMainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initModels()
    }

    private fun initModels() {
        mBinding.activity = this
        mBinding.dateTime = dateTimeFormatter.format(dateTime)
    }

    fun onLoginButtonClicked() {
        Intent(this, LoginActivity::class.java).apply { startActivity(this) }
    }
}