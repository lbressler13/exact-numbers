package xyz.lbres.exactnumbers.utils

import xyz.lbres.kotlinutils.general.tryOrDefault
import xyz.lbres.kotlinutils.generic.ext.ifNull
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
 * Update a variable if `null`, and return current value
 *
 * @param getValue () -> T?: function to get value of variable
 * @param setValue (T) -> Unit: function to update value of variable
 * @param generateValue () -> T: function to generate a new value if [getValue] returns `null`
 * @return T: the result of [getValue] or [generateValue]
 */
internal fun <T> getOrSet(getValue: () -> T?, setValue: (T) -> Unit, generateValue: () -> T): T {
    return getValue().ifNull {
        setValue(generateValue())
        getValue()!!
    }
}

/**
 * Generate hash code from a list of values
 *
 * @param values [List]<*>: values to use in hash code
 * @return [Int]: hash code
 */
internal fun createHashCode(values: List<*>): Int = values.fold(0) { acc, value -> 31 * acc + value.hashCode() }
