package trailguard.model

data class Hiker (val name: String, val experience: ExperienceLevel?) {
    init {
        require(name.isNotEmpty()) { "Name must not be empty" }
        requireNotNull(experience) { "Experience must not be null" }
    }
}