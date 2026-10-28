
enum class Size { SMALL, MEDIUM, LARGE }

open class Animal(val name: String)

class Cat(
    name: String,
    var vaccinated: Boolean = false
) : Animal(name)

class Dog(
    name: String,
    val size: Size = Size.MEDIUM
) : Animal(name)
