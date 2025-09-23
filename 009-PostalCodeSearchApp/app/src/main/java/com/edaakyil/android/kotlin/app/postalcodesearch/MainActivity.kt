package com.edaakyil.android.kotlin.app.postalcodesearch

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.edaakyil.android.kotlin.app.postalcodesearch.api.geonames.constant.STATUS_OK
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
                if (response.code() != STATUS_OK) {
                    Log.e("onResponse", response.code().toString())
                    Toast.makeText(this@MainActivity, "Unsuccessful operation", Toast.LENGTH_SHORT).show()
                    return
                }

                Log.i("onResponse.code", response.code().toString())
                Log.i("onResponse.message", response.message().toString())
                Log.i("onResponse.body", response.body().toString())
                Log.i("onResponse.headers", response.headers().toString())
                Log.i("onResponse.raw", response.raw().toString())

                response.body()!!.postalCodes.forEach { it ->
                    Toast.makeText(this@MainActivity, it.placeName, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<PostalCodes?>, t: Throwable) {
                Log.e("onFailure", t.message.toString())
                Toast.makeText(this@MainActivity, "Error occurred while connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initModels() {
    }

    private fun initBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initModels()
    }

    private fun initialize() {
        enableEdgeToEdge()
        initBinding()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val call = postalCodeService.findByPostalCode("34843", "csystem", "tr")

        // enqueue yaptiğimizde biz sonucu elde ediyoruz.
        // enqueue returns Callback<PostalCodes> ve Callback, functional bir interface değildir.
        // enqueue asenkron olarak çalışır. Ama enqueue'nin bize verdiği callback'ler, retrofit'i nerede engueue yaptısak o thread'de çalışır.
        // Yani onResponse ve onFailure metotları main (ui) thread'e çağırılacak yani enqueue asenkronluğu arkaplanda kendi yapıyor.
        call.enqueue(postalCodesCallback())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initialize()
    }
}

// response.code() -> 200
// response.message() -> 200
// response.body() -> PostalCodes(postalCodes=[Feyzullah])
// response.raw() -> Response{protocol=http/1.1, code=200, message=200, url=http://api.geonames.org/postalCodeLookupJSON?postalcode=34843&username=csystem&country=tr}
// response.headers():
    // Date: Tue, 23 Sep 2025 01:36:08 GMT
    // Server: Apache/2.4.37 (AlmaLinux) mod_jk/1.2.49 OpenSSL/1.1.1k
    // Access-Control-Allow-Origin: *
    // X-Frame-Options: sameorigin
    // Cache-Control: no-cache
    // Keep-Alive: timeout=5, max=100
    // Connection: Keep-Alive
    // Transfer-Encoding: chunked
    // Content-Type: application/json;charset=UTF-8
