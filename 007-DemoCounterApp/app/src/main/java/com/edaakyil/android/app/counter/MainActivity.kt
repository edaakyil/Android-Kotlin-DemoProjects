package com.edaakyil.android.app.counter

import android.os.Bundle
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

    private fun schedulerCallback() {
        val hour = mSeconds / 60 / 60
        val minute = mSeconds / 60 % 60
        val second = mSeconds % 60

        setCounterText1(hour, minute, second)
        setCounterText2(hour, minute, second)
        ++mSeconds
    }

    // For ViewBinding
    private fun setCounterText1(hour: Long, minute: Long, second: Long) {
        runOnUiThread { "$hour:$minute:$second".apply { mBinding.mainActivityTextViewCounter.text = this } }
    }

    // For DataBinding
    private fun setCounterText2(hour: Long, minute: Long, second: Long) {
        "$hour:$minute:$second".apply { mBinding.counterText = this }
    }

    fun onStartStopButtonClicked(){
        if (mStartedFlag) {
            mBinding.startStopButtonText = resources.getString(R.string.start_text)
            mScheduledFuture?.cancel(false)
        } else {
            mScheduledFuture = scheduledThreadPool.scheduleWithFixedDelay({ schedulerCallback() }, 0, 1, TimeUnit.SECONDS)
            mBinding.startStopButtonText = resources.getString(R.string.stop_text)
        }

        mStartedFlag = !mStartedFlag
    }
}