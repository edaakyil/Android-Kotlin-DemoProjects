package com.edaakyil.android.demodatabindingapp.model

import com.edaakyil.android.demodatabindingapp.MainActivity

class MainActivityActionModel(activity: MainActivity) {
    private val mActivity: MainActivity = activity

    fun onTitleTextClicked() = mActivity.onTitleTextClicked()
    fun onClearAllButtonClicked() = mActivity.onClearAllButtonClicked()
    fun onClearUsernameTextButtonClicked() = mActivity.onClearUsernameTextButtonClicked()
    fun onClearPasswordTextButtonClicked() = mActivity.onClearPasswordTextButtonClicked()
    fun onLoginButtonClicked() = mActivity.onLoginButtonClicked()
    fun onRegisterButtonClicked() = mActivity.onRegisterButtonClicked()
    fun onCloseButtonClicked() = mActivity.onCloseButtonClicked()
}