package sevices

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Log

class ProductRepository {

    fun getProductDetails(productId: String, callback: (Product?) -> Unit) {
        RetrofitClient.apiService.getProductDetails(productId).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val product = response.body()
                    callback(product) // sonucu geri gönderiyoruz
                } else {
                    callback(null)
                    Log.e("Product", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                callback(null)
                Log.e("Product", "Request failed: ${t.message}")
            }
        })
    }

}