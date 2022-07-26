package exactnumbers.irrationals.sqrt

import common.getIntFromDecimal
import exactnumbers.irrationals.common.Memoize
import kotlinutils.biginteger.ext.isZero
import java.math.BigDecimal
import java.math.BigInteger
import java.math.MathContext
import java.math.RoundingMode

/**
 * Find component of number that is a perfect square and can be extracted as a coefficient.
 * As an example, the function would return 5 for 50, because 50 = 2 * 5^2.
 * Uses memoization to avoid repeated computation.
 *
 * @param num [BigInteger]: value to extract from
 * @return [BigInteger]: the whole number that was extracted
 */
internal fun extractWholeOf(num: BigInteger): BigInteger {
    val memo = Memoize.individualWholeNumber

    if (num in memo) {
        return memo[num]!!
    }

    if (num.isZero()) {
        memo[num] = BigInteger.ONE
        return BigInteger.ONE
    }

    var extracted = BigInteger.ONE
    var factor = BigInteger.TWO
    var remaining = num

    val orderedRemaining = mutableListOf(remaining)
    val orderedFactors = mutableListOf(BigInteger.ONE)

    while (factor * factor <= remaining && remaining > BigInteger.ONE) {
        if (remaining in memo) {
            val fromMemo = memo[remaining]!!
            extracted *= fromMemo
            remaining = BigInteger.ONE

            orderedFactors.add(fromMemo)
            orderedRemaining.add(remaining)
        } else {
            // divide by current factor as many times as needed
            var extractedCount = 0

            while (remaining % (factor * factor) == BigInteger.ZERO) {
                extracted *= factor
                remaining /= (factor * factor)
                extractedCount++
            }

            if (extractedCount > 0) {
                orderedFactors.add(factor.pow(extractedCount))
                orderedRemaining.add(remaining)
            }

            factor++
        }
    }

    var currentProduct = BigInteger.ONE
    for (idx in orderedFactors.indices.reversed()) {
        memo[orderedRemaining[idx]] = currentProduct
        currentProduct *= orderedFactors[idx]
    }

    return extracted
}

/**
 * Get sqrt value of a whole number
 *
 * @param num [BigInteger]: number to get root of
 * @return [BigDecimal]: the root of the number, using the current base
 */
internal fun getRootOf(num: BigInteger): BigDecimal {
    val mc = MathContext(20, RoundingMode.HALF_UP)
    val whole = extractWholeOf(num)
    val remaining = num / (whole * whole)
    val root = whole.toBigDecimal() * remaining.toBigDecimal().sqrt(mc)
    val int = getIntFromDecimal(root) { it * it == num }

    return int?.toBigDecimal() ?: root
}
