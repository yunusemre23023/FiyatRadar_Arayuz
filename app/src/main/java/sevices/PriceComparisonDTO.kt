package sevices

import kotlinx.serialization.Serializable

@Serializable
data class PriceComparisonDTO(
    val storeName: String,
    val price: Double
)