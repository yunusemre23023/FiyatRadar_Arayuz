package sevices

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class ProductRepository {

    fun getProductDetails(productId: String) {
        // Retrofit API servisini kullanarak istek atıyoruz
        RetrofitClient.apiService.getProductDetails(productId).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    // Başarılı yanıt aldıysak, response.body() ile veriye erişebiliriz
                    val product = response.body()
                    Log.d("Product", "Product Name: ${product?.productName}")
                } else {
                    // Hata mesajını logluyoruz
                    Log.e("Product", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                // İstek başarısız olursa hata mesajı loglanır
                Log.e("Product", "Request failed: ${t.message}")
            }
            })
        }
}