package com.edaakyil.android.demodatabindingapp.model

import com.edaakyil.android.demodatabindingapp.MainActivity
import java.lang.ref.WeakReference

class MainActivityActionModel(activity: MainActivity) {
    private val mWeakReference = WeakReference(activity)

    fun onAcceptSwitchCheckedChange(checked: Boolean) = mWeakReference.get()?.onAcceptSwitchCheckedChange(checked)
    fun onAnonymousCheckBoxCheckedChange(checked: Boolean) = mWeakReference.get()?.onAnonymousCheckBoxCheckedChange(checked)
    fun onLoginAreaToggleButtonCheckedChange(checked: Boolean) = mWeakReference.get()?.onLoginAreaToggleButtonCheckedChange(checked)
    fun onTitleTextClicked() = mWeakReference.get()?.onTitleTextClicked()
    fun onClearAllButtonClicked() = mWeakReference.get()?.onClearAllButtonClicked()
    fun onClearUsernameTextButtonClicked() = mWeakReference.get()?.onClearUsernameTextButtonClicked()
    fun onClearPasswordTextButtonClicked() = mWeakReference.get()?.onClearPasswordTextButtonClicked()
    fun onLoginButtonClicked() = mWeakReference.get()?.onLoginButtonClicked()
    fun onRegisterButtonClicked() = mWeakReference.get()?.onRegisterButtonClicked()
    fun onCloseButtonClicked() = mWeakReference.get()?.onCloseButtonClicked()
}