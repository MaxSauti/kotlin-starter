package trailguard.app

import trailguard.model.ExperienceLevel
import trailguard.model.Hiker
import trailguard.model.HikingGroup
import trailguard.model.Location
import trailguard.weather.OpenMeteoClient

fun main() {
    // Zeigt, dass die vorgegebene technische Wetteranbindung funktioniert.
    val weather = OpenMeteoClient.loadWeather(Location("Steinfurt, Germany"))
    val hiker = Hiker("Hans", ExperienceLevel.EXPERIENCED)
    println(hiker.name + " " + hiker.experience)

    val list: MutableList<Hiker> = mutableListOf()
    list.add(hiker)
    val group = HikingGroup("Hikers", list, true)
    println(group.name + " " + group.hikers + " " + group.premiumCustomer)


    // ================================================================
    // Sobald Hiker, HikingGroup, ExperienceLevel, RouteWish, RouteCategory,
    // PlanningService und HikeReportPrinter existieren: den Block unten
    // auskommentieren (inkl. der zugehörigen Importe) und die Szenarien
    // hier aufrufen.
    // ================================================================
    // erbilStartScenario()
    // bergenStartScenario()
    // sanJuanStartScenario()
    // funchalStartScenario()
    // bug01PremiumRoutesScenario()
    // bug02WeatherFailureScenario()
    // bug03MixedGroupScenario()
    // change01ChangedRainRuleScenario()
}

// ====================================================================
// Vorbereitete Szenarien aus assignment.md. Die Eingabedaten entsprechen
// exakt den dort beschriebenen Start- und Ticket-Szenarien. Sobald eure
// Modelle und Services stehen, entfernt die Kommentarzeichen und ergänzt
// oben:
//
// import trailguard.model.ExperienceLevel
// import trailguard.model.Hiker
// import trailguard.model.HikingGroup
// import trailguard.model.RouteWish
// import trailguard.report.HikeReportPrinter
// import trailguard.report.PlanningService
// ====================================================================

