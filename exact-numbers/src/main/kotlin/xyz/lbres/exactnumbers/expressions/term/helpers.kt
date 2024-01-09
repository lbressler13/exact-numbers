package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
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
 * Create a simplified version of a Term
 *
 * @param term [Term]: term to simplify
 * @return [Term]: new term with simplified coefficient and factors
 */
internal fun simplifyTerm(term: Term): Term {
    var newCoefficient = term.coefficient
    val newValues: MutableList<IrrationalNumber<*>> = mutableListOf()

    term.factors.groupBy { it.type }
        .forEach { (type, values) ->
            val valueSet = values.toConstMultiSet()

            @Suppress("UNCHECKED_CAST")
            val simplifiedValues = when (type) {
                Log.TYPE -> Log.simplifySet(valueSet as ConstMultiSet<Log>)
                Sqrt.TYPE -> Sqrt.simplifySet(valueSet as ConstMultiSet<Sqrt>)
                Pi.TYPE -> Pi.simplifySet(valueSet as ConstMultiSet<Pi>)
                else -> simplifyGenericIrrational(valueSet)
            }

            newCoefficient *= simplifiedValues.first
            newValues.addAll(simplifiedValues.second)
        }

    return Term.fromValues(newCoefficient, newValues)
}

/**
 * Simplify a set of irrational numbers by extracting the rational values
 *
 * @param values [ConstMultiSet]<IrrationalNumber<*>>: list of values
 * @return [Pair]<ExactFraction, ConstMultiSet<IrrationalNumber<*>>: pair of values where first value is the product of the numbers
 * with rational values, and the second is a set of the irrational values
 */
private fun simplifyGenericIrrational(values: ConstMultiSet<IrrationalNumber<*>>): Pair<ExactFraction, ConstMultiSet<IrrationalNumber<*>>> {
    when {
        values.isEmpty() -> return Pair(ExactFraction.ONE, emptyConstMultiSet())
        values.anyConsistent { it.isZero() } -> return Pair(ExactFraction.ZERO, emptyConstMultiSet())
    }

    // val distinct = logValues.distinctValues.map { Log(it.argument, it.base) }.toSet()
    val distinct = values.map { simpleIf(it.isInverted, { it.inverse() }, { it }) }

    var coefficient = ExactFraction.ONE

    // avoids creating a standard MultiSet for efficiency
    val simplifiedValues: ConstMutableMultiSet<IrrationalNumber<*>> = constMutableMultiSetOf()
    for (value in distinct) {
        val diff = values.getCountOf(value) - values.getCountOf(value.inverse())
        val simplifiedValue = simpleIf(diff < 0, { value.inverse() }, { value })

        val rationalValue = simplifiedValue.getRationalValue()
        repeat(abs(diff)) {
            if (rationalValue != null) {
                coefficient *= rationalValue
            } else {
                simplifiedValues.add(simplifiedValue)
            }
        }
    }

    return Pair(coefficient, simplifiedValues)
//    var coefficient = ExactFraction.ONE
//    val irrationalValues: ConstMutableMultiSet<IrrationalNumber<*>> = constMutableMultiSetOf()
//
//    values.forEach {
//        val rationalValue = it.getRationalValue()
//        if (rationalValue != null) {
//            coefficient *= rationalValue
//        } else {
//            irrationalValues.add(it)
//        }
//    }
//
//    return Pair(coefficient, irrationalValues)
}
