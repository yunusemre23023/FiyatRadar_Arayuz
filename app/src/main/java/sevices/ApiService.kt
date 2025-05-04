package sevices

import retrofit2.Call
import retrofit2.http.GET
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


}