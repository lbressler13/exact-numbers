import kotlin.test.assertFailsWith

fun assertDivByZero(function: Runnable) {
    assertFailsWith<ArithmeticException>("divide by zero") { function.run() }
}
