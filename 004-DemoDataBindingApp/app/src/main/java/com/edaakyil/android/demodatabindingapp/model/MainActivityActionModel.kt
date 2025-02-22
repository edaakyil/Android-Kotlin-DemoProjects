package com.edaakyil.android.demodatabindingapp.model

import com.edaakyil.android.demodatabindingapp.MainActivity

class MainActivityActionModel(activity: MainActivity) {
    private val mActivity: MainActivity = activity

    fun onAcceptSwitchCheckedChange(checked: Boolean) = mActivity.onAcceptSwitchCheckedChange(checked)
    fun onAnonymousCheckBoxCheckedChange(checked: Boolean) = mActivity.onAnonymousCheckBoxCheckedChange(checked)
    fun onLoginAreaToggleButtonCheckedChange(checked: Boolean) = mActivity.onLoginAreaToggleButtonCheckedChange(checked)
    fun onTitleTextClicked() = mActivity.onTitleTextClicked()
    fun onClearAllButtonClicked() = mActivity.onClearAllButtonClicked()
    fun onClearUsernameTextButtonClicked() = mActivity.onClearUsernameTextButtonClicked()
    fun onClearPasswordTextButtonClicked() = mActivity.onClearPasswordTextButtonClicked()
    fun onLoginButtonClicked() = mActivity.onLoginButtonClicked()
    fun onRegisterButtonClicked() = mActivity.onRegisterButtonClicked()
    fun onCloseButtonClicked() = mActivity.onCloseButtonClicked()
}