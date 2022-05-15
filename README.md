# ExactNumbers

### ExactFraction
ExactFraction is an implementation of the [Number](https://docs.oracle.com/javase/8/docs/api/java/lang/Number.html) class.
It was inspired by the [BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html) class, but fills a slightly different purpose.
An ExactFraction is an exact representation of a rational number.
It stores the number as a pair of BigInteger's, representing the numerator and denominator, which means that infinite rational numbers (i.e. 5/9) can be stored without rounding.
Therefore, operations with ExactFraction's can be performed without losing precision due to rounding.

## Project Structure
```
exact-numbers
│   README.md
│   settings.gradle.kts             <-- project-level gradle settings
│
└───exact-fraction
    │
    └───src
        │
        │   build.gradle.kts        <-- build configurations
        │
        └───main
        │   │
        │   └───kotlin
        │       │   exactfraction   <-- code for ExactFraction class
        │       │   ext             <-- extension functions for existing classes
        │       │   utils           <-- reusable functions
        │      
        └───test                    <-- unit tests for all code
```

## Testing
Unit tests are written using the [Kotlin test](https://kotlinlang.org/api/latest/kotlin.test/) framework.
Tests are written for all logic in the package.

Tests can be run using an IDE, or with the following command:
`./gradlew test`
