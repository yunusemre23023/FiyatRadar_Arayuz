package sevices

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.165:8080/"

    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create()) // Bu önce gelmeli
            .addConverterFactory(GsonConverterFactory.create())    // Bu sonra gelmeli
            .build()
    }

    val apiService: ApiService by lazy {
        retrofitInstance.create(ApiService::class.java)
    }
}