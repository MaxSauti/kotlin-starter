package trailguard.service

import trailguard.constants.PRECIPITATION_MAX
import trailguard.constants.RAIN_MAX
import trailguard.model.ExperienceLevel
import trailguard.model.HikingGroup
import trailguard.model.HikingRoute
import trailguard.model.PlanningResult
import trailguard.model.RouteCategory
import trailguard.model.RouteWish
import trailguard.model.WeatherConditions

class PlanningService(val weatherService: WeatherService, val recommendationService: WeatherRecommendationService, val difficultyService: RouteDifficultyService) {

    fun planRoutes(group: HikingGroup, routeWish: RouteWish): PlanningResult {
        val result = createInitialResult(group, routeWish)
        val weather = weatherService.getWeather(routeWish.location) ?: run {
            result.routeOptions.clear()
            return result
        }
        return finishPlanning(result, group, weather)
    }

    fun createInitialResult(group: HikingGroup, routeWish: RouteWish): PlanningResult {
        val result = PlanningResult(group, routeWish)
        //if (weatherService.getWeather(routeWish.location) == null) return result
        val requestedCategory = difficultyService.calculateCategory(routeWish.distanceKm, routeWish.elevationGainMeters)
        result.addRouteOption(HikingRoute(routeWish.name, routeWish.location, routeWish.distanceKm, routeWish.elevationGainMeters, requestedCategory))
        return result
    }

    fun finishPlanning(result: PlanningResult, group: HikingGroup, weather: WeatherConditions): PlanningResult {
        val recommendation = recommendationService.createRecommendation(weather)
        val requestedRoute = result.routeOptions.first()
        val groupLimit = maximumCategoryForGroup(group)
        result.addRouteOption(createLongerRoute(requestedRoute, groupLimit, weather, true, false))
        result.addRouteOption(createLongerRoute(requestedRoute, groupLimit, weather, false, false))

        if (group.premiumCustomer) {
            result.addRouteOption(createLongerRoute(requestedRoute, groupLimit, weather, true, true))
            result.addRouteOption(createLongerRoute(requestedRoute, groupLimit, weather, false, true))
        }

        result.replaceRouteOptions(filterAllowedRoutes(result.routeOptions, groupLimit, weather))
        result.markComplete(weather, recommendation.requiredClothing, recommendation.warnings)
        return result
    }

