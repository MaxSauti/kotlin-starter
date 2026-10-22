package trailguard.service

import trailguard.model.RouteCategory

class RouteDifficultyService {
    fun calculateCategory(distanceKm: Double, elevationGainMeters: Int): RouteCategory {
        if (distanceKm <= 8 && elevationGainMeters <= 250) return RouteCategory.GREEN
        if (distanceKm <= 14 && elevationGainMeters <= 600) return RouteCategory.BLUE
        if (distanceKm <= 20 && elevationGainMeters <= 1000) return RouteCategory.RED
        return RouteCategory.BLACK
    }
}