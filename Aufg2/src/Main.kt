import data.Note
import data.Student
import map.ExtendedMap

fun main() {

    val map = ExtendedMap<Student, Note>()

    val student1 = Student(3, "Bob")
    val student2 = Student(5, "Fritz")
    val student3 = Student(1, "Eva")
    val student4 = Student(4, "Clara")

    val note1 = Note("Mathe I", 3.3)
    val note2 = Note("TI", 2.0)
    val note3 = Note("BWL", 1.0)
    val note4 = Note("EidI", 5.0)

    println("=== put() ===")

    testeMethode(
        "put(student1, note1)",
        null,
        map.put(student1, note1)
    )

    testeMethode(
        "put(student2, note2)",
        null,
        map.put(student2, note2)
    )

    testeMethode(
        "put(student3, note3)",
        null,
        map.put(student3, note3)
    )

    printMap(map)

    println("\n=== containsKey() ===")

    testeMethode(
        "containsKey(student1)",
        true,
        map.containsKey(student1)
    )

    println("\n=== containsValue() ===")

    testeMethode(
        "containsValue(note2)",
        true,
        map.containsValue(note2)
    )

    println("\n=== get() ===")

    testeMethode(
        "get(student3)",
        note3,
        map.get(student3)
    )

    println("\n=== put() vorhandenen Key ersetzen ===")

    testeMethode(
        "put(student1, note4)",
        note1,
        map.put(student1, note4)
    )

    printMap(map)

    println("\n=== remove() ===")

    testeMethode(
        "remove(student2)",
        note2,
        map.remove(student2)
    )

    printMap(map)

    println("\n=== putAll() ===")

    map.putAll(mapOf(student4 to note4))

    printMap(map)

    println("\n=== getMin() ===")

    testeMethode(
        "getMin()",
        note3,
        map.getMin()
    )

    println("\n=== getMax() ===")

    testeMethode(
        "getMax()",
        note4,
        map.getMax()
    )

    println("\n=== KeyView.removeAll() ===")

    testeMethode(
        "keys.removeAll(student1)",
        true,
        map.keys.removeAll(listOf(student1))
    )

    printMap(map)

    map.put(student1, note1)

    println("\n=== KeyView.retainAll() ===")

    testeMethode(
        "keys.retainAll(student1, student3)",
        true,
        map.keys.retainAll(listOf(student1, student3))
    )

    printMap(map)

    map.put(student2, note2)
    map.put(student4, note4)

    println("\n=== ValueView.remove() ===")

    testeMethode(
        "values.remove(note1)",
        true,
        map.values.remove(note1)
    )

    printMap(map)

    map.put(student1, note1)

    println("\n=== ValueView.removeAll() ===")

    testeMethode(
        "values.removeAll(note2)",
        true,
        map.values.removeAll(listOf(note2))
    )

    printMap(map)

    map.put(student2, note2)

    println("\n=== ValueView.retainAll() ===")

    testeMethode(
        "values.retainAll(note1, note2)",
        true,
        map.values.retainAll(listOf(note1, note2))
    )

    printMap(map)

    println("\n=== EntryView.add() ===")

    val entry1 = object : MutableMap.MutableEntry<Student, Note> {
        override val key = student4
        override var value = note4

        override fun setValue(newValue: Note): Note {
            val old = value
            value = newValue
            return old
        }
    }

    testeMethode(
        "entries.add(entry1)",
        true,
        map.entries.add(entry1)
    )

    printMap(map)

    println("\n=== EntryView.addAll() ===")

    val entry2 = object : MutableMap.MutableEntry<Student, Note> {
        override val key = Student(10, "Max")
        override var value = Note("Physik", 1.7)

        override fun setValue(newValue: Note): Note {
            val old = value
            value = newValue
            return old
        }
    }

    testeMethode(
        "entries.addAll(entry2)",
        true,
        map.entries.addAll(listOf(entry2))
    )

    printMap(map)
}

fun testeMethode(
    beschreibung: String,
    erwartet: Any?,
    erhalten: Any?
) {
    if (erwartet == erhalten) {
        println("✓ $beschreibung")
    } else {
        println("✗ $beschreibung")
        println("  Erwartet: $erwartet")
        println("  Erhalten: $erhalten")
    }
}

fun printMap(map: ExtendedMap<Student, Note>) {
    println("Aktuelle Map:")

    for ((student, note) in map) {
        println("$student -> $note")
    }
}