package com.example.fiyat_radar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sevices.Product
import sevices.ProductRepository

class Listeleme : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter
    private val repox = ProductRepository()

    private var allProducts: List<Product> = listOf()
    private var currentPage = 0
    private val pageSize = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listele)

        recyclerView = findViewById(R.id.recyclerViewUrunler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ProductAdapter(listOf())
        recyclerView.adapter = adapter

        val pageIndicator = findViewById<TextView>(R.id.pageIndicator)
        val buttonPrev = findViewById<Button>(R.id.buttonPrev)
        val buttonNext = findViewById<Button>(R.id.buttonNext)
        val buttonGeriDon = findViewById<Button>(R.id.buttonGeriDon)

        buttonGeriDon.setOnClickListener {
            startActivity(Intent(this, AnaSayfaActivity::class.java))
            finish()
        }

        fun updatePage() {
            val totalPages = (allProducts.size + pageSize - 1) / pageSize
            val fromIndex = currentPage * pageSize
            val toIndex = minOf(fromIndex + pageSize, allProducts.size)
            val currentList = allProducts.subList(fromIndex, toIndex)
            adapter.updateList(currentList)
            pageIndicator.text = "${currentPage + 1} / $totalPages"
            buttonPrev.isEnabled = currentPage > 0
            buttonNext.isEnabled = currentPage < totalPages - 1
        }

        buttonPrev.setOnClickListener {
            if (currentPage > 0) {
                currentPage--
                updatePage()
            }
        }

        buttonNext.setOnClickListener {
            val totalPages = (allProducts.size + pageSize - 1) / pageSize
            if (currentPage < totalPages - 1) {
                currentPage++
                updatePage()
            }
        }

        val urunAdi = UrunuAra.adi.trim()
        if (urunAdi.isNotEmpty()) {
            repox.getProductByName(urunAdi) { productList ->
                runOnUiThread {
                    if (!productList.isNullOrEmpty()) {
                        allProducts = productList
                        currentPage = 0
                        updatePage()
                    } else {
                        adapter.updateList(listOf())
                        pageIndicator.text = "0 / 0"
                    }
                }
            }
        }
    }
}
