package template.core.network.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import template.BuildConfig
import template.core.network.NetworkDataSource
import template.core.network.model.NetworkPost
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitAppNetworkApi {
    @GET(value = "posts")
    suspend fun getPosts(): List<NetworkPost>
}

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

    override suspend fun getPosts() = networkApi.getPosts()
}
