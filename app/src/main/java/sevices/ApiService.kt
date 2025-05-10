package sevices

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Örnek: Ürün ID'sine göre ürün bilgisi almak için GET isteği
    @GET("api/products/searchByBarcode")
    fun getProductDetails(@Query("barcode") barcode: String): Call<Product>

    @GET("/api/products")
    fun getAllProducts(): Call<List<Product>>

    @GET("api/products/search")
    fun getProductByName(@Query("name") name: String): Call<List<Product>>

    @POST("api/users/save")
    fun registerUser(@Body user: UserRegistrationDTO): Call<Void>

    @POST("api/users/login")
    @Headers("Content-Type: application/json")
    fun loginUser(@Body user: LoginRequest): Call<String>

}