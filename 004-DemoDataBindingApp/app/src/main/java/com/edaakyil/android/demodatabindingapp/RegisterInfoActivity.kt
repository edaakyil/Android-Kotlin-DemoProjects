package com.edaakyil.android.demodatabindingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.demodatabindingapp.constant.MARITAL_STATUS_TAGS
import com.edaakyil.android.demodatabindingapp.constant.USER_INFO
import com.edaakyil.android.demodatabindingapp.data.service.UserService
import com.edaakyil.android.demodatabindingapp.databinding.ActivityRegisterInfoBinding
import com.edaakyil.android.demodatabindingapp.model.UserRegisterInfoModel
import com.edaakyil.data.exception.DataServiceException

private const val SAVE_USER_INFO_LOG_TAG = "SAVE_USER_INFO"
private const val LOAD_USER_INFO_LOG_TAG = "LOAD_USER_INFO"

class RegisterInfoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var mBinding: ActivityRegisterInfoBinding
    private lateinit var mUserService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p2: Long) {
        Toast.makeText(this, mBinding.spinnerMaritalStatusAdapter!!.getItem(position), Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun initialize() {
        initBinding()
        mUserService = UserService(this)
    }

    private fun initBinding() {
        enableEdgeToEdge()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_register_info)
        initModels()
    }

    private fun initModels() {
        mBinding.activity = this
        mBinding.userRegisterInfo = UserRegisterInfoModel()
        initMaritalStatusModel()
    }

    private fun initMaritalStatusModel() {
        val maritalStatus = arrayOf(resources.getString(R.string.marital_status_single), resources.getString(R.string.marital_status_married), resources.getString(R.string.marital_status_divorced))

        mBinding.spinnerMaritalStatusAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, maritalStatus)

        mBinding.registerInfoActivitySpinnerMaritalStatus.apply {
            onItemSelectedListener = this@RegisterInfoActivity  // this is Spinner
        }
    }

    private fun fillUserRegisterInfoModel() {
        val maritalStatus = MARITAL_STATUS_TAGS[mBinding.selectedSpinnerMaritalStatusItemPosition]
        val lastEducationDegreeId = mBinding.registerInfoActivityRadioGroupLastEducationDegree.checkedRadioButtonId
        val lastEducationDegree = if (lastEducationDegreeId != -1) findViewById<RadioButton>(lastEducationDegreeId).tag.toString().toInt() else 0

        mBinding.userRegisterInfo!!.also {
            it.maritalStatus = maritalStatus
            it.lastEducationDegree = lastEducationDegree
        }
    }

    private fun saveData(close: Boolean) {
        mUserService.saveUserData(mBinding.userRegisterInfo!!)

        Log.i(SAVE_USER_INFO_LOG_TAG, "User saved successfully")
        Toast.makeText(this, R.string.user_successfully_saved_prompt, Toast.LENGTH_SHORT).show()

        if (close)
            finish()
    }

    private fun selectOptionIfUserSaved(close: Boolean) {
        Log.w(SAVE_USER_INFO_LOG_TAG, "user already exists")

        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_user_already_saved_title)
            .setMessage(R.string.alert_dialog_user_already_saved_message)
            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveData(close) }
            .setNegativeButton(R.string.alert_dialog_cancel) { _, _ -> }
            .create()
            .show()
    }

    private fun saveUserRegisterInfo(close: Boolean) {
        try {
            fillUserRegisterInfoModel()

            val username = mBinding.userRegisterInfo!!.username.trim()

            if (username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            if (!mUserService.isUserSaved(username))
                saveData(close)
            else
                selectOptionIfUserSaved(close)
        } catch (ex: DataServiceException) {
            Log.e(SAVE_USER_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(SAVE_USER_INFO_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }

    private fun fillUI() {
        val userInfo = mBinding.userRegisterInfo!!
        mBinding.registerInfoActivityEditTextName.setText(userInfo.name)
        mBinding.registerInfoActivityEditTextEmail.setText(userInfo.email)
        mBinding.registerInfoActivityEditTextUsername.setText(userInfo.username)
        mBinding.selectedSpinnerMaritalStatusItemPosition = MARITAL_STATUS_TAGS.indexOf(userInfo.maritalStatus)

        mBinding.registerInfoActivityRadioGroupLastEducationDegree.clearCheck()
        val lastEducationDegreeId = userInfo.lastEducationDegree
        if(lastEducationDegreeId != 0)
            (mBinding.registerInfoActivityRadioGroupLastEducationDegree.getChildAt(lastEducationDegreeId - 1) as RadioButton).isChecked = true
    }

    fun onLoadButtonClicked() {
        try {
            val username = mBinding.userRegisterInfo!!.username.trim()

            if (username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            val ri = mUserService.findByUsername(username)

            if (ri == null) {
                Toast.makeText(this, R.string.username_not_found_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            mBinding.userRegisterInfo!!.also {
                it.name = ri.name
                it.email = ri.email
                it.username = ri.username
                it.maritalStatus = ri.maritalStatus
                it.lastEducationDegree = ri.lastEducationDegree
            }

            fillUI()

            Toast.makeText(this, mBinding.userRegisterInfo.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(this, R.string.user_successfully_loaded_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: DataServiceException) {
            Log.e(LOAD_USER_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(LOAD_USER_INFO_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }

    fun onSaveButtonClicked() = saveUserRegisterInfo(false)

    fun onClearButtonClicked() = mBinding.registerInfoActivityRadioGroupLastEducationDegree.clearCheck()

    fun onContinueButtonClicked() {
        fillUserRegisterInfoModel()
        Intent(this, RegisterPasswordActivity::class.java).apply { putExtra(USER_INFO, mBinding.userRegisterInfo); startActivity(this) }
        finish()

        Toast.makeText(this, "${mBinding.userRegisterInfo?.maritalStatus}, ${mBinding.userRegisterInfo?.lastEducationDegree}", Toast.LENGTH_SHORT).show()
    }

    fun onCloseButtonClicked() {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_alert_title)
            .setMessage(R.string.alert_dialog_close_message)
            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveUserRegisterInfo(true) }
            .setNegativeButton(R.string.alert_dialog_close) { _, _ -> finish() }
            .setNeutralButton(R.string.alert_dialog_cancel) { _, _ -> Toast.makeText(this, R.string.alert_dialog_cancel, Toast.LENGTH_SHORT).show() }
            .setOnCancelListener { Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show() } // if setCancelable is false, this won't work
            .create()
            .show()
    }
}
