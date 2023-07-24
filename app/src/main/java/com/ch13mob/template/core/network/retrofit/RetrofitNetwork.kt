package com.ch13mob.template.core.network.retrofit

import com.ch13mob.template.BuildConfig
import com.ch13mob.template.core.network.NetworkDataSource
import com.ch13mob.template.core.network.model.NetworkQuote
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitAppNetworkApi {
    @GET(value = "v1/quotes")
    suspend fun getRandomQuote(): List<NetworkQuote>
}

// @Serializable
// private data class NetworkResponse<T>(
//    val data: T,
// )

@Singleton
class RetrofitNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : NetworkDataSource {

    private val networkApi = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(RetrofitAppNetworkApi::class.java)

    override suspend fun getRandomQuote() = networkApi.getRandomQuote()
}
