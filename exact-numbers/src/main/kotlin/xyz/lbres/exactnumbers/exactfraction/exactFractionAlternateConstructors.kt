package xyz.lbres.exactnumbers.exactfraction

import java.math.BigInteger

/**
 * Create an ExactFraction by specifying numerator only. Denominator is set to [BigInteger.ONE]
 * @param numerator [BigInteger]
 */
fun ExactFraction(numerator: BigInteger) = ExactFraction(numerator, BigInteger.ONE)

/**
 * Create an ExactFraction by specifying numerator only. Denominator is set to [BigInteger.ONE]
 * @param numerator [Int]
 */
fun ExactFraction(numerator: Int) = ExactFraction(numerator.toBigInteger(), BigInteger.ONE)

/**
 * Create an ExactFraction by specifying numerator only. Denominator is set to [BigInteger.ONE]
 * @param numerator [Long]
 */
fun ExactFraction(numerator: Long) = ExactFraction(numerator.toBigInteger(), BigInteger.ONE)

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [Int]
 */
fun ExactFraction(numerator: Int, denominator: Int) = ExactFraction(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Long]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: Long, denominator: Long) = ExactFraction(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: Long, denominator: Int) = ExactFraction(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: Int, denominator: Long) = ExactFraction(numerator.toBigInteger(), denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [BigInteger]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: BigInteger, denominator: Int) = ExactFraction(numerator, denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Int]
 * @param denominator [BigInteger]
 */
fun ExactFraction(numerator: Int, denominator: BigInteger) = ExactFraction(numerator.toBigInteger(), denominator)

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [BigInteger]
 * @param denominator [Long]
 */
fun ExactFraction(numerator: BigInteger, denominator: Long) = ExactFraction(numerator, denominator.toBigInteger())

/**
 * Create an ExactFraction by specifying numerator and denominator
 * @param numerator [Long]
 * @param denominator [BigInteger]
 */
fun ExactFraction(numerator: Long, denominator: BigInteger) = ExactFraction(numerator.toBigInteger(), denominator)

/**
 * Create ExactFraction by parsing a string
 *
 * @param s [String]
 */
fun ExactFraction(s: String) = ExactFraction.parse(s)
