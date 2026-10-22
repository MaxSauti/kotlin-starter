package trailguard.model

class WeatherRecommendation {
    val requiredClothing = ArrayList<String>()
    val warnings = ArrayList<String>()

    fun addRequiredClothing(clothing: String) {
        requiredClothing.add(clothing)
    }

    fun addWarning(warning: String) {
        warnings.add(warning)
    }
}