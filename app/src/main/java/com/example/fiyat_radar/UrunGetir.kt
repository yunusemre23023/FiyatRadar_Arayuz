package com.example.fiyat_radar

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import sevices.ProductRepository

class UrunGetir : ComponentActivity() {

    private lateinit var urunTextBir: TextView
    private lateinit var urunFiyat: TextView
    private lateinit var repository: ProductRepository
    fun extractImageName(url: String): String {
        val regex = Regex("file=([^&]+)") // "file=" parametresinden sonraki kısmı yakalar
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(1)?.value ?: "" // Eğer eşleşme varsa, istenen kısmı alır
    }
    fun extractId(url: String): String {
        val regex = Regex("id=(\\d+)") // "id=" parametresinin ardından gelen sayıları yakalar
        val matchResult = regex.find(url)
        return matchResult?.groups?.get(1)?.value ?: "" // Eğer eşleşme varsa, istenen kısmı alır
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_urun_getir) // Layout'u bağla

        urunFiyat = findViewById(R.id.urunFiyatText)
        urunTextBir = findViewById(R.id.urunugoster) // TextView'i bağla
        repository = ProductRepository()


        val barkod = BarcodeData.no
        var baglanti = ""
        repository.getProductDetails(barkod) { product ->
            if (product != null) {
                val fiyatStr = String.format("%.2f", product.price)
                val market = product.storeName
                val resim = product.image
                val resultText = """
                ${product.productName}
                """.trimIndent()

                urunTextBir.text = resultText // UI'yi güncelle
                urunFiyat.text = "$fiyatStr TL"

                val bas ="https://cdn.cimri.io/market/260x260/"
                val url = resim
                val imageName = extractImageName(url)

                val basiki = "https://cdn.cimri.io/pictures/merchant-logos/"
                val son = ".png"
                val resimid = extractId(market)

                val gorseliki = findViewById<ImageView>(R.id.urunImageAlt)
                val linkiki = "$basiki$resimid$son"

                val gorsel = findViewById<ImageView>(R.id.urunImage)
                val linki = "$bas$imageName"

                Glide.with(this)
                    .load(linki)
                    .placeholder(android.R.drawable.ic_menu_gallery) // Yüklenirken gösterilecek görsel
                    .error(android.R.drawable.stat_notify_error)     // Hata durumunda gösterilecek görsel
                    .into(gorsel)

                Glide.with(this)
                    .load(linkiki)
                    .placeholder(android.R.drawable.ic_menu_gallery) // Yüklenirken gösterilecek görsel
                    .error(android.R.drawable.stat_notify_error)     // Hata durumunda gösterilecek görsel
                    .into(gorseliki)
            } else {
                urunTextBir.text = "Ürün bilgisi alınamadı" // Hata mesajı
            }

        }
    }
}
