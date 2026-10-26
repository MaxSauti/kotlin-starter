package trailguard.model

data class RouteProfile(var distanceKm: Double, var elevationGainMeters: Int, var category: RouteCategory) {
    init {
        require(distanceKm > 0.0) { "DistanceKm must be positive" }
        require(elevationGainMeters >= 0.0) { "ElevationGainMeters must be positive" }
    }
}