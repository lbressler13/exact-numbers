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
