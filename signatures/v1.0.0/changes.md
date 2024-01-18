*v0.1.1 → v1.0.0*

### xyz.lbres.exactnumbers.exactfraction

**ExactFraction**
```diff
class ExactFraction

- var numerator: BigInteger
+ val numerator: BigInteger

- var denominator: BigInteger
+ val denominator: BigInteger

+ fun isWholeNumber(): Boolean
+ fun pow(other: BigInteger): ExactFraction
+ fun pow(other: Long): ExactFraction
+ fun pow(other: Int): ExactFraction
+ fun roundToWhole(roundingMode: RoundingMode): ExactFraction
```

**Other**

```diff
- fun checkIsEFString(s: String): Boolean
```

### xyz.lbres.exactnumbers.exceptions

**CastingOverflowException**
```diff
+ class CastingOverflowException: ArithmeticException
```

### xyz.lbres.exactnumbers.expressions.term

**Note**: xyz.lbres.expressions.term → xyz.lbres.exactnumbers.expressions.term

**Term**
```diff
- class Term
+ class Term: Number()

+ val factors: List<IrrationalNumber<*>>
+ val logs: List<Log>
+ val squareRoots: List<Sqrt>
+ val pis: List<Pi>
+ val piCount: Int

+ fun getFactorsByType(irrationalType: String): List<IrrationalNumber<*>>

- fun getLogs(): List<Log>
+ @Deprecated fun getLogs(): List<Log>

- fun getPiCount(): Int
+ @Deprecated fun getPiCount(): Int

- fun getSquareRoots(): List<Sqrt>
+ @Deprecated fun getSquareRoots(): List<Sqrt>
```

**Term.Companion**
```diff
object Term.Companion

+ fun fromValues(coefficient: ExactFraction, factors: List<IrrationalNumber<*>>): Term
```

### xyz.lbres.exactnumbers.irrationals

**IrrationalNumber**
```diff
+ abstract class IrrationalNumber<T : IrrationalNumber<T>>: Comparable<T>, Number()
```

### xyz.lbres.exactnumbers.irrationals.log

**Log**
```diff
- class Log: Comparable<Log>, Irrational
+ class Log: IrrationalNumber<Log>

- Log(argument: BigInteger, isDivided: Boolean)
- Log(argument: BigInteger, base: Int, isDivided: Boolean)
- Log(argument: ExactFraction, isDivided: Boolean)
- Log(argument: ExactFraction, base: Int, isDivided: Boolean)
- Log(argument: Int, isDivided: Boolean)
- Log(argument: Int, base: Int, isDivided: Boolean)
- Log(argument: Long, isDivided: Boolean)
- Log(argument: Long, base: Int, isDivided: Boolean)

- val isDivided: Boolean
+ @Deprecated val isDivided: Boolean

- fun swapDivided(): Log
+ @Deprecated fun swapDivided(): Log
```

### xyz.lbres.exactnumbers.irrationals.pi

**Pi**
```diff
- class Pi: Irrational
+ class Pi: IrrationalNumber<Pi>()

- Pi(isDivided: Boolean): Pi

+ fun toPlainString(): String

- val isDivided: Boolean
+ @Deprecated val isDivided: Boolean

- fun swapDivided(): Pi
+ @Deprecated fun swapDivided(): Pi
```

### xyz.lbres.exactnumbers.irrationals.pi

**Sqrt**
```diff
- class Sqrt: Comparable<Sqrt>, Irrational
+ class Sqrt: IrrationalNumber<Sqrt>

+ fun toPlainString(): String

- val isDivided: Boolean
+ @Deprecated val isDivided: Boolean

- fun swapDivided(): Sqrt
+ @Deprecated fun swapDivided(): Sqrt
```

### xyz.lbres.expression.term

**Note**: xyz.lbres.expressions.term → xyz.lbres.exactnumbers.expressions.term

```diff
- class Term
+ @Deprecated typealias Term = xyz.lbres.exactnumbers.expressions.term.Term
```
