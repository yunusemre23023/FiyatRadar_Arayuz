package sevices

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://192.168.1.190:8080/"  // Backend IP adresinizi burada belirtiyorsunuz

    // Retrofit nesnesini oluşturma
    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // JSON'dan Kotlin objelerine dönüşüm
            .build()
    }

    // API servis sınıfını oluşturma
    val apiService: ApiService by lazy {
        retrofitInstance.create(ApiService::class.java)
       }
}