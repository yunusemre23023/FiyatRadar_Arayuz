package com.example.fiyat_radar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import retrofit2.Call
import sevices.RetrofitClient
import sevices.UserRegistrationDTO

class RegisterActivity : ComponentActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val kullanici_adi = findViewById<EditText>(R.id.registername)
        val e_mail = findViewById<EditText>(R.id.registerEmail)
        val sifre = findViewById<EditText>(R.id.registerPassword)
        val tsifre = findViewById<EditText>(R.id.registerrepeatPassword)

        val uyar = findViewById<TextView>(R.id.uyaritext)


        val kayit = findViewById<Button>(R.id.kayitButton)
        kayit.setOnClickListener {
            val username = kullanici_adi.text.toString()
            val email = e_mail.text.toString()
            val password = sifre.text.toString()
            val repeatPassword = tsifre.text.toString()

            if (password != repeatPassword) {
                uyar.text = "Şifreler uyuşmuyor"
                return@setOnClickListener
            }

            val user = UserRegistrationDTO(username, email, password)

            RetrofitClient.apiService.registerUser(user).enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: retrofit2.Response<Void>) {
                    if (response.isSuccessful) {
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else if (response.code() == 409) {
                        uyar.text = "Bu e-posta adresi zaten kayıtlı."
                    } else {
                        uyar.text = "Kayıt sırasında bir hata oluştu."
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    uyar.text = "Sunucuya bağlanılamadı: ${t.message}"
                }
            })
        }
}
}
