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

    private fun initialize() {
        initViews()
    }

    private fun initViews() {
        mRadioGroupLastEducationDegree = findViewById(R.id.registerActivityRadioGroupLastEducationDegree)

        initRadioGroupMaritalStatus()

    }

    private fun initRadioGroupMaritalStatus() {
        mRadioGroupMaritalStatus = findViewById(R.id.registerActivityRadioGroupMaritalStatus)

        mRadioGroupMaritalStatus.getChildAt(0).tag = 'S'
        mRadioGroupMaritalStatus.getChildAt(1).tag = 'M'
        mRadioGroupMaritalStatus.getChildAt(2).tag = 'D'
    }

    fun onRegisterButtonClicked(view: View) {
        val selectedMaritalStatusValue = findViewById<RadioButton>(mRadioGroupMaritalStatus.checkedRadioButtonId).tag as Char

        val id= mRadioGroupLastEducationDegree.checkedRadioButtonId
        val selectedLastEducationDegreeValue = when (id) {
            -1 -> 0
            else -> findViewById<RadioButton>(id).tag.toString().toInt()
        }

        Toast.makeText(this, "$selectedMaritalStatusValue, $selectedLastEducationDegreeValue", Toast.LENGTH_SHORT).show()
    }

    fun onCloseButtonClicked(view: View) = finish()
}