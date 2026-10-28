package trailguard.model

data class HikingRoute(var name: String, val location: Location, val profile: RouteProfile) {
    init {
        require(name.isNotBlank()) { "Name must not be empty" }
    }

    constructor(name: String, location: Location, distanceKm: Double, elevationGainMeters: Int, category: RouteCategory) : this(
        name, location, RouteProfile(distanceKm, elevationGainMeters, category)
    )
}