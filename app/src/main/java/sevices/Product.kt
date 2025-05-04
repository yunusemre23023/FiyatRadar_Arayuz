package sevices

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val productName: String,
    val barcode: String,
    val description: String,
    val price: Double,
    val storeName: String,
    val image: String
)