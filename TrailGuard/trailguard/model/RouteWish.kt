package trailguard.model

data class RouteWish(
    val name: String,
    val location: Location,
    val distanceKm: Double,
    val elevationGainMeters: Int,
) {
    init {
        require(name.isNotBlank()) { "Name must not be empty" }
        require(distanceKm > 0.0) { "DistanceKm must be positive" }
        require(elevationGainMeters >= 0.0) { "ElevationGainMeters must be positive" }
    }
}
