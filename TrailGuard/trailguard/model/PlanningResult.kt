package trailguard.model

data class PlanningResult (val group: HikingGroup, val routeWish: RouteWish) {
    val routeOptions = ArrayList<HikingRoute>()
    var weather: WeatherConditions? = null
    val requiredClothing = ArrayList<String>()
    val warnings = ArrayList<String>()

    fun addRouteOption(route: HikingRoute) {
        routeOptions.add(route)
    }

    fun replaceRouteOptions(routes: List<HikingRoute>) {
        routeOptions.clear()
        routeOptions.addAll(routes)
    }

    fun markComplete(weather: WeatherConditions, requiredClothing: List<String>, warnings: List<String>) {
        this.weather = weather
        this.requiredClothing.addAll(requiredClothing)
        this.warnings.addAll(warnings)
    }
}