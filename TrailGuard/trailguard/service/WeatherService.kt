package trailguard.service

import trailguard.model.Location
import trailguard.model.WeatherConditions
import trailguard.weather.OpenMeteoClient
import trailguard.weather.WeatherSource
import java.io.IOException
import java.lang.Thread.sleep

class WeatherService (val weatherSource: WeatherSource = OpenMeteoClient) {

    fun getWeather(location: Location): WeatherConditions? {
        var lastTechnicalFailure: IOException

        for (i in 1..3){
            try {
                val response = weatherSource.loadWeather(location)
                return WeatherConditions(response.maximumTemperatureCelsius, response.rainProbabilityPercent, response.precipitationMillimeters, response.maximumWindGustKmh)
            } catch (technicalFailure: IOException){
                lastTechnicalFailure = technicalFailure
                if (i < 3) waitBeforeNextAttempt()
            } catch (e: InterruptedException){
                println("Der Wetterabruf wurde unterbrochen.")
                return null
            }
            println("Wetterabruf fehlgeschlagen: " + lastTechnicalFailure.message)
        }
        return null
    }

    fun waitBeforeNextAttempt() = try {
        sleep(150)
    } catch (e: InterruptedException) {
        println("Das Warten vor dem nächsten Versuch wurde unterbrochen.")
    }

}