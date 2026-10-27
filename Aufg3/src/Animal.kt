abstract class Animal
class Cat : Animal()
class Dog(val weightKg: Int) : Animal()

fun readInventory(name: String): List<Animal> =
    listOf(Dog(5), Cat(), Dog(22), Dog(40), Cat())

// Vorlage: nur für Katzen
fun filterCats(input: List<Animal>): List<Cat> {
    val out = mutableListOf<Cat>()
    for (a in input) {
        if (a is Cat) out.add(a)   // Smart Cast
    }
    return out
}

// (b) verallgemeinert auf eine Zielklasse, Ausgabe vom Typ List<T>
inline fun <reified T : Animal> filterByClass(input: List<Animal>): List<T> {
    val out = mutableListOf<T>()
    for (a in input) {
        if (a is T) out.add(a)     // erlaubt dank reified
    }
    return out
}

// (a) bekommt ausschließlich Hunde (Typ List<Dog>)
fun deworm(dogs: List<Dog>) {
    for (d in dogs) {
        val tablet = when {
            d.weightKg < 10 -> "1/2 Tablette"
            d.weightKg < 25 -> "1 Tablette"
            else            -> "2 Tabletten"
        }
        println("Hund (${d.weightKg} kg) entwurmt: $tablet")
    }
}

fun main() {
    val mix = readInventory("mix")
    val cats = filterByClass<Cat>(mix)   // zum Impfen
    val dogs = filterByClass<Dog>(mix)   // zum Entwurmen
    deworm(dogs)
    println("${cats.size} Katzen, ${dogs.size} Hunde")
}