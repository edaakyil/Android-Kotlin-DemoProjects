package com.edaakyil.android.kotlin.app.postalcodesearch.api.geonames.dto

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostalCodes(
    @SerializedName("postalcodes")
    val postalCodes: List<PostalCode>
): Serializable