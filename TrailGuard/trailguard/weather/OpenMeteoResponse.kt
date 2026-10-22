package trailguard.weather

/** Technisches DTO des vorgegebenen Open-Meteo-Adapters. */
data class OpenMeteoResponse(
    val maximumTemperatureCelsius: Double,
    val rainProbabilityPercent: Double,
    val precipitationMillimeters: Double,
    val maximumWindGustKmh: Double,
)
