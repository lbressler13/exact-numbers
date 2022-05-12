package ext

import exactfraction.ExactFraction
import java.math.BigInteger

// Additional ext methods related to ExactFraction and BigInteger

// casting to ExactFraction
fun Int.toExactFraction(): ExactFraction = ExactFraction(this)
fun Long.toExactFraction(): ExactFraction = ExactFraction(this)
fun BigInteger.toExactFraction(): ExactFraction = ExactFraction(this)

// equality checks for BigInteger
fun BigInteger.eq(other: Int): Boolean = equals(other.toBigInteger())
fun BigInteger.eq(other: Long): Boolean = equals(other.toBigInteger())

// unary checks for BigInteger
fun BigInteger.isNegative(): Boolean = this < BigInteger.ZERO
fun BigInteger.isZero(): Boolean = equals(BigInteger.ZERO)

// comparisons for BigInteger
fun min(val1: BigInteger, val2: BigInteger): BigInteger = if (val1 < val2) val1 else val2
fun max(val1: BigInteger, val2: BigInteger): BigInteger = if (val2 < val1) val1 else val2
