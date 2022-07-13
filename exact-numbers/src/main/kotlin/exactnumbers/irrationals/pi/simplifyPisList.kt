package exactnumbers.irrationals.pi

import shared.NumType
import kotlin.math.abs

internal fun simplifyPisList(pis: List<NumType>): List<Pi> {
    pis as List<Pi>

    val positive = pis.count { !it.isDivided }
    val negative = pis.size - positive
    val diff = abs(positive - negative)

    return when {
        positive == negative -> listOf()
        positive < negative -> List(diff) { Pi(isDivided = true) }
        else -> List(diff) { Pi(isDivided = false) }
    }
}
