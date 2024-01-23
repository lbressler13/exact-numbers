*v0.1.1 → v1.0.0*

### xyz.lbres.exactnumbers.exactfraction

**ExactFraction**
```kotlin
class ExactFraction

DEL var numerator: BigInteger
ADD val numerator: BigInteger

DEL var denominator: BigInteger
ADD val denominator: BigInteger

ADD fun isWholeNumber(): Boolean
ADD fun pow(other: BigInteger): ExactFraction
ADD fun pow(other: Long): ExactFraction
ADD fun pow(other: Int): ExactFraction
ADD fun roundToWhole(roundingMode: RoundingMode): ExactFraction
```

**Other**

```kotlin
DEL fun checkIsEFString(s: String): Boolean
```

### xyz.lbres.exactnumbers.exceptions

**CastingOverflowException**
```kotlin
ADD class CastingOverflowException: ArithmeticException
```

### xyz.lbres.exactnumbers.expressions.term

**Note**: xyz.lbres.expressions.term → xyz.lbres.exactnumbers.expressions.term

**Term**
```kotlin
ADD class Term
DEL class Term: Number()

ADD val factors: List<IrrationalNumber<*>>
ADD val logs: List<Log>
ADD val squareRoots: List<Sqrt>
ADD val pis: List<Pi>
ADD val piCount: Int

ADD fun getFactorsByType(irrationalType: String): List<IrrationalNumber<*>>

DEL fun getLogs(): List<Log>
ADD @Deprecated fun getLogs(): List<Log>

DEL fun getPiCount(): Int
ADD @Deprecated fun getPiCount(): Int

DEL fun getSquareRoots(): List<Sqrt>
ADD @Deprecated fun getSquareRoots(): List<Sqrt>
```

**Term.Companion**
```kotlin
object Term.Companion

ADD fun fromValues(coefficient: ExactFraction, factors: List<IrrationalNumber<*>>): Term
```

### xyz.lbres.exactnumbers.irrationals

**IrrationalNumber**
```kotlin
ADD abstract class IrrationalNumber<T : IrrationalNumber<T>>: Comparable<T>, Number()
```

### xyz.lbres.exactnumbers.irrationals.log

**Log**
```kotlin
DEL class Log: Comparable<Log>, Irrational
ADD class Log: IrrationalNumber<Log>

ADD Log(argument: BigInteger, isDivided: Boolean)
ADD Log(argument: BigInteger, base: Int, isDivided: Boolean)
ADD Log(argument: ExactFraction, isDivided: Boolean)
ADD Log(argument: ExactFraction, base: Int, isDivided: Boolean)
ADD Log(argument: Int, isDivided: Boolean)
ADD Log(argument: Int, base: Int, isDivided: Boolean)
ADD Log(argument: Long, isDivided: Boolean)
ADD Log(argument: Long, base: Int, isDivided: Boolean)

DEL val isDivided: Boolean
ADD @Deprecated val isDivided: Boolean

DEL fun swapDivided(): Log
ADD @Deprecated fun swapDivided(): Log
```

### xyz.lbres.exactnumbers.irrationals.pi

**Pi**
```kotlin
DEL class Pi: Irrational
ADD class Pi: IrrationalNumber<Pi>()

DEL Pi(isDivided: Boolean): Pi

ADD fun toPlainString(): String

DEL val isDivided: Boolean
ADD @Deprecated val isDivided: Boolean

DEL fun swapDivided(): Pi
ADD @Deprecated fun swapDivided(): Pi
```

### xyz.lbres.exactnumbers.irrationals.pi

**Sqrt**
```kotlin
DEL class Sqrt: Comparable<Sqrt>, Irrational
ADD class Sqrt: IrrationalNumber<Sqrt>

ADD fun toPlainString(): String

DEL val isDivided: Boolean
ADD @Deprecated val isDivided: Boolean

DEL fun swapDivided(): Sqrt
ADD @Deprecated fun swapDivided(): Sqrt
```

### xyz.lbres.expression.term

**Note**: xyz.lbres.expressions.term → xyz.lbres.exactnumbers.expressions.term

```kotlin
DEL class Term
ADD @Deprecated typealias Term = xyz.lbres.exactnumbers.expressions.term.Term
```
