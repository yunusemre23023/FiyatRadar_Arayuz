import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fiyat_radar.R
import sevices.PriceComparisonDTO

class PriceComparisonAdapter(private val comparisonList: List<PriceComparisonDTO>) :
    RecyclerView.Adapter<PriceComparisonAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val marketAdi: TextView = itemView.findViewById(R.id.textMarketAdi)
        val fiyat: TextView = itemView.findViewById(R.id.textFiyat)
        val marketLogo: ImageView = itemView.findViewById(R.id.imageMarketLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_price_comparison, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = comparisonList[position]
        holder.marketAdi.text = item.storeName
        holder.fiyat.text = "₺%.2f".format(item.price)

        // Market logosunu storeName'den al
        val baseUrl = "https://cdn.cimri.io/pictures/merchant-logos/"
        var marketId = ""
        if(holder.marketAdi.text=="pttAvm"){
            marketId = "11512"
        }
        else if(holder.marketAdi.text=="Şok"){
            marketId="15057"
        }
        else if(holder.marketAdi.text=="a101"){
            marketId="15055"
        }
        else if(holder.marketAdi.text=="Carrefour"){
            marketId="15058"
        }
        else if(holder.marketAdi.text=="Tarım Kredi Kooperatifi"){
            marketId="15059"
        }
        else if(holder.marketAdi.text=="Bim"){
            marketId="15054"
        }


        val logoUrl = "$baseUrl$marketId.png"

        Glide.with(holder.itemView.context)
            .load(logoUrl)
            .into(holder.marketLogo)
    }

    override fun getItemCount(): Int = comparisonList.size

}