/*
private fun erbilStartScenario() {
    printScenario("Startbeispiel 1: Irak-Route bei möglicherweise großer Hitze")
    val group = HikingGroup(
        "Wandergruppe Irak",
        listOf(
            Hiker("Ava", ExperienceLevel.EXPERT),
            Hiker("Ben", ExperienceLevel.EXPERIENCED),
        ),
        premiumCustomer = false,
    )
    val routeWish = RouteWish(
        "Irak-Route",
        Location("Erbil, Iraq"),
        18.0,
        400,
    )

    computeAndPrint(group, routeWish)
}

private fun bergenStartScenario() {
    printScenario("Startbeispiel 2: Norwegen-Route bei möglicherweise viel Regen")
    val group = HikingGroup(
        "Wandergruppe Norwegen",
        listOf(
            Hiker("Clara", ExperienceLevel.EXPERT),
            Hiker("David", ExperienceLevel.EXPERIENCED),
        ),
        premiumCustomer = false,
    )
    val routeWish = RouteWish(
        "Norwegen-Route",
        Location("Bergen, Norway"),
        16.0,
        500,
    )

    computeAndPrint(group, routeWish)
}

private fun sanJuanStartScenario() {
    printScenario("Startbeispiel 3: Puerto-Rico-Route bei möglichen Windböen")
    val group = HikingGroup(
        "Wandergruppe Puerto Rico",
        listOf(
            Hiker("Elif", ExperienceLevel.EXPERT),
            Hiker("Finn", ExperienceLevel.EXPERIENCED),
        ),
        premiumCustomer = false,
    )
    val routeWish = RouteWish(
        "Puerto-Rico-Route",
        Location("San Juan, Puerto Rico"),
        13.0,
        200,
    )

    computeAndPrint(group, routeWish)
}

private fun funchalStartScenario() {
    printScenario("Startbeispiel 4: Portugal-Route bei möglicherweise mildem Wetter")
    val group = HikingGroup(
        "Wandergruppe Portugal",
        listOf(
            Hiker("Greta", ExperienceLevel.EXPERT),
            Hiker("Hasan", ExperienceLevel.EXPERIENCED),
        ),
        premiumCustomer = false,
    )
    val routeWish = RouteWish(
        "Portugal-Route",
        Location("Funchal, Portugal"),
        20.0,
        1_000,
    )

    computeAndPrint(group, routeWish)
}

private fun bug01PremiumRoutesScenario() {
    printScenario("BUG-01: Premium-Routen verdrängen die Wunschroute")
    val group = HikingGroup(
        "Premiumgruppe Steinfurt",
        listOf(
            Hiker("Ida", ExperienceLevel.EXPERT),
            Hiker("Jona", ExperienceLevel.EXPERIENCED),
        ),
        premiumCustomer = true,
    )
    val routeWish = RouteWish(
        "Steinfurt-Runde",
        Location("Steinfurt, Germany"),
        13.0,
        250,
    )

    computeAndPrint(group, routeWish)
}

private fun bug02WeatherFailureScenario() {
    printScenario("BUG-02: Wetterfehler hinterlässt ein Teilergebnis")

    println("Kontrollfall - bekannter Ort:")
    val knownGroup = HikingGroup(
        "Team mit Wetter",
        listOf(
            Hiker("Kira", ExperienceLevel.INTERMEDIATE),
            Hiker("Luis", ExperienceLevel.EXPERIENCED),
        ),
        premiumCustomer = false,
    )
    val knownRouteWish = RouteWish(
        "Steinfurt-Spaziergang",
        Location("Steinfurt, Germany"),
        8.0,
        100,
    )

    computeAndPrint(knownGroup, knownRouteWish)

    println("Fehlerfall - unbekannter Ort:")
    val unknownGroup = HikingGroup(
        "Team Unbekannt",
        listOf(
            Hiker("Mina", ExperienceLevel.INTERMEDIATE),
            Hiker("Oskar", ExperienceLevel.EXPERIENCED),
        ),
        premiumCustomer = false,
    )
    val unknownRouteWish = RouteWish(
        "Unbekannte Route",
        Location("NON_EXISTING_LOCATION"),
        8.0,
        100,
    )
    computeAndPrint(unknownGroup, unknownRouteWish)
}

private fun bug03MixedGroupScenario() {
    printScenario("BUG-03: Reihenfolge der Gruppenmitglieder verändert Gültigkeit")
    val difficultWish = RouteWish(
        "Sehr lange Steinfurt-Tour",
        Location("Steinfurt, Germany"),
        28.0,
        1_200,
    )

    println("Reihenfolge 1 - Anfänger zuerst:")
    val firstOrder = HikingGroup(
        "Anfänger zuerst",
        listOf(
            Hiker("Noah", ExperienceLevel.BEGINNER),
            Hiker("Eva", ExperienceLevel.EXPERT),
        ),
        premiumCustomer = false,
    )
    computeAndPrint(firstOrder, difficultWish)

    println("Reihenfolge 2 - dieselben Personen in umgekehrter Reihenfolge:")
    val secondOrder = HikingGroup(
        "Expertin zuerst",
        listOf(
            Hiker("Eva", ExperienceLevel.EXPERT),
            Hiker("Noah", ExperienceLevel.BEGINNER),
        ),
        premiumCustomer = false,
    )
    computeAndPrint(secondOrder, difficultWish)
}

private fun change01ChangedRainRuleScenario() {
    printScenario("CHANGE-01: Neue Regenregel konsistent umsetzen")
    val group = HikingGroup(
        "Premiumgruppe Regeländerung",
        listOf(
            Hiker("Pia", ExperienceLevel.EXPERT),
            Hiker("Ravi", ExperienceLevel.EXPERIENCED),
        ),
        premiumCustomer = true,
    )
    val routeWish = RouteWish(
        "Steinfurt-Bergtour",
        Location("Steinfurt, Germany"),
        18.0,
        800,
    )

    println("Gesucht ist bei Bedarf zusätzlich ein Ort mit 60 bis 79 Prozent Regenwahrscheinlichkeit.")
    computeAndPrint(group, routeWish)
}

private fun computeAndPrint(group: HikingGroup, routeWish: RouteWish) {
    try {
        HikeReportPrinter.print(PlanningService().planRoute(group, routeWish))
    } catch (e: RuntimeException) {
        println("Route kann nicht berechnet werden: ${e.message}")
    }
}
*/

private fun printScenario(title: String) {
    println("============================================================")
    println(title)
    println("============================================================")
}
