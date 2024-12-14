package com.edaakyil.app.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.edaakyil.app.android.basicviews.constant.MARITAL_STATUS_TAGS

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

        /*
        for (i in 0..<mRadioGroupMaritalStatus.childCount)
        mRadioGroupMaritalStatus.getChildAt(i).tag = MARITAL_STATUS_TAGS[i]
        */

        (0..<mRadioGroupMaritalStatus.childCount).forEach { mRadioGroupMaritalStatus.getChildAt(it).tag = MARITAL_STATUS_TAGS[it] }

        mRadioGroupMaritalStatus.setOnCheckedChangeListener { _, id -> Toast.makeText(this, "Cheched: ${findViewById<RadioButton>(id).text}", Toast.LENGTH_SHORT).show() }
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

    fun onCloseButtonClicked(view: View) = finish()
}