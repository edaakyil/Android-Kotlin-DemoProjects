package com.edaakyil.android.kotlin.app.postalcodesearch

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.kotlin.app.postalcodesearch.api.geonames.constant.STATUS_OK
import com.edaakyil.android.kotlin.app.postalcodesearch.api.geonames.dto.PostalCode
import com.edaakyil.android.kotlin.app.postalcodesearch.api.geonames.dto.PostalCodes
import com.edaakyil.android.kotlin.app.postalcodesearch.api.geonames.service.IPostalCodeService
import com.edaakyil.android.kotlin.app.postalcodesearch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    @Inject
    lateinit var postalCodeService: IPostalCodeService

    private fun postalCodesCallback(): Callback<PostalCodes> {
        return object: Callback<PostalCodes> {
            override fun onResponse(call: Call<PostalCodes?>, response: Response<PostalCodes?>) {
                Log.i("Response:Raw", response.raw().toString())

                if (response.code() != STATUS_OK) {
                    Log.e("Response:Status", response.code().toString())
                    Toast.makeText(this@MainActivity, "Unsuccessful operation", Toast.LENGTH_SHORT).show()
                    return
                }

                if (response.body()?.postalCodes == null) {
                    Toast.makeText(this@MainActivity, "Limit exhausted", Toast.LENGTH_SHORT).show()
                    return
                }

                mBinding.adapter!!.clear()

                response.body()!!.postalCodes.forEach { it -> mBinding.adapter!!.add(it) }
            }

            override fun onFailure(call: Call<PostalCodes?>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
                Toast.makeText(this@MainActivity, "Error occurred while connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initModels() {
        mBinding.activity = this
        mBinding.postalCode= ""
        mBinding.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, ArrayList<PostalCode>())
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initModels()
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainActivityMainLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }

    fun onGetPlacesButtonClicked() {
        val call = postalCodeService.findByPostalCode(mBinding.postalCode!!, "csystem", "tr")
        call.enqueue(postalCodesCallback())
    }

    fun onPlaceClicked(position: Int) {
        val postalCode = mBinding.adapter!!.getItem(position)

        Toast.makeText(this, postalCode.toString(), Toast.LENGTH_SHORT).show()
    }
}