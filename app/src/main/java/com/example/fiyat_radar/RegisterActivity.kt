package com.example.fiyat_radar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val kayit = findViewById<Button>(R.id.kayitButton)
        kayit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
    }
}
}
