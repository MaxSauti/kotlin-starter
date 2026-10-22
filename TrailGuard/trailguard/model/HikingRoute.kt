package trailguard.model

class HikingRoute(var name: String, val location: Location, distanceKm: Double, elevationGainMeters: Int, category: RouteCategory) {
    val profile: RouteProfile
    init {
      profile = RouteProfile(distanceKm, elevationGainMeters, category)
    }

    constructor(other: HikingRoute) : this(other.name, other.location, other.profile.distanceKm, other.profile.elevationGainMeters, other.profile.category)
}