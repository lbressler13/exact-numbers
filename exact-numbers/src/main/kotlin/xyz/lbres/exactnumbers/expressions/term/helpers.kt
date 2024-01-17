package xyz.lbres.exactnumbers.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.IrrationalNumber
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
import xyz.lbres.kotlinutils.set.multiset.mapToSetConsistent
import kotlin.math.abs

/**
 * Create a simplified version of a Term
 *
 * @param coefficient [ExactFraction]: coefficient of term
 * @param factorGroups [Map]<String, List<IrrationalNumber<*>>: map where keys are irrational number types,
 * and values are lists of numbers with that type
 * @return [Term]: new term with simplified coefficient and factors
 */
internal fun createSimplifiedTerm(coefficient: ExactFraction, factorGroups: Map<String, List<IrrationalNumber<*>>>): Term {
    var newCoefficient = coefficient
    val newFactors: MutableList<IrrationalNumber<*>> = mutableListOf()

    factorGroups.forEach { (type, values) ->
        val valueSet = values.toConstMultiSet()

        @Suppress("UNCHECKED_CAST")
        val simplifiedValues = when (type) {
            Log.TYPE -> Log.simplifySet(valueSet as ConstMultiSet<Log>)
            Sqrt.TYPE -> Sqrt.simplifySet(valueSet as ConstMultiSet<Sqrt>)
            Pi.TYPE -> Pi.simplifySet(valueSet as ConstMultiSet<Pi>)
            else -> simplifyGenericIrrational(valueSet)
        }

        newCoefficient *= simplifiedValues.first
        newFactors.addAll(simplifiedValues.second)
    }

    return Term.fromValues(newCoefficient, newFactors)
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

    val distinct = values.mapToSetConsistent { simpleIf(it.isInverted, { it.inverse() }, { it }) }.distinctValues
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
                val valueToAdd = simpleIf(diff < 0, { value.inverse() }, { value })
                simplifiedValues.add(valueToAdd)
            }
        }
    }

    return Pair(coefficient, simplifiedValues)
}
