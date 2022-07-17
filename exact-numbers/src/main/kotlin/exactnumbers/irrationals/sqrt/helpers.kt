package exactnumbers.irrationals.sqrt

import common.getIntFromDecimal
import exactnumbers.irrationals.common.Memoization
import kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

internal fun extractWholeOf(num: BigInteger): BigInteger {
    val memoization = Memoization.individualWholeNumber

    if (num in memoization) {
        return memoization[num]!!
    }

    if (num.isZero()) {
        memoization[num] = BigInteger.ONE
        return BigInteger.ONE
    }

    var extracted = BigInteger.ONE
    var factor = BigInteger.TWO
    var remaining = num

    while (factor * factor <= remaining && remaining > BigInteger.ONE) {
        while (remaining % (factor * factor) == BigInteger.ZERO) {
            extracted *= factor
            remaining /= (factor * factor)
        }

        factor++
    }

    memoization[num] = extracted
    return extracted
}

internal fun getRootOf(num: BigInteger): BigDecimal {
    val mc = MathContext(20, RoundingMode.HALF_UP)
    val whole = extractWholeOf(num)
    val remaining = num / (whole * whole)
    val root = whole.toBigDecimal() * remaining.toBigDecimal().sqrt(mc)
    val int = getIntFromDecimal(root) { it * it == num }

    return int?.toBigDecimal() ?: root
}
