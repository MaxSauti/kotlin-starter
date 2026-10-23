package trailguard.service

import trailguard.model.Location
import trailguard.model.RouteCategory
import trailguard.weather.OpenMeteoClient
import trailguard.weather.WeatherSource
import java.io.IOException

class WeatherService (val weatherSource: WeatherSource = OpenMeteoClient) {

    fun getWeather(location: Location) {
        var lastTechnicalFailure: IOException

        for (i in 1..3){

        }
    }

}