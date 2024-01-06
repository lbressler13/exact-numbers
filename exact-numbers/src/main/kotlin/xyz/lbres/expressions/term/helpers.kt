package xyz.lbres.expressions.term

import xyz.lbres.exactnumbers.exactfraction.ExactFraction
import xyz.lbres.exactnumbers.irrationals.common.IrrationalNumber
import xyz.lbres.exactnumbers.irrationals.log.Log
import xyz.lbres.exactnumbers.irrationals.pi.Pi
import xyz.lbres.exactnumbers.irrationals.sqrt.Sqrt
import xyz.lbres.kotlinutils.collection.ext.toConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.ConstMutableMultiSet
import xyz.lbres.kotlinutils.set.multiset.const.constMutableMultiSetOf

internal fun simplifyTerm(term: Term): Term {
    val groups = term.irrationals.groupBy { it.type }

    var newCoefficient = term.coefficient
    val newValues: MutableList<IrrationalNumber<*>> = mutableListOf()

    groups.forEach { (type, values) ->
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

private fun simplifyGenericIrrational(values: ConstMultiSet<IrrationalNumber<*>>): Pair<ExactFraction, ConstMultiSet<IrrationalNumber<*>>> {
    var coefficient = ExactFraction.ONE
    val irrationalValues: ConstMutableMultiSet<IrrationalNumber<*>> = constMutableMultiSetOf()

    values.forEach {
        val rationalValue = it.getRationalValue()
        if (rationalValue != null) {
            coefficient *= rationalValue
        } else {
            irrationalValues.add(it)
        }
    }

    return Pair(coefficient, irrationalValues)
}
