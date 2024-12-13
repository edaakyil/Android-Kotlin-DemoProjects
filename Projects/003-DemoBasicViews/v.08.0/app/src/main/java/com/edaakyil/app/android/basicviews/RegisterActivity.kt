package com.edaakyil.app.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var mRadioGroupMaritalStatus: RadioGroup
    private lateinit var mRadioGroupLastEducationDegree: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        initialize()
    }

    private fun initRadioGroups() {
        mRadioGroupMaritalStatus = findViewById(R.id.registerActivityRadioGroupMaritalStatus)
        mRadioGroupLastEducationDegree = findViewById(R.id.registerActivityRadioGroupLastEducationDegree)
    }

    private fun initViews() {
        initRadioGroups()
    }

    private fun initialize() {
        initViews()
    }

    fun onRegisterButtonClicked(view: View) {
        val selectedMaritalStatusText = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).text

        val id= mRadioGroupLastEducationDegree.checkedRadioButtonId
        val selectedLastEducationDegreeText = when (id) {
            -1 -> resources.getText(R.string.last_education_degree_indeterminate)
            else -> findViewById<RadioButton>(id).text
        }

        Toast.makeText(this, "$selectedMaritalStatusText, $selectedLastEducationDegreeText", Toast.LENGTH_SHORT).show()
    }

    fun onCloseButtonClicked(view: View) {

    }
}