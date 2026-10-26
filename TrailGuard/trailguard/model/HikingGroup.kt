package trailguard.model

data class HikingGroup (val name: String, val hikers: List<Hiker>, val premiumCustomer : Boolean = false) {
    init {
        require(name.isNotBlank()) { "Name must not be empty" }
        require(hikers.isNotEmpty()) { "Hiker must not be empty" }
    }
}