// ktlint-disable filename
package xyz.lbres.expressions.term

private const val replacementPackage = "xyz.lbres.exactnumbers.expressions.term"
private const val deprecationMessage = "Class moved to $replacementPackage in v1.0.0"

// reference to previous location for xyz.lbres.exactnumbers.expressions.term.Term
@Deprecated(deprecationMessage, ReplaceWith("Term", "$replacementPackage.Term"), DeprecationLevel.ERROR)
typealias Term = xyz.lbres.exactnumbers.expressions.term.Term
