package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger

/**
 * Create a Log by specifying argument, base, and if inverted
 * @param argument [ExactFraction]
 * @param base [Int]
 * @param isInverted [Boolean]
 */
fun Log(argument: ExactFraction, base: Int, isInverted: Boolean): Log = LogImpl(argument, base, isInverted)

fun Log(argument: ExactFraction, isInverted: Boolean): Log = Log(argument, base = 10, isInverted)
fun Log(argument: ExactFraction, base: Int): Log = Log(argument, base, isInverted = false)
fun Log(argument: ExactFraction): Log = Log(argument, base = 10)
fun Log(argument: Int): Log = Log(ExactFraction(argument))
fun Log(argument: Int, base: Int): Log = Log(ExactFraction(argument), base)
fun Log(argument: Int, isInverted: Boolean): Log = Log(ExactFraction(argument), isInverted)
fun Log(argument: Int, base: Int, isInverted: Boolean): Log = Log(ExactFraction(argument), base, isInverted)
fun Log(argument: Long): Log = Log(ExactFraction(argument))
fun Log(argument: Long, base: Int): Log = Log(ExactFraction(argument), base)
fun Log(argument: Long, isInverted: Boolean): Log = Log(ExactFraction(argument), isInverted)
fun Log(argument: Long, base: Int, isInverted: Boolean): Log = Log(ExactFraction(argument), base, isInverted)
fun Log(argument: BigInteger): Log = Log(ExactFraction(argument))
fun Log(argument: BigInteger, base: Int): Log = Log(ExactFraction(argument), base)
fun Log(argument: BigInteger, isInverted: Boolean): Log = Log(ExactFraction(argument), isInverted)
fun Log(argument: BigInteger, base: Int, isInverted: Boolean): Log = Log(ExactFraction(argument), base, isInverted)
