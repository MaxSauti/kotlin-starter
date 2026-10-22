package trailguard.model

data class RouteWish(
    val name: String,
    val location: Location,
    val distanceKm: Double,
    val elevationGainMeters: Int,
)
