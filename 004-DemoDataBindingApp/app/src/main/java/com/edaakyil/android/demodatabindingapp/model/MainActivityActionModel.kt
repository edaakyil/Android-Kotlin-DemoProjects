package com.edaakyil.android.demodatabindingapp.model

import com.edaakyil.android.demodatabindingapp.MainActivity

class MainActivityActionModel(activity: MainActivity) {
    private val mActivity: MainActivity = activity

    fun onRegisterButtonClicked() = mActivity.onRegisterButtonClicked()
    fun onCloseButtonClicked() = mActivity.onCloseButtonClicked()
}