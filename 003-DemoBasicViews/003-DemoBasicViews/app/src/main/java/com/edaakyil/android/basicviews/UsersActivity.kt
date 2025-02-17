package com.edaakyil.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.edaakyil.android.basicviews.constant.DEFAULT_USER_COUNT
import com.edaakyil.android.basicviews.data.service.UserService
import com.edaakyil.android.basicviews.model.UserModel
import com.edaakyil.data.exception.DataServiceException

class UsersActivity : AppCompatActivity() {
    private lateinit var mTextViewUser: TextView
    private lateinit var mEditTextCount: EditText
    private lateinit var mListViewUsers: ListView
    private lateinit var mArrayAdapterUsers: ArrayAdapter<UserModel>
    private lateinit var mUserService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_users)

        initialize()
    }

    private fun initialize() {
        mUserService = UserService(this)
        initViews()
    }

    private fun initViews() {
        mTextViewUser = findViewById(R.id.usersActivityTextViewUser)
        mEditTextCount = findViewById(R.id.usersActivityEditTextCount)
        initListViewUsers()
    }

    private fun initListViewUsers() {
        mListViewUsers = findViewById(R.id.usersActivityListViewUsers)
    }

    fun onLoadUsersButtonClicked(view: View) {
        try {
            val countStr = mEditTextCount.text.toString().trim()
            var count = DEFAULT_USER_COUNT  // max value of count (default value)

            if (countStr.isNotBlank())
                count = countStr.toInt()

            val users = mUserService.findUsers(count)  // Must be asynchronous

            mArrayAdapterUsers = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
                .apply { mListViewUsers.adapter = this }

            mEditTextCount.text.clear()
        } catch (_: NumberFormatException) {
            AlertDialog.Builder(this)
                .setTitle(R.string.alert_dialog_alert_title)
                .setMessage(R.string.invalid_count_value_prompt)
                .setPositiveButton(R.string.alert_dialog_ok) { _, _ -> }
                .create()
                .show()
        }catch (ex: DataServiceException) {
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }
}