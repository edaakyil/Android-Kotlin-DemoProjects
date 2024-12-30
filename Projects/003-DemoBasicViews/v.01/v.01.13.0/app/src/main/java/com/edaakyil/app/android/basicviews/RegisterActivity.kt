package com.edaakyil.app.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.edaakyil.app.android.basicviews.constant.MARITAL_STATUS_TAGS
import java.io.BufferedWriter
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

class RegisterActivity : AppCompatActivity() {
    private lateinit var mEditTextName: EditText
    private lateinit var mEditTextEmail: EditText
    private lateinit var mEditTextUsername: EditText
    private lateinit var mRadioGroupMaritalStatus: RadioGroup
    private lateinit var mRadioGroupLastEducationDegree: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        initialize()
    }

    private fun initialize() = initViews()

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

    fun onClearButtonClicked(view: View) = mRadioGroupLastEducationDegree.clearCheck()

    fun onRegisterButtonClicked(view: View) {
        val selectedMaritalStatusValue = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).tag as Char

        val id= mRadioGroupLastEducationDegree.checkedRadioButtonId
        val selectedLastEducationDegreeValue = when (id) {
            -1 -> 0
            else -> findViewById<RadioButton>(id).tag.toString().toInt()
        }

        Toast.makeText(this, "$selectedMaritalStatusValue, $selectedLastEducationDegreeValue", Toast.LENGTH_SHORT).show()
    }

    private fun saveAtCloseCallback() {
        val name = mEditTextName.text.toString().trim()
        val email = mEditTextEmail.text.toString().trim()
        val username = mEditTextUsername.text.toString().trim()
        val maritalStatus = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).tag as Char
        val lastEducationDegreeId = mRadioGroupLastEducationDegree.checkedRadioButtonId
        val lastEducationDegree = if (lastEducationDegreeId != -1) findViewById<RadioButton>(lastEducationDegreeId).tag.toString().toInt() else 0

        if (username.isBlank()) {
            Toast.makeText(this, R.string.username_missing_blank, Toast.LENGTH_SHORT).show()
            return
        }

        BufferedWriter(OutputStreamWriter(openFileOutput("$username.txt", MODE_PRIVATE), StandardCharsets.UTF_8)).use {
            it.write("$username ")
            it.write("$name ")
            it.write("$email ")
            it.write("$maritalStatus ")
            it.write("$lastEducationDegree ")

            Toast.makeText(this, R.string.user_info_saved, Toast.LENGTH_SHORT).show()
        }

        finish()
    }

    fun onCloseButtonClicked(view: View) {
        val dlg = AlertDialog.Builder(this)
            .setTitle(R.string.alert_dialog_close_title)
            .setMessage(R.string.alert_dialog_close_message)
            .setPositiveButton(R.string.save) { _, _ -> saveAtCloseCallback() }
            .setNegativeButton(R.string.close) { _, _ -> finish() }
            .setNeutralButton(R.string.cancel) { _, _ -> }
            //.setCancelable(true) // default value is true
            .setOnCancelListener { Toast.makeText(this, R.string.continue_register_prompt, Toast.LENGTH_SHORT).show() } // if setCancelable is false, this won't work
            .create()
            //.show()

        dlg.show()
    }
}