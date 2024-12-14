package com.edaakyil.app.android.basicviews.application

import android.app.Application
import android.widget.Toast

class DemoBasicViewsApplication : Application() {
    override fun onCreate() {
        Toast.makeText(this, "Application started", Toast.LENGTH_LONG).show()
        super.onCreate()
    }
}