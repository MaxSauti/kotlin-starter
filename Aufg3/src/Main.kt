fun main() {
    println("=== Fremden Bestand uebernehmen ===")
    val animals = readInventory("mix")
    println("Uebernommene Tiere: ${animals.size}")
    for (animal in animals) {
        println("  ${animal.name}")
    }

    println()
    println("=== Katzen impfen ===")
    //val cats = filterCats(animals)
    val cats = filterByType<Cat>(animals)
    println("Davon Katzen: ${cats.size}")
    vaccinateCats(cats)

    println()
    println("=== Zweiter Durchlauf mit demselben Bestand ===")
    // Der Filter liefert eine neue, schreibgeschuetzte Liste - aber dieselben
    // Katzenobjekte. Deshalb ist der Impfstatus noch da.
    vaccinateCats(filterByType<Cat>(animals))

    println()
    println("=== Hunde entwurmen ===")


     val dogs = filterByType<Dog>(animals)
     println("Davon Hunde: ${dogs.size}")
     dewormDogs(dogs)
}
