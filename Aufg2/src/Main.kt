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

    println("Füge 3 Einträge hinzu")
    println(map.put(student1, note1))
    println(map.put(student2, note2))
    println(map.put(student3, note3))

    println(map.containsKey(student1))

    println(map.containsValue(note2))

    println(map.get(student3))

    println(map.put(student1, note4))

    println("remove student2")
    println(map.remove(student2))

    printMap(map)

    println("putAll student4 to note4")
    map.putAll(mapOf(student4 to note4))

    printMap(map)

    println("Min: ${map.getMin()}")
    println("Max: ${map.getMax()}")

    println("\nKey-View")
    println("removeAll student1")
    println(map.keys.removeAll(listOf(student1)))
    printMap(map)
    println("Füge 3 Einträge hinzu")
    println(map.put(student1, note1))
    println(map.put(student2, note2))
    println(map.put(student3, note3))
    println("retainAll student1, student3")
    println(map.keys.retainAll(listOf(student1, student3)))
    printMap(map)

    println("\nValue-View")
    println("remove note1")
    println(map.values.remove(note1))
    printMap(map)
    println(map.put(student1, note1))
    println(map.put(student2, note2))
    println(map.put(student3, note3))
    println("removeAll note2")
    println(map.values.removeAll(listOf(note2)))
    printMap(map)
    map.put(student2, note2)
    println(map.values.retainAll(listOf(note1, note2)))
    printMap(map)

    println("\nEntry-View")
    val entry1 = object : MutableMap.MutableEntry<Student, Note> {
            override val key = student4
            override var value = note4

            override fun setValue(newValue: Note): Note {
                val old = value
                value = newValue
                return old
            }
        }

    println(map.entries.add(entry1))
    printMap(map)

    val entry2 = object : MutableMap.MutableEntry<Student, Note> {
            override val key = Student(10, "Max")
            override var value = Note("Physik", 1.7)

            override fun setValue(newValue: Note): Note {
                val old = value
                value = newValue
                return old
            }
        }

    println(map.entries.addAll(listOf(entry2)))
    printMap(map)
}

fun printMap(map: ExtendedMap<Student, Note>) {
    for ((student, note) in map) {
        println("$student -> $note")
    }
}