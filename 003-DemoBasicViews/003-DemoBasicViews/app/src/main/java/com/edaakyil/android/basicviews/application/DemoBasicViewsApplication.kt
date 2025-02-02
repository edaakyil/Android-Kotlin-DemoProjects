package com.edaakyil.android.basicviews.application

import android.app.Application
import android.widget.Toast
import com.edaakyil.android.basicviews.constant.USERS
import java.io.File

class DemoBasicViewsApplication : Application() {
    override fun onCreate() {
        Toast.makeText(this, "Application started", Toast.LENGTH_LONG).show()
        File(filesDir, USERS).mkdirs()
        super.onCreate()
    }
}