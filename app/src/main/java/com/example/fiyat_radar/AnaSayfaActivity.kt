package com.example.fiyat_radar

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class AnaSayfaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ana_sayfa)

        val buttonToggle = findViewById<Button>(R.id.buttonToggle)
        val hiddenLayout = findViewById<LinearLayout>(R.id.hiddenLayout)

        val camerabutton = findViewById<Button>(R.id.buttonkamera)
        camerabutton.setOnClickListener {
            val intent = Intent(this, KameraActivity::class.java)
            startActivity(intent)


        }

        buttonToggle.setOnClickListener {
            if (hiddenLayout.visibility == View.GONE) {
                hiddenLayout.visibility = View.VISIBLE
                buttonToggle.text = "Detayları Gizle"
            } else {
                hiddenLayout.visibility = View.GONE
                buttonToggle.text = "Ürün Ekle"
            }
        }

        val buttonToggletwo = findViewById<Button>(R.id.buttonToggleiki)
        val hiddenLayouttwo = findViewById<LinearLayout>(R.id.hiddenLayoutiki)

        buttonToggletwo.setOnClickListener {
            if (hiddenLayouttwo.visibility == View.GONE) {
                hiddenLayouttwo.visibility = View.VISIBLE
                buttonToggletwo.text = "Detayları Gizle"
            } else {
                hiddenLayouttwo.visibility = View.GONE
                buttonToggletwo.text = "Ürün Ara"
            }
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


    }
}
