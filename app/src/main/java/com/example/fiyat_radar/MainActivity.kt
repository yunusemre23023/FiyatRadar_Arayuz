package com.example.fiyat_radar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fiyat_radar.ui.theme.Fiyat_RadarTheme
import retrofit2.Call
import sevices.ApiResponse
import sevices.LoginRequest
import sevices.RetrofitClient
import sevices.UserRegistrationDTO

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val mail = findViewById<EditText>(R.id.kullaniciaditext)
        val sifre = findViewById<EditText>(R.id.passwordEditText)

        val registerButton = findViewById<Button>(R.id.registerButton)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val girisYapButton = findViewById<Button>(R.id.loginButton)
        girisYapButton.setOnClickListener {
            val username = mail.text.toString()
            val password = sifre.text.toString()

            val loginRequest = LoginRequest(username, password)

            RetrofitClient.apiService.loginUser(loginRequest).enqueue(object : retrofit2.Callback<String> {
                override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                    if (response.isSuccessful) {
                        // Yanıt başarılıysa
                        val loginMessage = response.body()  // Gelen mesajı alıyoruz
                        if (loginMessage != null && loginMessage.contains("Hoş geldin")) {
                            // Giriş başarılı ise
                            val intent = Intent(this@MainActivity, AnaSayfaActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Hata mesajı
                            Toast.makeText(this@MainActivity, "Hata: $loginMessage", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        // Sunucu hatası
                        Toast.makeText(this@MainActivity, "Hatalı Giris: ${response.code()} - ${response.message()}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    // Bağlantı hatası
                    Toast.makeText(this@MainActivity, "Sunucu hatası: ${t.message}", Toast.LENGTH_LONG).show()
                }
            })
        }

    }
}
