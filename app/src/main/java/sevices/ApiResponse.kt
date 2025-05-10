package sevices

data class ApiResponse(
    val status: String,
    val message: String,
    val user: User
)

data class User(
    val id: Int,
    val username: String
)