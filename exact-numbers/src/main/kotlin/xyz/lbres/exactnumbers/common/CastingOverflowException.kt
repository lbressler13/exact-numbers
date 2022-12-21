package xyz.lbres.exactnumbers.common

/**
 * ArithmeticException specifically for casting overflow.
 * Has specific field for value of string that caused overflow
 */
class CastingOverflowException() : ArithmeticException() {
    override var message: String? = null
    var overflowValue: Number? = null

    constructor (baseType: String, targetType: String, valueString: String, value: Number?) : this() {
        this.message = "Overflow casting value $valueString of type $baseType to $targetType"
        overflowValue = value
    }
}
