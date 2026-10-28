/**
 * Simuliert das Einlesen eines Bestandes aus einer fremden Quelle.
 *
 * Was genau geliefert wird, hängt von der Quelle ab - der Rückgabetyp sagt nur
 * zu, dass es Tiere sind.
 */
fun readInventory(source: String): List<Animal> {
    return when (source) {
        "cats" -> listOf(Cat("Minka"), Cat("Luna"))
        "dogs" -> listOf(Dog("Bello", Size.LARGE), Dog("Rex", Size.SMALL))
        else -> listOf(
            Cat("Minka"),
            Dog("Bello", Size.LARGE),
            Cat("Luna"),
            Dog("Rex", Size.SMALL)
        )
    }
}

/**
 * Sammelt die Katzen aus einem gemischten Bestand.
 *
 * Die Einzelprüfung mit `is` ist der Weg aus der Vorlesung: Der Elementtyp der
 * Liste ist zur Laufzeit nicht mehr vorhanden, das einzelne Objekt aber schon.
 * Nach `animal is Cat` kennt der Compiler im Zweig den konkreteren Typ (Smart
 * Cast), deshalb ist `result.add(animal)` ohne Cast möglich.
 */
fun filterCats(animals: List<Animal>): List<Cat> {
    val result = mutableListOf<Cat>()
    for (animal in animals) {
        if (animal is Cat) {
            result.add(animal)
        }
    }
    return result
}

/**
 * Impft alle noch nicht geimpften Katzen.
 *
 * Beachtenswert: Der Parameter ist eine `List<Cat>`, also schreibgeschützt -
 * und trotzdem ändert diese Funktion den Impfstatus. Schreibgeschützt ist die
 * Liste, nicht die Katze.
 */
fun vaccinateCats(cats: List<Cat>) {
    for (cat in cats) {
        if (cat.vaccinated) {
            println("${cat.name} ist bereits geimpft.")
        } else {
            cat.vaccinated = true
            println("${cat.name} wurde geimpft.")
        }
    }
}

// ===========================================================================
// TODO 1 · Die hundetypische Behandlung
// ===========================================================================
//
// Schreibt hier eine Funktion, die Hunde entwurmt. Die Größe der Tablette
// richtet sich nach der Größe des Hundes (Size.SMALL, Size.MEDIUM,
// Size.LARGE). Eine Konsolenausgabe je Hund genügt, zum Beispiel:
//
//     Bello erhaelt eine grosse Tablette.
//     Rex erhaelt eine kleine Tablette.
//
// Orientiert euch an vaccinateCats weiter oben. Überlegt euch dabei, welchen
// Parametertyp eure Funktion braucht und warum List<Animal> nicht genügt.
fun dewormDogs(dogs: List<Dog>) {
    for (dog in dogs) {
        val tablet = when (dog.size) { //dog.size nicht möglich wenn List<Animal>
            Size.SMALL -> "kleine"
            Size.MEDIUM -> "mittlere"
            Size.LARGE -> "grosse"
        }
        println("${dog.name} erhaelt eine $tablet Tablette.")
    }
}


// ===========================================================================
// TODO 2 · Ein Filter, der für jede Tierart funktioniert
// ===========================================================================
//
// filterCats oben holt die Katzen aus einem gemischten Bestand. Für TODO 1
// braucht ihr dasselbe für Hunde.
//
// Der naheliegende Weg - filterCats kopieren und Cat durch Dog ersetzen - ist
// als Zwischenschritt in Ordnung, aber nicht die Abgabe. Gesucht ist EINE
// Funktion, die für Cat, für Dog und für jede weitere Tierart funktioniert,
// ohne dass ihr sie ein zweites Mal schreibt.
//
// Sie soll sich so aufrufen lassen:
//
//     val cats: List<Cat> = eureFunktion<Cat>(animals)
//     val dogs: List<Dog> = eureFunktion<Dog>(animals)
//
// Fangt mit der offensichtlichen generischen Fassung an: Nehmt filterCats und
// ersetzt Cat durch einen Typparameter T. Der Compiler wird sie ablehnen.
//
// Lest die Fehlermeldung genau. Sie benennt den Grund, und der Grund stammt
// aus Kapitel 3 der Vorlesung - ihr könnt also erklären, WARUM es nicht geht.
// WIE sich das auflösen lässt, war nicht mehr Teil der Vorlesung. Das ist euer
// Rechercheauftrag: Internet und generative KI sind ausdrücklich erlaubt.
//
// Wenn eure Funktion steht, wird filterCats nicht mehr gebraucht und kann
// ersatzlos verschwinden. Ein eigenes filterDogs darf gar nicht erst
// entstehen.
inline fun <reified T : Animal> filterByType(animals: List<Animal>): List<T> {
    val result = mutableListOf<T>()
    for (animal in animals) {
        if (animal is T) {
            result.add(animal)   // Smart Cast auf T
        }
    }
    return result
}

