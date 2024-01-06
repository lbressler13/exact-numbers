package xyz.lbres.exactnumbers.common

// TODO use for all casting

/**
 * ArithmeticException specifically for overflows when casting numbers
 */
class CastingOverflowException() : ArithmeticException() {
    override var message: String? = null
    var overflowValue: Number? = null

    /**
     * @param baseType [String]: name of type being cast from
     * @param targetType [String]: name of type being cast to
     * @param valueString [String]: string to display for value that caused overflow
     * @param value [Number]?: number that caused overflow. Optional, defaults to `null`
     */
    constructor (baseType: String, targetType: String, valueString: String, value: Number? = null) : this() {
        this.message = "Overflow casting value $valueString of type $baseType to $targetType"
        overflowValue = value
    }
}
