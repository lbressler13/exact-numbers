package ext

import exactfraction.ExactFraction
import java.math.BigInteger

// Additional ext methods related to ExactFraction and BigInteger

// casting to ExactFraction
internal fun Int.toExactFraction(): ExactFraction = ExactFraction(this)
internal fun Long.toExactFraction(): ExactFraction = ExactFraction(this)
internal fun BigInteger.toExactFraction(): ExactFraction = ExactFraction(this)

// equality checks for BigInteger
internal fun BigInteger.eq(other: Int): Boolean = equals(other.toBigInteger())
internal fun BigInteger.eq(other: Long): Boolean = equals(other.toBigInteger())

// unary checks for BigInteger
internal fun BigInteger.isNegative(): Boolean = this < BigInteger.ZERO
internal fun BigInteger.isZero(): Boolean = equals(BigInteger.ZERO)

// comparisons for BigInteger
internal fun min(val1: BigInteger, val2: BigInteger): BigInteger = if (val1 < val2) val1 else val2
internal fun max(val1: BigInteger, val2: BigInteger): BigInteger = if (val2 < val1) val1 else val2
