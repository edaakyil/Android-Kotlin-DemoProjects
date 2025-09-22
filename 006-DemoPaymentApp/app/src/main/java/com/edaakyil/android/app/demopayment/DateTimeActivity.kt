package com.edaakyil.android.demopaymentapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.demopaymentapp.application.module.datetime.annotation.DateTimeFormatterInterceptor
import com.edaakyil.android.demopaymentapp.databinding.ActivityDateTimeBinding
import com.edaakyil.android.lib.util.datetime.module.annotation.CurrentLocalDateInterceptor
import com.edaakyil.android.lib.util.datetime.module.annotation.CurrentLocalDateTimeInterceptor
import com.edaakyil.android.lib.util.datetime.module.annotation.CurrentLocalTimeInterceptor
import com.edaakyil.android.lib.util.datetime.module.annotation.DateFormatterTRInterceptor
import com.edaakyil.android.lib.util.datetime.module.annotation.DateTimeFormatterTRInterceptor
import com.edaakyil.android.lib.util.datetime.module.annotation.TimeFormatterTRInterceptor
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@AndroidEntryPoint
class DateTimeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityDateTimeBinding

    @Inject
    lateinit var dateTimeEda: LocalDateTime

    @Inject
    @DateTimeFormatterInterceptor
    lateinit var dateTimeFormatterEda: DateTimeFormatter

    @Inject
    @CurrentLocalDateInterceptor
    lateinit var date: LocalDate

    @Inject
    @CurrentLocalTimeInterceptor
    lateinit var time: LocalTime

    @Inject
    @CurrentLocalDateTimeInterceptor
    lateinit var dateTime: LocalDateTime

    @Inject
    @DateFormatterTRInterceptor
    lateinit var dateFormatter: DateTimeFormatter

    @Inject
    @TimeFormatterTRInterceptor
    lateinit var timeFormatter: DateTimeFormatter

    @Inject
    @DateTimeFormatterTRInterceptor
    lateinit var dateTimeFormatter: DateTimeFormatter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(mBinding.dateTimeActivityMainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_date_time)
        initModels()
    }

    private fun initModels() {
        mBinding.dateTimeEda = dateTimeFormatterEda.format(dateTimeEda)
        mBinding.date = dateFormatter.format(date)
        mBinding.time = timeFormatter.format(time)
        mBinding.dateTime = dateTimeFormatter.format(dateTime)
    }
}