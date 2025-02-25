package com.edaakyil.android.demolibraryusageapp.application

import android.app.Application
import android.widget.Toast
import com.edaakyil.android.demolibraryusageapp.constant.USERS
import java.io.File

class DemoLibraryUsageApplication : Application() {
    override fun onCreate() {
        Toast.makeText(this, "Application started", Toast.LENGTH_SHORT).show()
        File(filesDir, USERS).mkdirs()
        super.onCreate()
    }
}