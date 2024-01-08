package xyz.lbres.exactnumbers.testutils

import xyz.lbres.exactnumbers.common.CastingOverflowException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Assert that a divide by zero exception is thrown when code is run
 *
 * @param test () -> Unit: test to run
 */
fun assertDivByZero(test: () -> Unit) {
    assertFailsWithMessage<ArithmeticException>("divide by zero") { test() }
}

/**
 * Assert that a test throws an Exception with the correct message
 *
 * @param message [String]: expected error message
 * @param test () -> Unit: test to run
 * @return [Exception] the exception that was thrown
 */
inline fun <reified T : Exception> assertFailsWithMessage(message: String, test: () -> Unit): T {
    val error = assertFailsWith<T> { test() }
    assertEquals(message, error.message)
    return error
}

/**
 * Validate that a test succeeds, and throw error if it fails
 *
 * @param errorMessage [String]: error message to throw if test fails
 * @param test () -> Unit: test to run
 */
fun <T> assertSucceeds(errorMessage: String, test: () -> T) {
    try {
        test()
    } catch (_: Exception) {
        throw AssertionError(errorMessage)
    }
}

/**
 * Generate a function to assert that a CastingOverthrowException is thrown, with the correct message and overflow value
 *
 * @param baseType [String]: name of type being cast from
 * @return function validate error being thrown
 */
fun <T> getCastingOverflowAssertion(baseType: String): (String, T, () -> Unit) -> Unit {
    val assertionFn: (String, T, () -> Unit) -> Unit = { targetType, value, cast ->
        val errorMessage = "Overflow casting value $value of type $baseType to $targetType"
        val error = assertFailsWithMessage<CastingOverflowException>(errorMessage) { cast() }
        assertEquals(value, error.overflowValue)
    }
    return assertionFn
}
