package xyz.lbres.exactnumbers.exceptions

/**
 * ArithmeticException for overflows when casting numbers
 */
class CastingOverflowException private constructor(
    baseType: String,
    targetType: String,
    valueString: String,
    val overflowValue: Any?,
    noArgsConstructor: Boolean
) : ArithmeticException() {
    override val message: String?

    init {
        this.message = if (noArgsConstructor) {
            null
        } else {
            "Overflow casting value $valueString of type $baseType to $targetType"
        }
    }

    constructor() : this("", "", "", null, noArgsConstructor = true)

    /**
     * @param baseType [String]: name of type being cast from
     * @param targetType [String]: name of type being cast to
     * @param valueString [String]: string to display for value that caused overflow
     * @param overflowValue [Any]?: number that caused overflow. Optional, defaults to `null`
     */
    constructor (baseType: String, targetType: String, valueString: String, overflowValue: Any? = null) :
        this(baseType, targetType, valueString, overflowValue, noArgsConstructor = false)
}
