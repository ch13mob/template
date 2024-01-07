package com.sample.features.animatedcounter

data class Digit(
    val digitChar: Char,
    val fullNumber: Int,
    val place: Int
) {
    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Digit -> digitChar == other.digitChar
            else -> super.equals(other)
        }
    }
}

operator fun Digit.compareTo(other: Digit): Int {
    return fullNumber.compareTo(other.fullNumber)
}
