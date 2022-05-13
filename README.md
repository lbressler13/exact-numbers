# ExactFraction

ExactFraction is a number implementation, inspired by the [BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html) class.
An ExactFraction allows for exact representations of rational numbers by storing each number as a pair of BigInteger's, representing the numerator and denominator.
Unlike a BigDecimal, an ExactFraction doesn't specify a precision for infinite rational numbers (i.e. 5/9), so operations can be performed on ExactFraction's without losing precision due to rounding.
