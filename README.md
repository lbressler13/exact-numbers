# ExactNumbers

## Number Types

### Rational (ExactFraction)
ExactFraction is an implementation of the [Number](https://docs.oracle.com/javase/8/docs/api/java/lang/Number.html) class.
It was inspired by the [BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html) class, but fills a slightly different purpose.
An ExactFraction is an exact representation of a rational number.
It stores the number as a pair of BigInteger's, representing the numerator and denominator, which means that infinite rational numbers (i.e. 5/9) can be stored without rounding.
Therefore, operations with ExactFraction's can be performed without losing precision due to rounding.

### Irrational
Though the exact value of an irrational number can't be stored, the value is represented as a collection of rational values related to the number.
This allows numbers to be multiplied, divided, and simplified before computing the final value, which allows for greater precision when getting the value.

### Term
A term is a way of representing the product of several numbers, which can include both rational and irrational numbers.
Terms can be multiplied and divided, and the list of numbers is simplified as much as possible before calculating the final value.

## Project Structure
```project
├── exact-numbers
│   ├── build                     <-- automatically generated build files
│   ├── libs                      <-- local libraries
│   ├── src
│   │   ├── main
│   │   │   ├── kotlin
│   │   │   │   ├── common             <-- code that is shared between packages
│   │   │   │   ├── exactnumbers       <-- source code for exact-numbers package
│   │   │   │   │   ├── exactfraction  <-- code for ExactFraction class
│   │   │   │   │   ├── ext            <-- extension functions for existing classes 
│   │   │   │   │   ├── irrationals    <-- code for representations of various types of irrational numbers
│   │   │   │   ├── expressions        <-- source code for expressions package
│   │   ├── test
│   │   │   ├── kotlin
│   │   │   │   ├── exactnumbers  <-- unit tests for exact-numbers package
│   │   │   │   ├── expressions   <-- unit tests for expressions package
│   ├── build.gradle.kts          <-- build configurations
├── README
└── settings.gradle.kts
```

## Building
The package can be built using an IDE, or with the following command:
```./gradlew build```

When the package is built, a .jar file will be generated in the build/libs folder.
The name will be in the format "exact-numbers-version", where the version is specified in the build.gradle.kts file.

### Dependencies
This app has a dependency on the [kotlin-utils](https://github.com/lbressler13/kotlin-utils) package.
This package must be built and placed in a local **libs** folder in order for a gradle build to succeed.
The package version can be updated in the module-level build.gradle.

## Testing
Unit tests are written using the [Kotlin test](https://kotlinlang.org/api/latest/kotlin.test/) framework.
Tests must be written for all logic in the package.

Tests can be run using an IDE, or with the following command:
```./gradlew test```

## Linting
Linting is done using [ktlint](https://ktlint.github.io/), using [this](https://github.com/jlleitschuh/ktlint-gradle) plugin.
See [here](https://github.com/pinterest/ktlint#standard-rules) for a list of standard rules.

Linting can be run using an IDE, or with the following command:
```./gradlew ktlintFormat```

## Importing the package
In order to import the package, copy the most recent .jar file into your project, and add the file to the list of imports for the project.
