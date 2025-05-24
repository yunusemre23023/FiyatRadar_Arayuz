package com.example.fiyat_radar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sevices.Product

class ProductAdapter(private var productList: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    fun updateList(newList: List<Product>) {
        productList = newList
        notifyDataSetChanged()
    }
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

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val urunAdi: TextView = itemView.findViewById(R.id.textUrunAdi)
        val fiyat: TextView = itemView.findViewById(R.id.textFiyat)
        val urunResim: ImageView = itemView.findViewById(R.id.imageUrun)
        val marketLogo: ImageView = itemView.findViewById(R.id.imageMarketLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_urun, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.urunAdi.text = product.productName
        holder.fiyat.text = "₺%.2f".format(product.price)


        val bas ="https://cdn.cimri.io/market/260x260/"
        val url = product.image
        val imageName = extractImageName(url)

        val basiki = "https://cdn.cimri.io/pictures/merchant-logos/"
        val son = ".png"
        val market = product.storeName
        val resimid = extractId(market)

        val linki = "$bas$imageName"
        val linkiki = "$basiki$resimid$son"
        Glide.with(holder.itemView.context)
            .load(linki)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.urunResim)

        // Market logosu farklıysa farklı URL'den yükleyebilirsin, şimdilik aynı
        Glide.with(holder.itemView.context)
            .load(linkiki)
            .into(holder.marketLogo)
    }

    override fun getItemCount(): Int = productList.size
}
