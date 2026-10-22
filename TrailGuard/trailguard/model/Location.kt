package trailguard.model

// Diese Typen beschreiben gemeinsam die Eingabe einer gewünschten Route.
// Nur Location ist vorgegeben, da OpenMeteoClient sie benötigt.
// RouteCategory und RouteWish sind Teil eurer Aufgabe -- ergänzt sie hier
// gemäß domain-rules.md.

data class Location(
    val searchTerm: String,
) {
    init {
        require(searchTerm.isNotBlank()) { "Location must not be blank" }
    }
}

