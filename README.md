# ExactNumbers

[![All Checks](https://github.com/lbressler13/exact-numbers/actions/workflows/main_workflow.yml/badge.svg?branch=main)](https://github.com/lbressler13/exact-numbers/actions/workflows/main_workflow.yml)

## Number Types

### Rational (ExactFraction)

ExactFraction is an implementation of the [Number](https://docs.oracle.com/javase/8/docs/api/java/lang/Number.html) class.
It was inspired by the [BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html) class, but fills a slightly different purpose.
An ExactFraction is an exact representation of a rational number.
It stores the number as a pair of BigIntegers, representing the numerator and denominator, which means that infinitely repeating rational numbers (i.e. 5/9) can be stored without rounding.
Therefore, operations with ExactFractions can be performed without losing precision due to rounding.

### Irrational

Though the exact value of an irrational number can't be stored, the value is represented as a collection of rational numbers related to the irrational.
This allows numbers to be multiplied, divided, and simplified before computing the final value, which allows for greater precision when getting the value.

Irrational numbers extend the IrrationalNumber class.

## Expression Types

### Term

A term is a way of representing the product of several numbers, which can include both rational and irrational numbers.
Terms can be multiplied and divided, and the list of numbers is simplified as much as possible before calculating the final value.

## Project Structure

```project
├── exact-numbers
│   ├── build                     <-- automatically generated build files
│   ├── src
│   │   ├── main
│   │   │   ├── kotlin
│   │   │   │   ├── exactnumbers       <-- source code for exact-numbers package
│   │   │   │   │   ├── exactfraction  <-- code for ExactFraction class
│   │   │   │   │   ├── exceptions     <-- exception classes
│   │   │   │   │   ├── expressions    <-- code for representations of expressions
│   │   │   │   │   ├── ext            <-- extension functions for existing classes 
│   │   │   │   │   ├── irrationals    <-- code for representations of various types of irrational numbers
│   │   │   │   │   ├── utils          <-- shared classes and utils
│   │   ├── test                  
│   │   │   ├── kotlin            <-- unit tests for all packages
│   ├── build.gradle.kts          <-- build configurations
├── README
├── settings.gradle.kts
└── signatures                    <-- markdown files with signatures for public functions and classes
```

## Building

The package can be built using an IDE, or with the following command:
```./gradlew build```

When the package is built, a .jar file will be generated in the build/libs folder.
The name will be in the format "exact-numbers-version", where the version is specified in the build.gradle.kts file.

### Dependencies

This app has a dependency on the [kotlin-utils](https://github.com/lbressler13/kotlin-utils) package, which is published to the GitHub Packages registry.
In order to build the project, you will need a GitHub access token with at least the `read:packages` scope.

You can add the following properties to a local gradle properties file in order to build:
```properties
gpr.user=GITHUB_USERNAME
gpr.key=GITHUB_PAT
```
This will allow you to build through an IDE or the command line.
To build in the command line, you can set:
```shell
USERNAME=GITHUB_USERNAME
TOKEN=GITHUB_PAT
```

However, this configuration may not allow you to build through an IDE.
If you have values set in both gradle.properties and in the environment, the values in gradle.properties will be used.

**Do not commit your access token.**

See [here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#using-a-published-package) for more information on importing GitHub packages.

## Testing

Unit tests are written using the [Kotlin test](https://kotlinlang.org/api/latest/kotlin.test/) framework.
Mocking is done using the [MockK library](https://mockk.io/).
Tests must be written for all logic in the package.

Tests can be run using an IDE, or with the following command:
```./gradlew test```

### Resources

When writing tests for irrationals, it can be useful to use a high-precision calculator, such as [this](https://www.mathsisfun.com/scientific-calculator.html) or [this](https://www.mathsisfun.com/calculator-precision.html), to calculate expected values.

## Linting

Linting is done with [ktlint](https://ktlint.github.io/), using [this](https://github.com/jlleitschuh/ktlint-gradle) plugin.
See [here](https://github.com/pinterest/ktlint#standard-rules) for a list of standard rules.

Code can be formatted using an IDE, or with the following command:
```./gradlew ktlintFormat```

## Importing the package

This package is hosted in the GitHub Packages registry.
See [here](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#using-a-published-package) for information on importing GitHub packages.
