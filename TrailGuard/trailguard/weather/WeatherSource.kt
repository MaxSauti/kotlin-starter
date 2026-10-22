package trailguard.weather

import java.io.IOException
import trailguard.model.Location

interface WeatherSource {
    @Throws(IOException::class, InterruptedException::class)
    fun loadWeather(location: Location): OpenMeteoResponse
}
