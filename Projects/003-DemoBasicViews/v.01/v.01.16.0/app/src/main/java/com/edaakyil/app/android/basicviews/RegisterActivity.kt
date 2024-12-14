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
import com.edaakyil.app.android.basicviews.model.RegisterInfoModel
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

const val SAVE_REGISTER_INFO = "SAVE_REGISTER_INFO"
const val LOAD_REGISTER_INFO = "LOAD_REGISTER_INFO"

class RegisterActivity : AppCompatActivity() {
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextEmail: EditText
    private lateinit var mEditTextUsername: EditText
    private lateinit var mRadioGroupMaritalStatus: RadioGroup
    private lateinit var mRadioGroupLastEducationDegree: RadioGroup
    private lateinit var mRegisterInfo: RegisterInfoModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        initialize()
    }

    private fun initialize() {
        mRegisterInfo = RegisterInfoModel()
        initViews()
    }

    private fun initViews() {
        mRadioGroupLastEducationDegree = findViewById(R.id.registerActivityRadioGroupLastEducationDegree)

        initEditTexts()
        initRadioGroupMaritalStatus()
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

    private fun fillRegisterInfo() {
        val name = mEditTextName.text.toString()
        val email = mEditTextEmail.text.toString()
        val username = mEditTextUsername.text.toString()
        val maritalStatus = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).tag as Char
        val lastEducationDegreeId = mRadioGroupLastEducationDegree.checkedRadioButtonId
        val lastEducationDegree = if (lastEducationDegreeId != -1) findViewById<RadioButton>(lastEducationDegreeId).tag.toString().toInt() else 0

        mRegisterInfo.also {
            it.name = name
            it.email = email
            it.username = username
            it.maritalStatus = maritalStatus
            it.lastEducationDegree = lastEducationDegree
        }
    }

    private fun loadRegisterInfo(br: BufferedReader) {
        val str = br.readLine() ?: throw IOException()
        val info = str.split(",")

        mEditTextName.setText(info[1])
        mEditTextEmail.setText(info[2])

        //(mRadioGroupMaritalStatus.getChildAt(MARITAL_STATUS_TAGS.indexOf(info[3] as Char)) as RadioButton).isChecked = true
        (mRadioGroupMaritalStatus.getChildAt(MARITAL_STATUS_TAGS.indexOf(info[3][0])) as RadioButton).isChecked = true

        val lastEducationDegree = info[4].toInt()
        if (lastEducationDegree != 0)
            (mRadioGroupLastEducationDegree.getChildAt(lastEducationDegree - 1) as RadioButton).isChecked = true
	else
            mRadioGroupLastEducationDegree.clearCheck()
    }

    private fun writeRegisterInfo(bw: BufferedWriter) {
        bw.write("${mRegisterInfo.username},")
        bw.write("${mRegisterInfo.name},")
        bw.write("${mRegisterInfo.email},")
        bw.write("${mRegisterInfo.maritalStatus},")
        bw.write("${mRegisterInfo.lastEducationDegree}")
    }

    private fun saveAtCloseCallback() {
        try {
            fillRegisterInfo()

            if (mRegisterInfo.username.isBlank()) {
                Toast.makeText(this, R.string.username_missing_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            val file = File(filesDir, "${mRegisterInfo.username}.txt")

            if (file.exists()) {
                Log.w(SAVE_REGISTER_INFO, "user already saved")
                Toast.makeText(this, R.string.username_already_saved_prompt, Toast.LENGTH_SHORT).show()
                return
            }

            //BufferedWriter(OutputStreamWriter(openFileOutput("${mRegisterInfo.username}.txt", MODE_PRIVATE), StandardCharsets.UTF_8)).use { writeRegisterInfo(it) }
            BufferedWriter(OutputStreamWriter(openFileOutput("${mRegisterInfo.username}.txt", MODE_PRIVATE), StandardCharsets.UTF_8)).use(::writeRegisterInfo)  // function reference

            Log.i(SAVE_REGISTER_INFO, "User successfully saved")
            Toast.makeText(this, R.string.user_successfully_saved_prompt, Toast.LENGTH_SHORT).show()

            finish()
        } catch (ex: IOException) {
            Log.e(SAVE_REGISTER_INFO, ex.message ?: "")
            Toast.makeText(this, R.string.io_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Log.e(SAVE_REGISTER_INFO, ex.message, ex)
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
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

            BufferedReader(InputStreamReader(openFileInput("$username.txt"), StandardCharsets.UTF_8)).use(::loadRegisterInfo)

            Toast.makeText(this, R.string.user_successfully_loaded_prompt,Toast.LENGTH_SHORT).show()
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
        fillRegisterInfo()

        Toast.makeText(this, "${mRegisterInfo.maritalStatus}, ${mRegisterInfo.lastEducationDegree}", Toast.LENGTH_SHORT).show()
    }

    fun onCloseButtonClicked(view: View) {
        AlertDialog.Builder(this)
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