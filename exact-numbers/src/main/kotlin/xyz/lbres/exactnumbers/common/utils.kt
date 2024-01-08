package xyz.lbres.exactnumbers.common

import xyz.lbres.kotlinutils.general.tryOrDefault
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode

/**
 * Round decimal to nearest ints, and determine if either value passes a check.
 * If so, returns the passing value. If both pass, the closer value will be returned.
 *
 * @param decimal [BigDecimal]: the number to round
 * @param checkInt ([BigInteger]) -> Boolean: function to check rounded value
 * @return [BigInteger]?: a rounded number that passes the checks, or null if there is none
 */
internal fun getIntFromDecimal(decimal: BigDecimal, checkInt: (BigInteger) -> Boolean): BigInteger? {
    return tryOrDefault(null) {
        val upInt = decimal.setScale(0, RoundingMode.UP).toBigInteger()
        val downInt = decimal.setScale(0, RoundingMode.DOWN).toBigInteger()

        val upPasses = checkInt(upInt)
        val downPasses = checkInt(downInt)

        when {
            upPasses && downPasses -> decimal.setScale(0, RoundingMode.HALF_UP).toBigInteger()
            upPasses -> upInt
            downPasses -> downInt
            else -> null
        }
    }
}

/**
 * Generate hash code from a list of values
 *
 * @param values [List]<*>: values to use in hash code
 * @return [Int]: hash code
 */
internal fun createHashCode(values: List<*>): Int {
    var result = 0
    for (value in values) {
        result = 31 * result + value.hashCode()
    }
    return result
}
