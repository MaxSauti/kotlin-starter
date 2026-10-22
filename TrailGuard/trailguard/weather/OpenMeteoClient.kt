package trailguard.weather

import java.io.IOException
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets
import java.time.Duration
import trailguard.model.Location

/**
 * Vorgegebener technischer Adapter. Anwendungscode verwendet nur [loadWeather].
 * Die Methode löst Ortsname oder Postleitzahl auf und lädt danach das Wetter.
 *
 * @throws IOException bei unbekannten Orten, Netzwerk-, HTTP- oder Datenfehlern
 * @throws InterruptedException wenn der laufende Request unterbrochen wird
 */
object OpenMeteoClient : WeatherSource {
    private val httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(3))
        .build()

    @Throws(IOException::class, InterruptedException::class)
    override fun loadWeather(location: Location): OpenMeteoResponse {
        val coordinates = resolveCoordinates(location)
        val forecastUrl = "https://api.open-meteo.com/v1/forecast" +
            "?latitude=${coordinates.latitude}" +
            "&longitude=${coordinates.longitude}" +
            "&daily=temperature_2m_max,precipitation_probability_max," +
            "precipitation_sum,wind_gusts_10m_max" +
            "&timezone=auto&forecast_days=1"

        return try {
            parse(sendGetRequest(forecastUrl))
        } catch (invalidResponse: IllegalStateException) {
            throw IOException("Open-Meteo returned unreadable weather data", invalidResponse)
        }
    }

    private fun resolveCoordinates(location: Location): Coordinates {
        val encodedSearchTerm = URLEncoder.encode(
            location.searchTerm,
            StandardCharsets.UTF_8,
        )
        val geocodingUrl = "https://geocoding-api.open-meteo.com/v1/search" +
            "?name=$encodedSearchTerm&count=1&language=de&format=json"
        val responseBody = sendGetRequest(geocodingUrl)

        return try {
            Coordinates(
                singleNumber(responseBody, "latitude"),
                singleNumber(responseBody, "longitude"),
            )
        } catch (unknownLocation: IllegalStateException) {
            throw IOException(
                "Open-Meteo kennt den Ort nicht: ${location.searchTerm}",
                unknownLocation,
            )
        }
    }

    private fun sendGetRequest(url: String): String {
        val request = HttpRequest.newBuilder(URI.create(url))
            .timeout(Duration.ofSeconds(3))
            .GET()
            .build()
        val response = httpClient.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() != 200) {
            throw IOException("Open-Meteo returned HTTP ${response.statusCode()}")
        }
        return response.body()
    }

    private fun arrayNumber(responseBody: String, fieldName: String): Double =
        Regex("\\\"$fieldName\\\"\\s*:\\s*\\[\\s*(-?[0-9]+(?:\\.[0-9]+)?)")
            .find(responseBody)
            ?.groupValues
            ?.get(1)
            ?.toDouble()
            ?: error("Missing field $fieldName")

    private fun singleNumber(responseBody: String, fieldName: String): Double =
        Regex("\\\"$fieldName\\\"\\s*:\\s*(-?[0-9]+(?:\\.[0-9]+)?)")
            .find(responseBody)
            ?.groupValues
            ?.get(1)
            ?.toDouble()
            ?: error("Missing field $fieldName")

    internal fun parse(responseBody: String) = OpenMeteoResponse(
        arrayNumber(responseBody, "temperature_2m_max"),
        arrayNumber(responseBody, "precipitation_probability_max"),
        arrayNumber(responseBody, "precipitation_sum"),
        arrayNumber(responseBody, "wind_gusts_10m_max"),
    )

    private data class Coordinates(
        val latitude: Double,
        val longitude: Double,
    )
}
