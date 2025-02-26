package com.edaakyil.android.demolibraryusageapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.basicviews.data.service.UserService
import com.edaakyil.android.basicviews.data.service.model.UserModel
import com.edaakyil.android.demolibraryusageapp.constant.DEFAULT_USER_COUNT
import com.edaakyil.android.demolibraryusageapp.databinding.ActivityUsersBinding
import com.edaakyil.lib.java.data.exception.DataServiceException

class UsersActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityUsersBinding
    private lateinit var mUserService: UserService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    private fun initialize() {
        initBinding()
        mUserService = UserService(this)
    }

    private fun initBinding() {
        enableEdgeToEdge()
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_users)
        initModels()
    }

    private fun initModels() {
        mBinding.activity = this
        mBinding.countStr = ""
        mBinding.nameSelectedUser = resources.getString(R.string.select_user_text)
        mBinding.listViewAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<UserModel>())
    }

    fun onLoadUsersButtonClicked() {
        try {
            mBinding.listViewAdapter!!.clear()

            val countStr = mBinding.countStr!!.trim()
            var count = DEFAULT_USER_COUNT  // max value of count (default value)

            if (countStr.isNotBlank())
                count = countStr.toInt()

            if (count <= 0) {
                Toast.makeText(this, R.string.value_must_be_positive_prompt, Toast.LENGTH_SHORT).show()
                mBinding.countStr = ""
                return
            }

            val users = mUserService.findUsers(count)  // Must be asynchronous
            mBinding.listViewAdapter!!.addAll(users)
            mBinding.countStr = ""
            mBinding.nameSelectedUser = resources.getString(R.string.select_user_text)
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

    fun onListViewItemClicked(position: Int) {
        val user = mBinding.listViewAdapter!!.getItem(position)

        //mBinding.nameSelectedUser = "Selected user: " + user?.name  //mBinding.nameSelectedUser = user?.name
        mBinding.nameSelectedUser = resources.getString(R.string.selected_user_text).format(user?.name)

        Toast.makeText(this, user?.name, Toast.LENGTH_SHORT).show()
    }
}