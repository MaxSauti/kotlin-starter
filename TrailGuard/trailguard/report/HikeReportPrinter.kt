package trailguard.report

import trailguard.model.HikingRoute
import trailguard.model.PlanningResult
import trailguard.model.RouteCategory
import trailguard.model.WeatherConditions
import kotlin.math.round

class HikeReportPrinter {
    fun print(result: PlanningResult) {
        println("Planung für Gruppe: " + result.group.name)
        println("Wunschroute: " + result.routeWish.name)
        println("Ort: " + result.routeWish.location.searchTerm)
        println("Gewünschte Distanz: " + rounded(result.routeWish.distanceKm) + " km")
        println("Gewünschter Höhengewinn: " + result.routeWish.elevationGainMeters + " m")

        result.weather?.let { printWeather(it) }
            ?: { println("Wettergrundlage : null") }

        println("Kleidungsempfehlung: " + displayList(result.requiredClothing))
        println("Wetterhinweise: " + displayList(result.warnings))
        println("Verbleibende Routenoptionen: " + result.routeOptions.size)
        printRoutes(result.routeOptions)

        if (result.routeOptions.isEmpty()) println("Für diese Gruppe und Wetterlage bleibt keine passende Route übrig.")
        else println("Die Gruppe entscheidet selbst zwischen den verbleibenden Optionen.")
        println()
    }

    fun printWeather(weather: WeatherConditions) {}

    fun printRoutes(routes: List<HikingRoute>) {}

    fun categoryDescription(category: RouteCategory) {}

    fun displayList(values: List<String>) {}

    fun rounded(value: Double) = round(value * 10.0) / 10.0
}