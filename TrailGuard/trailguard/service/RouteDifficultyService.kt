package trailguard.service

import trailguard.model.RouteCategory

class RouteDifficultyService {
    fun calculateCategory(distanceKm: Double, elevationGainMeters: Int) = when {
        distanceKm <= 8 && elevationGainMeters <= 250 -> RouteCategory.GREEN
        distanceKm <= 14 && elevationGainMeters <= 600 -> RouteCategory.BLUE
        distanceKm <= 20 && elevationGainMeters <= 1000 -> RouteCategory.RED
        else -> RouteCategory.BLACK
    }
}