package xyz.lbres.exactnumbers.exactfraction

import java.math.BigInteger

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [BigInteger]
 * @param denominator [BigInteger]
 */
fun ExactFraction(numerator: BigInteger, denominator: BigInteger): ExactFraction = ExactFractionImpl(numerator, denominator)

/**
 * Create an ExactFraction by specifying numerator only
 * @param numerator [BigInteger]
 */
fun ExactFraction(numerator: BigInteger): ExactFraction = ExactFractionImpl(numerator, BigInteger.ONE)

/**
 * Create an ExactFraction by specifying numerator only
 * @param numerator [Int]
 */
fun ExactFraction(numerator: Int): ExactFraction = ExactFractionImpl(numerator.toBigInteger(), BigInteger.ONE)

/**
 * Create an ExactFraction by specifying numerator only
 * @param numerator [Long]
 */
fun ExactFraction(numerator: Long): ExactFraction = ExactFractionImpl(numerator.toBigInteger(), BigInteger.ONE)

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [Int]
 */
fun ExactFraction(numerator: Int, denominator: Int): ExactFraction = ExactFractionImpl(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Long]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: Long, denominator: Long): ExactFraction = ExactFractionImpl(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: Long, denominator: Int): ExactFraction = ExactFractionImpl(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: Int, denominator: Long): ExactFraction = ExactFractionImpl(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [BigInteger]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: BigInteger, denominator: Int): ExactFraction = ExactFractionImpl(numerator, denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [BigInteger]
 */
fun ExactFraction(numerator: Int, denominator: BigInteger): ExactFraction = ExactFractionImpl(numerator.toBigInteger(), denominator)

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [BigInteger]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: BigInteger, denominator: Long): ExactFraction = ExactFractionImpl(numerator, denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Long]
 * @param denominator [BigInteger]
 */
fun ExactFraction(numerator: Long, denominator: BigInteger): ExactFraction = ExactFractionImpl(numerator.toBigInteger(), denominator)

/**
 * Create ExactFraction by parsing a string
 * @param string [String]
 */
fun ExactFraction(string: String): ExactFraction = ExactFraction.parse(string)
