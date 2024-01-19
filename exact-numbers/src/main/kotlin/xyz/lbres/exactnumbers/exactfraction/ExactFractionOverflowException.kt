package xyz.lbres.exactnumbers.exactfraction

/**
 * [ArithmeticException] for ExactFraction overflow.
 * Has field for value of string that caused overflow.
 */
class ExactFractionOverflowException() : ArithmeticException() {
    override var message: String? = null
    var overflowValue: String? = null

    constructor (message: String) : this() {
        this.message = message
    }

    constructor (message: String, overflowValue: String) : this(message) {
        this.overflowValue = overflowValue
    }
}
