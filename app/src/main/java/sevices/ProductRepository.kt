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
    fun fetchAllProducts(callback: (String) -> Unit) {
        RetrofitClient.apiService.getAllProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val products = response.body()
                    val productsText = StringBuilder()

                    products?.forEach { product ->
                        productsText.append("Ürün Adı: ${product.productName}\n")
                        productsText.append("Fiyat: ${product.price}\n\n")
                    }

                    callback(productsText.toString()) // Veriyi geri gönderiyoruz
                } else {
                    callback("Hata kodu: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback("İstek başarısız: ${t.message}")
            }
        })
    }

    fun getProductByName(productName: String, callback: (List<Product>?) -> Unit) {
        RetrofitClient.apiService.getProductByName(productName).enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val productList = response.body()
                    callback(productList)
                } else {
                    callback(null)
                    Log.e("Product", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                callback(null)
                Log.e("Product", "Request failed: ${t.message}")
            }
        })
    }



}
