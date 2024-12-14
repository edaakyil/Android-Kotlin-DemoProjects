package com.edaakyil.app.android.basicviews.application

import android.app.Application
import android.widget.Toast
import java.io.File

class DemoBasicViewsApplication : Application() {
    override fun onCreate() {
        Toast.makeText(this, "Application started", Toast.LENGTH_LONG).show()
        File(filesDir, "users").mkdirs()
        super.onCreate()
    }
}