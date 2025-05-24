package com.example.fiyat_radar

import PriceComparisonAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sevices.PriceComparisonDTO
import sevices.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetayGoruntule : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detay)

        recyclerView = findViewById(R.id.recyclerViewFiyatlar)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val barcode = intent.getStringExtra("barcode")
        if (barcode != null) {
            getPriceComparisons(barcode)
        } else {
            Toast.makeText(this, "Barkod bilgisi alınamadı", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun getPriceComparisons(barcode: String) {
        RetrofitClient.apiService.getPriceComparisonsByBarcode(barcode)
            .enqueue(object : Callback<List<PriceComparisonDTO>> {
                override fun onResponse(
                    call: Call<List<PriceComparisonDTO>>,
                    response: Response<List<PriceComparisonDTO>>
                ) {
                    if (response.isSuccessful) {
                        val comparisons = response.body()
                        if (!comparisons.isNullOrEmpty()) {
                            val adapter = PriceComparisonAdapter(comparisons)
                            recyclerView.adapter = adapter
                        } else {
                            Toast.makeText(this@DetayGoruntule, "Karşılaştırma bulunamadı", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@DetayGoruntule, "Sunucu hatası", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<PriceComparisonDTO>>, t: Throwable) {
                    Toast.makeText(this@DetayGoruntule, "Hata: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}

