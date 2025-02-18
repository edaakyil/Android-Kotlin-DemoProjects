package com.edaakyil.android.demodatabindingapp.application

import android.app.Application
import android.widget.Toast
import com.edaakyil.android.demodatabindingapp.constant.USERS
import java.io.File

class DemoBasicViewsApplication : Application() {
    override fun onCreate() {
        Toast.makeText(this, "Application started", Toast.LENGTH_SHORT).show()
        File(filesDir, USERS).mkdirs()
        super.onCreate()
    }
}