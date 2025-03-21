package com.edaakyil.android.app.counter

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.app.counter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private var mStartedFlag = false
    private var mSeconds: Long = 0L
    private var mScheduledFuture: ScheduledFuture<*>? = null

    @Inject
    @Named("mainActivityScheduledExecutorService")
    lateinit var scheduledThreadPool: ScheduledExecutorService

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
        mBinding.startStopButtonText = resources.getString(R.string.start_text)
    }

    private fun setCounterText() {
        val hour = mSeconds / 60 / 60
        val minute = mSeconds / 60 % 60
        val second = mSeconds % 60

        "$hour:$minute:$second".apply { mBinding.mainActivityTextViewCounter.text = this }
    }

    fun onStartStopButtonClicked(){
        if (mStartedFlag) {
            mBinding.startStopButtonText = resources.getString(R.string.start_text)
            mScheduledFuture?.cancel(false)
        } else {
            mBinding.startStopButtonText = resources.getString(R.string.stop_text)
            mScheduledFuture = scheduledThreadPool.scheduleWithFixedDelay({ Log.i("Duration", (mSeconds++).toString()) }, 0, 1, TimeUnit.SECONDS)
        }

        mStartedFlag = !mStartedFlag
    }
}