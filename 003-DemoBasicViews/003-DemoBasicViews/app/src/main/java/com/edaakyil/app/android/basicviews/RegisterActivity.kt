package com.edaakyil.app.android.basicviews

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
import com.edaakyil.app.android.basicviews.constant.MARITAL_STATUS_TAGS
import com.edaakyil.app.android.basicviews.model.UserInfoModel
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

const val SAVE_REGISTER_INFO = "SAVE_REGISTER_INFO"
const val LOAD_REGISTER_INFO = "LOAD_REGISTER_INFO"
const val DELIMITER = ":"

class RegisterActivity : AppCompatActivity() {
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextEmail: EditText
    private lateinit var mEditTextUsername: EditText
    private lateinit var mRadioGroupMaritalStatus: RadioGroup
    private lateinit var mRadioGroupLastEducationDegree: RadioGroup
    private lateinit var mUserInfo: UserInfoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        initialize()
    }

    private fun initialize() {
        mUserInfo = UserInfoModel()
        initViews()
    }

    private fun initViews() {
        initEditTexts()
        initRadioGroupMaritalStatus()
        mRadioGroupLastEducationDegree = findViewById(R.id.registerActivityRadioGroupLastEducationDegree)
    }

    private fun initEditTexts() {
        mEditTextName = findViewById(R.id.registerActivityEditTextName)
        mEditTextEmail = findViewById(R.id.registerActivityEditTextEmail)
        mEditTextUsername = findViewById(R.id.registerActivityEditTextUsername)
    }

    private fun initRadioGroupMaritalStatus() {
        mRadioGroupMaritalStatus = findViewById(R.id.registerActivityRadioGroupMaritalStatus)

        /*
        for (i in 0..<mRadioGroupMaritalStatus.childCount)
        mRadioGroupMaritalStatus.getChildAt(i).tag = MARITAL_STATUS_TAGS[i]
        */

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

        Toast.makeText(this, R.string.user_info_saved, Toast.LENGTH_SHORT).show()
    }

    private fun saveAtCloseCallback() {
        try {
            fillUserInfoModel()

            if (mUserInfo.username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            val file = File(filesDir, "${mUserInfo.username}.txt")

            if (file.exists()) {
                Log.w(SAVE_REGISTER_INFO, "user already exists")
                Toast.makeText(this, R.string.username_already_saved_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            //BufferedWriter(OutputStreamWriter(openFileOutput("${mUserInfoModel.username}.txt", MODE_PRIVATE), StandardCharsets.UTF_8)).use { writeUserInfo(it) }
            BufferedWriter(OutputStreamWriter(openFileOutput("${mUserInfo.username}.txt", MODE_PRIVATE), StandardCharsets.UTF_8)).use(::writeUserInfo)  // function/method reference

            Log.i(SAVE_REGISTER_INFO, "Saved")

            finish()
        } catch (ex: IOException) {
            Log.e(SAVE_REGISTER_INFO, ex.message ?: "")
            Toast.makeText(this, R.string.io_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
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

            val file = File(filesDir, "$username.txt")

            if (!file.exists()) {
                Toast.makeText(this, R.string.username_not_found_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            BufferedReader(InputStreamReader(openFileInput("$username.txt"), StandardCharsets.UTF_8)).use(::fillUI)

            Toast.makeText(this, R.string.user_successfully_loaded_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: IOException) {
            Log.e(LOAD_REGISTER_INFO, ex.message ?: "")
            Toast.makeText(this, R.string.io_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(LOAD_REGISTER_INFO, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }

    fun onClearButtonClicked(view: View) = mRadioGroupLastEducationDegree.clearCheck()

    fun onRegisterButtonClicked(view: View) {
        fillUserInfoModel()

        Toast.makeText(this, "${mUserInfo.maritalStatus}, ${mUserInfo.lastEducationDegree}", Toast.LENGTH_SHORT).show()
    }

    fun onCloseButtonClicked(view: View) {
        val dlg = AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_close_title)
            .setMessage(R.string.alert_dialog_close_message)
            .setPositiveButton(R.string.alert_dialog_close_save) { _, _ -> saveAtCloseCallback() }
            .setNegativeButton(R.string.alert_dialog_close_close) { _, _ -> finish() }
            .setNeutralButton(R.string.alert_dialog_close_cancel) { _, _ -> Toast.makeText(this, R.string.alert_dialog_close_cancel, Toast.LENGTH_SHORT).show() }
            .setOnCancelListener { Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show() } // if setCancelable is false, this won't work
            .create()
            .show()
    }
}