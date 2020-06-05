package test.com.github.fj.android.util

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 01 - Nov - 2018
 */
import java.util.*
import kotlin.random.Random
import kotlin.reflect.KClass

fun randomBoolean(): Boolean {
    return Random.nextInt(2) == 1
}

fun randomUInt(): Int {
    return randomUInt(Int.MAX_VALUE)
}

fun randomUInt(upperBound: Int): Int {
    return Random.nextInt(0, upperBound)
}

fun randomULong(): Long {
    return randomULong(Long.MAX_VALUE)
}

fun randomULong(upperBound: Long): Long {
    return Random.nextLong(0, upperBound)
}

/**
 * @return Random float number between 0(inclusive) and 1(exclusive).
 */
fun randomFloat(): Float {
    return Random.nextFloat()
}

/**
 * @return Random float number between [min](inclusive) and [max](exclusive).
 */
fun randomFloat(min: Float, max: Float): Float {
    if (min >= max) {
        throw IllegalArgumentException("min($min) >= max($max)")
    }

    return Random.nextFloat() * (min - max) + min
}

fun getRandomAlphaNumericString(length: Int) =
    getRandomAlphaNumericStringInternal(
        length,
        RANDOM_ALPHANUMERIC_CHARS
    )

fun getRandomCapitalAlphaNumericString(length: Int) =
    getRandomAlphaNumericStringInternal(
        length,
        RANDOM_CAPITAL_CHARS
    )

fun <T> randomElementOf(collections: Collection<T>): T {
    val targetIdx: Int = Random.nextInt(collections.size)
    return elementAt(collections, targetIdx)
}

fun randomLocale(): Locale? {
    val locales: Array<Locale> = Locale.getAvailableLocales()
    var l: Locale
    if (locales.size == 1) {
        l = locales[0]
        if (l.country.isEmpty() || l.language.isEmpty()) {
            throw java.lang.UnsupportedOperationException("This system supports no locale to perform tests")
        }
    } else {
        do {
            l = locales[randomUInt(locales.size)]
        } while (l.country.isEmpty() || l.language.isEmpty())
    }
    return l
}

fun <T : Enum<*>> randomEnum(klass: KClass<T>): T {
    return randomEnum(klass, null)
}

fun <T : Enum<*>> randomEnum(klass: KClass<T>, acceptFunction: ((T) -> Boolean)?): T {
    // Not supported in Kotlin reflection
    val constants = klass.java.enumConstants
        ?: throw IllegalArgumentException("$klass does not hold any enum constants")
    var randomValue: T
    var acceptFuncResult: Boolean

    do {
        val randomIndex = randomUInt(constants.size)
        randomValue = constants[randomIndex]
        acceptFuncResult = acceptFunction?.invoke(randomValue) ?: true
    } while (!acceptFuncResult)

    return randomValue
}

private fun <T> elementAt(collection: Collection<T>, targetIdx: Int): T {
    for ((idx, it) in collection.withIndex()) {
        if (targetIdx == idx) {
            return it
        }
    }
    throw UnsupportedOperationException("targetIdx: " + targetIdx + ", collection size: " + collection.size)
}

private const val RANDOM_ALPHANUMERIC_CHARS =
    "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXxYyZz1234567890"
private const val RANDOM_CAPITAL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZz1234567890"

private fun getRandomAlphaNumericStringInternal(length: Int, pool: CharSequence): String {
    val sb = StringBuffer(length)
    for (loop in 0 until length) {
        val index = Random.nextInt(pool.length)
        sb.append(pool[index])
    }

    return sb.toString()
}
