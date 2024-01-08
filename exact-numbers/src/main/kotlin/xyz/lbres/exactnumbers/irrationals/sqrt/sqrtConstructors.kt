package xyz.lbres.exactnumbers.irrationals.sqrt

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import java.math.BigInteger

fun Sqrt(radicand: ExactFraction): Sqrt = SqrtImpl(radicand)
fun Sqrt(radicand: Int): Sqrt = SqrtImpl(ExactFraction(radicand))
fun Sqrt(radicand: Long): Sqrt = SqrtImpl(ExactFraction(radicand))
fun Sqrt(radicand: BigInteger): Sqrt = SqrtImpl(ExactFraction(radicand))
