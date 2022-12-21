package xyz.lbres.exactnumbers.irrationals.pi

import kotlin.test.assertEquals

internal fun runToByteTests() {
    assertEquals(3, Pi().toByte())
    assertEquals(0, Pi().inverse().toByte())
}

internal fun runToCharTests() {
    assertEquals(3.toChar(), Pi().toChar())
    assertEquals(0.toChar(), Pi().inverse().toChar())
}

internal fun runToShortTests() {
    assertEquals(3, Pi().toShort())
    assertEquals(0, Pi().inverse().toShort())
}

internal fun runToIntTests() {
    assertEquals(3, Pi().toInt())
    assertEquals(0, Pi().inverse().toInt())
}

internal fun runToLongTests() {
    assertEquals(3, Pi().toLong())
    assertEquals(0, Pi().inverse().toLong())
}

internal fun runToFloatTests() {
    val pi = 3.1415927f
    assertEquals(pi, Pi().toFloat())

    val inverse = 0.31830987f
    assertEquals(inverse, Pi().inverse().toFloat())
}

internal fun runToDoubleTests() {
    val pi = 3.141592653589793
    assertEquals(pi, Pi().toDouble())

    val inverse = 0.3183098861837907
    assertEquals(inverse, Pi().inverse().toDouble())
}
