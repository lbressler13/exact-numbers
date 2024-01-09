package xyz.lbres.exactnumbers.irrationals.log

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumberCompanion
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.general.simpleIf
import xyz.lbres.kotlinutils.set.multiset.anyConsistent
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMutableMultiSetOf
import xyz.lbres.kotlinutils.set.multiset.const.emptyConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.mapToSet
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import kotlin.math.abs

/**
 * Representation of a log, with an integer base and rational argument
 */
sealed class Log : IrrationalNumber<Log>() {
    abstract val argument: ExactFraction
    abstract val base: Int

    abstract fun getSimplified(): Pair<ExactFraction, Log>

    companion object : IrrationalNumberCompanion<Log>() {
        override val TYPE = "Log"

        val ZERO = Log(ExactFraction.ONE, 10)
        val ONE = Log(ExactFraction.TEN, 10)

        /**
         * Extract rational values and simplify remaining set of logs
         *
         * @param numbers [ConstMultiSet]<Log>: set to simplify
         * @return [Pair]<ExactFraction, ConstMultiSet<Log>>: product of rational values and simplified set of logs
         */
        // TODO: improve simplification by looking at bases
        override fun simplifySet(numbers: ConstMultiSet<Log>): Pair<ExactFraction, ConstMultiSet<Log>> {
            when {
                numbers.isEmpty() -> return Pair(ExactFraction.ONE, emptyConstMultiSet())
                numbers.anyConsistent(Log::isZero) -> return Pair(ExactFraction.ZERO, emptyConstMultiSet())
            }

            val simplifiedNumbers = numbers.mapToSetConsistent { it.getSimplified() }
            val coefficient = simplifiedNumbers.fold(ExactFraction.ONE) { acc, pair -> acc * pair.first }

            val logValues: ConstMultiSet<Log> = simplifiedNumbers.mapToSet { it.second }.toConstMultiSet()
            val distinct = logValues.distinctValues.map { Log(it.argument, it.base) }.toSet()

            // avoids creating a standard MultiSet for efficiency
            val simplifiedValues: ConstMutableMultiSet<Log> = constMutableMultiSetOf()
            for (log in distinct) {
                if (log != ONE) {
                    val diff = logValues.getCountOf(log) - logValues.getCountOf(log.inverse())
                    val simplified: ConstMultiSet<Log> = ConstMultiSet(abs(diff)) {
                        simpleIf(diff < 0, { log.inverse() }, { log })
                    }
                    simplifiedValues.addAll(simplified)
                }
            }

            return Pair(coefficient, simplifiedValues)
        }
    }
}
