package exactnumbers.irrationals.sqrt

import common.getIntFromDecimal
import kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

internal fun extractWholeOf(num: BigInteger): BigInteger {
    if (num.isZero()) {
        return BigInteger.ONE
    }

    var extracted = BigInteger.ONE
    var factor = BigInteger.TWO
    var remaining = num

    while (factor * factor <= remaining && remaining > BigInteger.ONE) {
        if (remaining % (factor * factor) == BigInteger.ZERO) {
            extracted *= factor
            remaining /= (factor * factor)
            factor = BigInteger.TWO
        } else {
            factor++
        }
    }

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
