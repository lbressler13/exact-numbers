package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger

/**
 * Construct a Log by specifying argument and base
 * @param argument [ExactFraction]
 * @param base [Int]
 */
fun Log(argument: ExactFraction, base: Int): Log = LogImpl(argument, base, false)

/**
 * Construct a Log by specifying argument and base
 * @param argument [Int]
 * @param base [Int]
 */
fun Log(argument: Int, base: Int): Log = Log(ExactFraction(argument), base)

/**
 * Construct a Log by specifying argument and base
 * @param argument [Long]
 * @param base [Int]
 */
fun Log(argument: Long, base: Int): Log = Log(ExactFraction(argument), base)

/**
 * Construct a Log by specifying argument and base
 * @param argument [BigInteger]
 * @param base [Int]
 */
fun Log(argument: BigInteger, base: Int): Log = Log(ExactFraction(argument), base)

/**
 * Construct a Log by specifying argument only
 * @param argument [ExactFraction]
 */
fun Log(argument: ExactFraction): Log = Log(argument, base = 10)

/**
 * Construct a Log by specifying argument only
 * @param argument [Int]
 */
fun Log(argument: Int): Log = Log(ExactFraction(argument))

/**
 * Construct a Log by specifying argument only
 * @param argument [Long]
 */
fun Log(argument: Long): Log = Log(ExactFraction(argument))

/**
 * Construct a Log by specifying argument only
 * @param argument [BigInteger]
 */
fun Log(argument: BigInteger): Log = Log(ExactFraction(argument))
