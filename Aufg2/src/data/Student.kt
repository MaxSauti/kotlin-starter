package data

data class Student(
    val matrikelNr: Int,
    val name: String
) : Comparable<Student> {

    override fun compareTo(other: Student): Int {
        return matrikelNr.compareTo(other.matrikelNr)
    }

}