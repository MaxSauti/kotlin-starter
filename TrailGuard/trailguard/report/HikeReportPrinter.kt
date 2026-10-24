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
            ?: run {
                println("Wettergrundlage : null")
                result.routeOptions.clear()
            }

        println("Kleidungsempfehlung: " + displayList(result.requiredClothing))
        println("Wetterhinweise: " + displayList(result.warnings))
        println("Verbleibende Routenoptionen: " + result.routeOptions.size)
        printRoutes(result.routeOptions)

        if (result.routeOptions.isEmpty()) println("Für diese Gruppe und Wetterlage bleibt keine passende Route übrig.")
        else println("Die Gruppe entscheidet selbst zwischen den verbleibenden Optionen.")
        println()
    }

    fun printWeather(weather: WeatherConditions) {
        println("Wettergrundlage: ")
        println("  Höchsttemperatur: " + weather.maximumTemperatureCelsius + " °C")
        println("  Regenwahrscheinlichkeit: " + weather.rainProbabilityPercent + " %")
        println("  Erwarteter Niederschlag: " + weather.precipitationMillimeters + " mm")
        println("  Maximale Windböen: " + weather.maximumWindGustKmh + " km/h")
    }

    fun printRoutes(routes: List<HikingRoute>) {
        var index = 1
        for (route in routes) {
            println("Option " + (index++) + ":")
            println("  Name: " + route.name)
            println("  Distanz: " + rounded(route.profile.distanceKm) + " km")
            println("  Höhengewinn: " + route.profile.elevationGainMeters + " m")
            println("  Schwierigkeit: " + categoryDescription(route.profile.category))
        }
    }

    fun categoryDescription(category: RouteCategory) = when (category) {
        RouteCategory.GREEN -> "🟢 GREEN – leicht, entspannte Wanderung"
        RouteCategory.BLUE -> "🔵 BLUE – moderat, etwas Kondition nötig"
        RouteCategory.RED -> "🔴 RED – anspruchsvoll, gute Kondition"
        else -> "⚫ BLACK – sehr anspruchsvoll / lange Bergtour"
    }

    fun displayList(values: List<String>) = if (values.isEmpty()) "keine" else values.joinToString(", ")

    fun rounded(value: Double) = round(value * 10.0) / 10.0
}