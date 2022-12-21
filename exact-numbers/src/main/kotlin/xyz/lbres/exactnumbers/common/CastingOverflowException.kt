package xyz.lbres.exactnumbers.common

class CastingOverflowException() : ArithmeticException() {
    override var message: String? = null

    constructor (baseType: String, value: String, targetType: String) : this() {
        this.message = "Error casting value $value of type $baseType to $targetType"
    }
}
