package xyz.lbres.common

/**
 * ArithmeticException for overflows when casting numbers
 */
class CastingOverflowException() : ArithmeticException() {
    override var message: String? = null
    var overflowValue: Any? = null

    /**
     * @param baseType [String]: name of type being cast from
     * @param targetType [String]: name of type being cast to
     * @param valueString [String]: string to display for value that caused overflow
     * @param overflowValue [Any]?: number that caused overflow. Optional, defaults to `null`
     */
    constructor (baseType: String, targetType: String, valueString: String, overflowValue: Any? = null) : this() {
        this.message = "Overflow casting value $valueString of type $baseType to $targetType"
        this.overflowValue = overflowValue
    }
}