    fun createLongerRoute(requestedRoute: HikingRoute, groupLimit: RouteCategory, weather: WeatherConditions, longer: Boolean, premium: Boolean): HikingRoute {
        var distanceFac = 0.6
        var elevationFac = 0.3
        var description = " – Kürzer und deutlich leichter"
        when {
            longer && premium -> {
                distanceFac = 1.25
                elevationFac = 1.2
                description = " – Premium anspruchsvoller"
            }
            longer && !premium -> {
                distanceFac = 1.6
                elevationFac = 1.5
                description = " – Länger und anspruchsvoller"
            }
            !longer && premium -> {
                distanceFac = 0.8
                elevationFac = 0.7
                description = " – Premium leichter"
            }
        }
        val distance = requestedRoute.profile.distanceKm * distanceFac
        val elevation = (requestedRoute.profile.elevationGainMeters * elevationFac).toInt()
        val route = HikingRoute(requestedRoute.name + description, requestedRoute.location,
            distance, elevation,
            difficultyService.calculateCategory(distance, elevation))
        return if (premium) findRoutes(route, groupLimit, weather) else route
    }
/*
    fun createEasierRoute(requestedRoute: HikingRoute): HikingRoute {
        val shorterDistance = requestedRoute.profile.distanceKm * 0.6
        val lowerElevation = (requestedRoute.profile.elevationGainMeters * 0.3).toInt()
        return HikingRoute(requestedRoute.name + " – Kürzer und deutlich leichter", requestedRoute.location,
            shorterDistance, lowerElevation,
            difficultyService.calculateCategory(shorterDistance, lowerElevation))
    }

    fun createPremiumLongerRoute(requestedRoute: HikingRoute, groupLimit: RouteCategory, weather: WeatherConditions): HikingRoute {
        val premiumRoute = HikingRoute(requestedRoute)
        premiumRoute.name = requestedRoute.name + " – Premium anspruchsvoller"
        val premiumDistance = requestedRoute.profile.distanceKm * 1.25
        val premiumElevation = (requestedRoute.profile.elevationGainMeters * 1.2).toInt()
        premiumRoute.profile.distanceKm = premiumDistance
        premiumRoute.profile.elevationGainMeters = premiumElevation
        premiumRoute.profile.category = difficultyService.calculateCategory(premiumDistance, premiumElevation)
        return findRoutes(premiumRoute, groupLimit, weather)
    }

    fun createPremiumEasierRoute(routeWish: RouteWish, groupLimit: RouteCategory, weather: WeatherConditions): HikingRoute {
        val premiumDistance = routeWish.distanceKm * 0.8
        val premiumElevation = (routeWish.elevationGainMeters * 0.7).toInt()
        val premiumRoute = HikingRoute(routeWish.name + " – Premium leichter", routeWish.location,
            premiumDistance, premiumElevation,
            difficultyService.calculateCategory(premiumDistance, premiumElevation))
        return findRoutes(premiumRoute, groupLimit, weather)
    }
*/
    fun findRoutes(premiumRoute: HikingRoute, groupLimit: RouteCategory, weather: WeatherConditions): HikingRoute {
        while(isPremiumRouteDisallowed(premiumRoute, groupLimit, weather)) {
            val adjustedDistance = premiumRoute.profile.distanceKm * 0.95
            val adjustedElevation = (premiumRoute.profile.elevationGainMeters * 0.95).toInt()
            premiumRoute.profile.distanceKm = adjustedDistance
            premiumRoute.profile.elevationGainMeters = adjustedElevation
            premiumRoute.profile.category = difficultyService.calculateCategory(adjustedDistance, adjustedElevation)
        }
        return premiumRoute
    }

    fun filterAllowedRoutes(routes: List<HikingRoute>, groupLimit: RouteCategory, weather: WeatherConditions): List<HikingRoute> {
        val relevantRain = weather.rainProbabilityPercent >= RAIN_MAX || weather.precipitationMillimeters >= PRECIPITATION_MAX
        return routes.filter { route ->
            val profile = route.profile
            val allowedForGroup = categoryValue(profile.category) <= categoryValue(groupLimit)
            val allowedForRain = !relevantRain || profile.elevationGainMeters <= 700
            val allowedForHeat = weather.maximumTemperatureCelsius < 30 ||
                    (profile.category != RouteCategory.BLACK && profile.distanceKm <= 27)
            val allowedForWind = weather.maximumWindGustKmh < 60 ||
                    profile.category != RouteCategory.BLACK

            allowedForGroup && allowedForRain && allowedForHeat && allowedForWind
        }
    }

    fun isPremiumRouteDisallowed(route: HikingRoute, groupLimit: RouteCategory, weather: WeatherConditions) = when {
        categoryValue(route.profile.category) > categoryValue(groupLimit) -> true
        (weather.rainProbabilityPercent >= RAIN_MAX || weather.precipitationMillimeters >= PRECIPITATION_MAX)
                && route.profile.elevationGainMeters > 700 -> true
        weather.maximumTemperatureCelsius >= 30
                && (route.profile.category == RouteCategory.BLACK || route.profile.distanceKm > 27) -> true
        weather.maximumWindGustKmh >= 60 && route.profile.category == RouteCategory.BLACK -> true
        else -> false
    }

    fun maximumCategoryForGroup(group: HikingGroup): RouteCategory {
        var groupMax = RouteCategory.BLACK
        for (hiker in group.hikers) {
            when (hiker.experience) {
                ExperienceLevel.BEGINNER -> return RouteCategory.BLUE
                ExperienceLevel.INTERMEDIATE -> groupMax = RouteCategory.RED
                else -> continue
            }
        }
        return groupMax
    }

    fun categoryValue(category: RouteCategory) = when (category) {
        RouteCategory.GREEN -> 1
        RouteCategory.BLUE -> 2
        RouteCategory.RED -> 3
        else -> 4
    }
}