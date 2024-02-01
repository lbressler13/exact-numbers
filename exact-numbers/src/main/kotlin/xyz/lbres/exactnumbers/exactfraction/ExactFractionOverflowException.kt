package xyz.lbres.exactnumbers.exactfraction

/**
 * [ArithmeticException] for ExactFraction overflow.
 * Has field for string representation of value that caused overflow
 */
class ExactFractionOverflowException private constructor(
    message: String?,
    overflowValue: String?,
    noArgsConstructor: Boolean
) : ArithmeticException() {
    override val message: String?
    val overflowValue: String?

    init {
        this.message = message
        this.overflowValue = overflowValue
    }

    constructor() : this(null, null, noArgsConstructor = true)

    constructor(message: String) : this(message, null, noArgsConstructor = false)

    constructor(message: String, overflowValue: String) : this(message, overflowValue, noArgsConstructor = false)
}
