package com.edaakyil.android.basicviews

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.edaakyil.android.basicviews.constant.MARITAL_STATUS_TAGS
import com.edaakyil.android.basicviews.constant.USER_INFO
import com.edaakyil.android.basicviews.constant.USERS_FORMAT
import com.edaakyil.android.basicviews.model.UserInfoModel
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

private const val SAVE_REGISTER_INFO = "SAVE_REGISTER_INFO"
private const val LOAD_REGISTER_INFO = "LOAD_REGISTER_INFO"
private const val DELIMITER = ":"

class RegisterInfoActivity : AppCompatActivity() {
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextEmail: EditText
    private lateinit var mEditTextUsername: EditText
    private lateinit var mRadioGroupMaritalStatus: RadioGroup
    private lateinit var mRadioGroupLastEducationDegree: RadioGroup
    private lateinit var mUserInfo: UserInfoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register_info)

        initialize()
    }

    private fun initialize() {
        mUserInfo = UserInfoModel()
        initViews()
    }

    private fun initViews() {
        initEditTexts()
        initRadioGroupMaritalStatus()
        mRadioGroupLastEducationDegree = findViewById(R.id.registerInfoActivityRadioGroupLastEducationDegree)
    }

    private fun initEditTexts() {
        mEditTextName = findViewById(R.id.registerInfoActivityEditTextName)
        mEditTextEmail = findViewById(R.id.registerInfoActivityEditTextEmail)
        mEditTextUsername = findViewById(R.id.registerInfoActivityEditTextUsername)
    }

    private fun initRadioGroupMaritalStatus() {
        mRadioGroupMaritalStatus = findViewById(R.id.registerInfoActivityRadioGroupMaritalStatus)

        (0..<mRadioGroupMaritalStatus.childCount).forEach { mRadioGroupMaritalStatus.getChildAt(it).tag = MARITAL_STATUS_TAGS[it] }

        mRadioGroupMaritalStatus.setOnCheckedChangeListener { _, id -> Toast.makeText(this, "Checked: ${findViewById<RadioButton>(id).text}", Toast.LENGTH_SHORT).show() }
    }

    private fun fillUserInfoModel() {
        val name = mEditTextName.text.toString()
        val email = mEditTextEmail.text.toString()
        val username = mEditTextUsername.text.toString()
        val maritalStatus = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).tag as Char
        val lastEducationDegreeId = mRadioGroupLastEducationDegree.checkedRadioButtonId
        val lastEducationDegree = if (lastEducationDegreeId != -1) findViewById<RadioButton>(lastEducationDegreeId).tag.toString().toInt() else 0

        mUserInfo.also {
            it.name = name
            it.email = email
            it.username = username
            it.maritalStatus = maritalStatus
            it.lastEducationDegree = lastEducationDegree
        }
    }

    private fun writeUserInfo(bw: BufferedWriter) {
        bw.write("${mUserInfo.username}$DELIMITER")
        bw.write("${mUserInfo.name}$DELIMITER")
        bw.write("${mUserInfo.email}$DELIMITER")
        bw.write("${mUserInfo.maritalStatus}$DELIMITER")
        bw.write("${mUserInfo.lastEducationDegree}")
    }

    private fun saveData(close: Boolean) {
        BufferedWriter(OutputStreamWriter(FileOutputStream(File(filesDir, USERS_FORMAT.format("${mUserInfo.username}.txt"))), StandardCharsets.UTF_8)).use(::writeUserInfo)

        Log.i(SAVE_REGISTER_INFO, "User saved successfully")
        Toast.makeText(this, R.string.user_successfully_saved_prompt, Toast.LENGTH_SHORT).show()

        if (close)
            finish()
    }

    private fun selectOptionIfUserSaved(close: Boolean) {
        Log.w(SAVE_REGISTER_INFO, "user already exists")
        //Toast.makeText(this, R.string.username_already_saved_prompt, Toast.LENGTH_SHORT).show()

        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_user_already_saved_title)
            .setMessage(R.string.alert_dialog_user_already_saved_message)
            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveData(close) }
            .setNegativeButton(R.string.alert_dialog_cancel) { _, _ -> }
            .create()
            .show()
    }

    private fun saveUserInfo(close: Boolean) {
        try {
            fillUserInfoModel()

            if (mUserInfo.username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            val file = File(filesDir, USERS_FORMAT.format("${mUserInfo.username}.txt"))

            if (!file.exists())
                saveData(close)
            else
                selectOptionIfUserSaved(close)
        } catch (ex: IOException) {
            Log.e(SAVE_REGISTER_INFO, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(SAVE_REGISTER_INFO, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }

    /** Read the exist user's information from internal memory
     *
     */
    private fun readUserInfo(br: BufferedReader): List<String> {
        val str = br.readLine() ?: throw IOException()  // line(satır) okuma

        // line'ı split etme
        val info = str.split(DELIMITER) // Java version: str.split("[ ]+")

        return info
    }

    /** Fill the exist user's information to the UI
     *
     */
    private fun fillUI(br: BufferedReader) {
        val existUserInfo = readUserInfo(br)

        mEditTextName.setText(existUserInfo[1])
        mEditTextEmail.setText(existUserInfo[2])
        (mRadioGroupMaritalStatus.getChildAt(MARITAL_STATUS_TAGS.indexOf(existUserInfo[3][0])) as RadioButton).isChecked = true

        mRadioGroupLastEducationDegree.clearCheck()
        val lastEducationDegreeId = existUserInfo[4].toInt()
        if(lastEducationDegreeId != 0)
            (mRadioGroupLastEducationDegree.getChildAt(lastEducationDegreeId - 1) as RadioButton).isChecked = true
    }

    fun onLoadButtonClicked(view: View) {
        try {
            val username = mEditTextUsername.text.toString()

            if (username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            val file = File(filesDir, USERS_FORMAT.format("$username.txt"))

            if (!file.exists()) {
                Toast.makeText(this, R.string.username_not_found_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            BufferedReader(InputStreamReader(FileInputStream(file), StandardCharsets.UTF_8)).use(::fillUI)

            Toast.makeText(this, R.string.user_successfully_loaded_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: IOException) {
            Log.e(LOAD_REGISTER_INFO, ex.message ?: "")
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(LOAD_REGISTER_INFO, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }

    fun onSaveButtonClicked(view: View) = saveUserInfo(false)

    fun onClearButtonClicked(view: View) = mRadioGroupLastEducationDegree.clearCheck()

    fun onContinueButtonClicked(view: View) {
        fillUserInfoModel()
        Intent(this, RegisterPasswordActivity::class.java).apply { putExtra(USER_INFO, mUserInfo); startActivity(this) }
        finish()

        Toast.makeText(this, "${mUserInfo.maritalStatus}, ${mUserInfo.lastEducationDegree}", Toast.LENGTH_SHORT).show()
    }

    fun onCloseButtonClicked(view: View) {
        AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_title_alert)
            .setMessage(R.string.alert_dialog_close_message)
            .setPositiveButton(R.string.alert_dialog_save) { _, _ -> saveUserInfo(true) }
            .setNegativeButton(R.string.alert_dialog_close) { _, _ -> finish() }
            .setNeutralButton(R.string.alert_dialog_cancel) { _, _ -> Toast.makeText(this, R.string.alert_dialog_cancel, Toast.LENGTH_SHORT).show() }
            .setOnCancelListener { Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show() } // if setCancelable is false, this won't work
            .create()
            .show()
    }
}
