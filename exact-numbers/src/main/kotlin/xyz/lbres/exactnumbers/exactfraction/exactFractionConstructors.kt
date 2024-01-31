package xyz.lbres.exactnumbers.exactfraction

import java.math.BigInteger

/**
 * Construct an ExactFraction by specifying numerator and denominator
 * @param numerator [BigInteger]
 * @param denominator [BigInteger]
 */
fun ExactFraction(numerator: BigInteger, denominator: BigInteger): ExactFraction = ExactFractionImpl(numerator, denominator)

/**
 * Construct an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [Int]
 */
fun ExactFraction(numerator: Int, denominator: Int): ExactFraction = ExactFraction(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Construct an ExactFraction by specifying numerator and denominator
 * @param numerator [Long]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: Long, denominator: Long): ExactFraction = ExactFraction(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Construct an ExactFraction by specifying numerator and denominator
 * @param numerator [Long]
 * @param denominator [Int]
 */
fun ExactFraction(numerator: Long, denominator: Int): ExactFraction = ExactFraction(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Construct an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: Int, denominator: Long): ExactFraction = ExactFraction(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Construct an ExactFraction by specifying numerator and denominator
 * @param numerator [BigInteger]
 * @param denominator [Int]
 */
fun ExactFraction(numerator: BigInteger, denominator: Int): ExactFraction = ExactFraction(numerator, denominator.toBigInteger())

/**
 * Construct an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [BigInteger]
 */
fun ExactFraction(numerator: Int, denominator: BigInteger): ExactFraction = ExactFraction(numerator.toBigInteger(), denominator)

/**
 * Construct an ExactFraction by specifying numerator and denominator
 * @param numerator [BigInteger]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: BigInteger, denominator: Long): ExactFraction = ExactFraction(numerator, denominator.toBigInteger())

/**
 * Construct an ExactFraction by specifying numerator and denominator
 * @param numerator [Long]
 * @param denominator [BigInteger]
 */
fun ExactFraction(numerator: Long, denominator: BigInteger): ExactFraction = ExactFraction(numerator.toBigInteger(), denominator)

/**
 * Construct an ExactFraction by specifying numerator only
 * @param numerator [BigInteger]
 */
fun ExactFraction(numerator: BigInteger): ExactFraction = ExactFraction(numerator, BigInteger.ONE)

/**
 * Construct an ExactFraction by specifying numerator only
 * @param numerator [Int]
 */
fun ExactFraction(numerator: Int): ExactFraction = ExactFraction(numerator.toBigInteger())

/**
 * Construct an ExactFraction by specifying numerator only
 * @param numerator [Long]
 */
fun ExactFraction(numerator: Long): ExactFraction = ExactFraction(numerator.toBigInteger())

/**
 * Construct ExactFraction by parsing an EF-format or decimal string
 * @param string [String]
 */
fun ExactFraction(string: String): ExactFraction = ExactFraction.parse(string)
