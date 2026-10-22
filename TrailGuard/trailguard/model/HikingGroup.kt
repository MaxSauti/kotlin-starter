package trailguard.model

data class HikingGroup (val name: String, val hikers: List<Hiker>, val premiumCustomer : Boolean = false) {
}