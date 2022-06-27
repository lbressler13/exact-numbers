import exactfraction.ExactFraction
import exactfraction.ExactFractionOverflowException
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal fun assertDivByZero(function: () -> Unit) {
    assertFailsWith<ArithmeticException>("divide by zero") { function() }
}

internal fun assertExactFractionOverflow(type: String, value: ExactFraction, cast: () -> Unit) {
    val errorMessage = "Overflow when casting to $type"
    val error = assertFailsWith<ExactFractionOverflowException>(errorMessage) { cast() }
    assertEquals(value.toFractionString(), error.overflowValue)
}
