package com.edaakyil.android.demodatabindingapp.model

import com.edaakyil.android.demodatabindingapp.MainActivity

class MainActivityActionModel(activity: MainActivity) {
    private val mActivity: MainActivity = activity

    fun onClearAllButtonClicked() = mActivity.onClearAllButtonClicked()
    fun onClearUsernameTextButtonClicked() = mActivity.onClearUsernameTextButtonClicked()
    fun onClearPasswordTextButtonClicked() = mActivity.onClearPasswordTextButtonClicked()
    fun onRegisterButtonClicked() = mActivity.onRegisterButtonClicked()
    fun onCloseButtonClicked() = mActivity.onCloseButtonClicked()
}