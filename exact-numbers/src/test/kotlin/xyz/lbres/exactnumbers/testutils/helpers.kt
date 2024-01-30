package xyz.lbres.exactnumbers.testutils

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.exactfraction.ExactFractionOverflowException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * Assert that a divide by zero exception is thrown when code is run
 *
 * @param test [() -> Unit]: the code to run
 */
fun assertDivByZero(test: () -> Unit) {
    assertFailsWith<ArithmeticException>("divide by zero") { test() }
}

/**
 * Assert that a correct ExactFractionOverflowException is thrown when an ExactFraction is cast
 *
 * @param type [String]: the type of the value being cast to
 * @param value [ExactFraction]: the value to cast
 * @param cast () -> Unit: the call to cast the value
 */
fun assertExactFractionOverflow(type: String, value: ExactFraction, cast: () -> Unit) {
    val errorMessage = "Overflow when casting to $type"
    val error = assertFailsWith<ExactFractionOverflowException>(errorMessage) { cast() }
    assertEquals(value.toFractionString(), error.overflowValue)
}

/**
 * Assert that a test throws an exception of the correct type, with the correct message
 *
 * @param message [String]: expected error message
 * @param test () -> Unit: test to run
 * @return T: the exception that was thrown
 */
inline fun <reified T : Exception> assertFailsWithMessage(message: String, test: () -> Unit): T {
    val error = assertFailsWith<T> { test() }
    assertEquals(message, error.message)
    return error
}

/**
 * Validate that an operation succeeds, and throw an AssertionError if it fails
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
