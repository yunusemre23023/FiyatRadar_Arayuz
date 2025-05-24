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



        val buttonAra = findViewById<Button>(R.id.buttonara)
        val editTextUrunAdi = findViewById<EditText>(R.id.edittextara)

        buttonAra.setOnClickListener {
            val urunAdi = editTextUrunAdi.text.toString().trim()
            UrunuAra.adi = editTextUrunAdi.text.toString()
            val intent = Intent(this, Listeleme::class.java)
            startActivity(intent)
        }





        // Repository'yi başlat
           /* repository = ProductRepository()

            // Örnek olarak ürün bilgilerini almak için istek gönderiyoruz
            val barcode_no = "121121212345"
            repository.getProductDetails(barcode_no)*/



    }
}
