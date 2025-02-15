package com.edaakyil.android.basicviews

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.edaakyil.android.basicviews.data.service.UserService
import com.edaakyil.android.basicviews.model.UserRegisterInfoModel
import com.edaakyil.data.exception.DataServiceException

class UsersActivity : AppCompatActivity() {
    private lateinit var mTextViewUser: TextView
    private lateinit var mListViewUsers: ListView
    private lateinit var mArrayAdapterUsers: ArrayAdapter<UserRegisterInfoModel>
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
        initListViewUsers()
    }

    private fun initListViewUsers() {
        mListViewUsers = findViewById(R.id.usersActivityListViewUsers)
    }

    fun onLoadUsersButtonClicked(view: View) {
        try {
            val users = mUserService.findUsers(20)  // Must be asynchronous

            mArrayAdapterUsers = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
                .apply { mListViewUsers.adapter = this }
        } catch (ex: DataServiceException) {
            Toast.makeText(this, R.string.data_problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        } catch (ex: Exception) {
            Toast.makeText(this, R.string.problem_occurred_prompt, Toast.LENGTH_SHORT).show()
        }
    }
}