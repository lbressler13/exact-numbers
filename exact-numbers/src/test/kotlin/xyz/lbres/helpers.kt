
import kotlin.test.assertFailsWith

/**
 * Helper functions to be used in tests
 */

/**
 * Assert that a divide by zero exception is thrown when code is run
 *
 * @param function [() -> Unit]: the code to run
 */
internal fun assertDivByZero(function: () -> Unit) {
    assertFailsWith<ArithmeticException>("divide by zero") { function() }
}
