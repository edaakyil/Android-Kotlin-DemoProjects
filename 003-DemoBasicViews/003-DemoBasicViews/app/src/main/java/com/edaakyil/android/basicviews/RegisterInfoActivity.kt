package com.edaakyil.android.basicviews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.edaakyil.android.basicviews.constant.MARITAL_STATUS_TAGS
import com.edaakyil.android.basicviews.constant.USER_INFO
import com.edaakyil.android.basicviews.data.service.UserService
import com.edaakyil.android.basicviews.model.UserRegisterInfoModel
import com.edaakyil.data.exception.DataServiceException

private const val SAVE_USER_INFO_LOG_TAG = "SAVE_USER_INFO"
private const val LOAD_USER_INFO_LOG_TAG = "LOAD_USER_INFO"

class RegisterInfoActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextEmail: EditText
    private lateinit var mEditTextUsername: EditText
    private lateinit var mSpinnerMaritalStatus: Spinner
    private lateinit var mRadioGroupLastEducationDegree: RadioGroup
    private lateinit var mUserRegisterInfo: UserRegisterInfoModel
    private lateinit var mUserService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_info)

        initialize()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p2: Long) {
        Toast.makeText(this, mSpinnerMaritalStatus.selectedItem as String, Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private fun initialize() {
        mUserRegisterInfo = UserRegisterInfoModel()
        mUserService = UserService(this)
        initViews()
    }

    private fun initViews() {
        initEditTexts()
        initSpinnerMaritalStatus()
        mRadioGroupLastEducationDegree = findViewById(R.id.registerInfoActivityRadioGroupLastEducationDegree)
    }

    private fun initEditTexts() {
        mEditTextName = findViewById(R.id.registerInfoActivityEditTextName)
        mEditTextEmail = findViewById(R.id.registerInfoActivityEditTextEmail)
        mEditTextUsername = findViewById(R.id.registerInfoActivityEditTextUsername)
    }

    private fun initSpinnerMaritalStatus() {
        val maritalStatus = arrayOf(resources.getString(R.string.marital_status_single), resources.getString(R.string.marital_status_married), resources.getString(R.string.marital_status_divorced))

        mSpinnerMaritalStatus = findViewById<Spinner>(R.id.registerInfoActivitySpinnerMaritalStatus).apply {
            adapter = ArrayAdapter(this@RegisterInfoActivity, android.R.layout.simple_spinner_dropdown_item, maritalStatus)
            onItemSelectedListener = this@RegisterInfoActivity  // this is Spinner
        }
    }

    private fun fillUserRegisterInfoModel() {
        val name = mEditTextName.text.toString().trim()
        val email = mEditTextEmail.text.toString().trim()
        val username = mEditTextUsername.text.toString().trim()
        val maritalStatus = MARITAL_STATUS_TAGS[mSpinnerMaritalStatus.selectedItemPosition]
        val lastEducationDegreeId = mRadioGroupLastEducationDegree.checkedRadioButtonId
        val lastEducationDegree = if (lastEducationDegreeId != -1) findViewById<RadioButton>(lastEducationDegreeId).tag.toString().toInt() else 0

        mUserRegisterInfo.also {
            it.name = name
            it.email = email
            it.username = username
            it.maritalStatus = maritalStatus
            it.lastEducationDegree = lastEducationDegree
        }
    }

    private fun saveData(close: Boolean) {
        mUserService.saveUserData(mUserRegisterInfo)

        Log.i(SAVE_USER_INFO_LOG_TAG, "User saved successfully")
        Toast.makeText(this, R.string.user_successfully_saved_prompt, Toast.LENGTH_SHORT).show()

        if (close)
            finish()
    }

    private fun selectOptionIfUserSaved(close: Boolean) {
        Log.w(SAVE_USER_INFO_LOG_TAG, "user already exists")
        //Toast.makeText(this, R.string.username_already_saved_prompt, Toast.LENGTH_SHORT).show()

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

            if (mUserRegisterInfo.username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            if (!mUserService.isUserSaved(mUserRegisterInfo.username))
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
        mEditTextName.setText(mUserRegisterInfo.name)
        mEditTextEmail.setText(mUserRegisterInfo.email)
        mSpinnerMaritalStatus.setSelection(MARITAL_STATUS_TAGS.indexOf(mUserRegisterInfo.maritalStatus))

        mRadioGroupLastEducationDegree.clearCheck()
        val lastEducationDegreeId = mUserRegisterInfo.lastEducationDegree
        if(lastEducationDegreeId != 0)
            (mRadioGroupLastEducationDegree.getChildAt(lastEducationDegreeId - 1) as RadioButton).isChecked = true
    }

    fun onLoadButtonClicked(view: View) {
        try {
            val username = mEditTextUsername.text.toString().trim()

            if (username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            val ri = mUserService.findByUsername(username)

            if (ri == null) {
                Toast.makeText(this, R.string.username_not_found_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            mUserRegisterInfo = ri // smart cast
            fillUI()

            Toast.makeText(this, R.string.user_successfully_loaded_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: DataServiceException) {
            Log.e(LOAD_USER_INFO_LOG_TAG, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(LOAD_USER_INFO_LOG_TAG, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }

    fun onSaveButtonClicked(view: View) = saveUserRegisterInfo(false)

    fun onClearButtonClicked(view: View) = mRadioGroupLastEducationDegree.clearCheck()

    fun onContinueButtonClicked(view: View) {
        fillUserRegisterInfoModel()
        Intent(this, RegisterPasswordActivity::class.java).apply { putExtra(USER_INFO, mUserRegisterInfo); startActivity(this) }
        finish()

        Toast.makeText(this, "${mUserRegisterInfo.maritalStatus}, ${mUserRegisterInfo.lastEducationDegree}", Toast.LENGTH_SHORT).show()
    }

    fun onCloseButtonClicked(view: View) {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_title_alert)
            .setMessage(R.string.alert_dialog_close_message)
            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveUserRegisterInfo(true) }
            .setNegativeButton(R.string.alert_dialog_close) { _, _ -> finish() }
            .setNeutralButton(R.string.alert_dialog_cancel) { _, _ -> Toast.makeText(this, R.string.alert_dialog_cancel, Toast.LENGTH_SHORT).show() }
            .setOnCancelListener { Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show() } // if setCancelable is false, this won't work
            .create()
            .show()
    }
}
