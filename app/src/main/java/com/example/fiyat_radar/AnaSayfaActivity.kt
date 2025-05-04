package com.example.fiyat_radar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import sevices.ProductRepository

class AnaSayfaActivity : ComponentActivity() {private lateinit var repository: ProductRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ana_sayfa)


        val camerabutton = findViewById<Button>(R.id.buttonkamera)
        camerabutton.setOnClickListener {
            val intent = Intent(this, KameraActivity::class.java)
            startActivity(intent)
        }


        val buttonTogglet = findViewById<Button>(R.id.buttonToggleuc)
        val hiddenLayoutt = findViewById<LinearLayout>(R.id.hiddenLayoutuc)

        buttonTogglet.setOnClickListener {
            if (hiddenLayoutt.visibility == View.GONE) {
                hiddenLayoutt.visibility = View.VISIBLE
                buttonTogglet.text = "Detayları Gizle"
            } else {
                hiddenLayoutt.visibility = View.GONE
                buttonTogglet.text = "Listele"
            }
        }

        val urunTextBir: TextView = findViewById(R.id.uruntextbir)

        val repo = ProductRepository()
        repo.getProductDetails("121121212345") { product ->
            if (product != null) {
                val fiyatStr = String.format("%.2f", product.price)
                val market = product.storeName
                val aciklama = product.description
                val resultText = """
            Ürün: ${product.productName}
            Fiyat: $fiyatStr
            Market: $market
            Açıklama: $aciklama
        """.trimIndent()

                urunTextBir.text = resultText
            } else {
                urunTextBir.text = "Ürün bilgisi alınamadı"
            }
        }

        val urunTextIki: TextView = findViewById(R.id.uruntextiki)

        val repoiki = ProductRepository()

        repoiki.fetchAllProducts { resultTextiki ->
            urunTextIki.post {
                urunTextIki.text = resultTextiki
            }
        }

        val buttonAra = findViewById<Button>(R.id.buttonara)
        val editTextUrunAdi = findViewById<EditText>(R.id.edittextara)
        val urungoster = findViewById<TextView>(R.id.uruntextbir)
        val repouc = ProductRepository()

        buttonAra.setOnClickListener {
            val urunAdi = editTextUrunAdi.text.toString().trim()

            if (urunAdi.isNotEmpty()) {
                repouc.getProductByName(urunAdi) { productList ->
                    runOnUiThread {
                        if (!productList.isNullOrEmpty()) {
                            val result = productList.joinToString(separator = "\n\n") { product ->
                                val fiyatStr = String.format("%.2f", product.price)
                                """
                        Ürün: ${product.productName}
                        Fiyat: $fiyatStr
                        Market: ${product.storeName}
                        Açıklama: ${product.description}
                        """.trimIndent()
                            }
                            urungoster.text = result
                        } else {
                            urungoster.text = "Ürün bulunamadı."
                        }
                    }
                }
            } else {
                urungoster.text = "Lütfen bir ürün adı girin."
            }
        }
        val bas ="https://cdn.cimri.io"
        val resim = findViewById<ImageView>(R.id.logoImageView)
        val linki = "$bas/market/260x260/mayi-tuz-2-kg-delice-iyotlu-ince-kaynak-tuzu-_1948277.jpg"

        Glide.with(this)
            .load(linki)
            .into(resim)




        // Repository'yi başlat
           /* repository = ProductRepository()

            // Örnek olarak ürün bilgilerini almak için istek gönderiyoruz
            val barcode_no = "121121212345"
            repository.getProductDetails(barcode_no)*/



    }
}
