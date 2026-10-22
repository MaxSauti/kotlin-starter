package trailguard.service

import trailguard.model.WeatherConditions
import trailguard.model.WeatherRecommendation

class WeatherRecommendationService {
    fun createRecommendation(weather: WeatherConditions): WeatherRecommendation {
        val recommendation = WeatherRecommendation()
        if (weather.rainProbabilityPercent >= 80 || weather.precipitationMillimeters >= 20) {
            recommendation.addRequiredClothing("Regenjacke")
            recommendation.addWarning("Regen: Routen mit mehr als 700 Höhenmetern entfallen")
        }
        if (weather.maximumTemperatureCelsius < 5) {
            recommendation.addRequiredClothing("Warme Zusatzschicht")
            recommendation.addWarning("Warm Niedrige Temperatur")
        }
        if (weather.maximumTemperatureCelsius >= 30) {
            recommendation.addRequiredClothing("Sonnenschutz")
            recommendation.addRequiredClothing("Mindestens 2 Liter Wasser")
            recommendation.addWarning("Große Hitze: BLACK-Routen und Routen über 27 km entfallen")
        }
        if (weather.maximumWindGustKmh >= 60) {
            recommendation.addRequiredClothing("Winddichte Jacke")
            recommendation.addWarning("Starke Windböen: BLACK-Routen entfallen")
        }
        return recommendation
    }
}