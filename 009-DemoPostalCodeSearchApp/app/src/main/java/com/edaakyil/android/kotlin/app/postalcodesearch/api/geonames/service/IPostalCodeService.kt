package com.edaakyil.android.kotlin.app.postalcodesearch.api.geonames.service

import com.edaakyil.android.kotlin.app.postalcodesearch.api.geonames.dto.PostalCodes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/*
 * İlgili metotları oluşturmak için bir interface oluşturmamız gerek yani aslında bu bir çeşit API servis katmanıdır.
 *
 * Retrofit'in Call interface'i asenkronluğu sağlar. Eğer biz burada Coroutines kullanıyor olsaydık burda doğrudan türe grei dönecektik.
 *
 * http://api.geonames.org/postalCodeLookupJSON?postalcode=34843&username=csystem&coutry=tr
 *      ↪ base url -> http://api.geonames.org/
 *      ↪ endpoint -> postalCodeLookupJSON  => GET metodunun parametresi bunu temsil ediyor
 *      ↪ GET parameters -> postalcode=34843&username=csystem&coutry=tr  => findByPostalCode metodunun parametreleri bunu temsil ediyor
 *
 * Query annotation'u ile findByPostalCode metodunun parametrelerinin GET parametresi olduğunu söylememiz gerekiyor
 *
 * Bizim bunu kullanacak olan nesneye ihtiyacımız var.
 * Yani findByPostalCode metodunu çağırıp işlem yapabilmemiz için Retrofit sınıfı türünden bir nesneye ihtiyacımız var.
 * Onunla birlikte bütün bu metotları çağırabiliyoruz, onunla birlikte API servisi elde edebiliyoruz.
 */
interface IPostalCodeService {
    @GET("postalCodeLookupJSON")
    fun findByPostalCode(
        @Query("postalcode") postalCode: String,
        @Query("username") username: String,
        @Query("country") country: String
    ): Call<PostalCodes>
}