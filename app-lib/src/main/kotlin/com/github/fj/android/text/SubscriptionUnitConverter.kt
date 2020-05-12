package com.github.fj.android.text

/**
 * Helper to parse [com.android.billingclient.api.SkuDetails#subscriptionPeriod].
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import java.util.regex.Pattern

private const val REGEXP =
    "^P(?=\\d+[YMWD])(\\d+Y)?(\\d+M)?(\\d+W)?(\\d+D)?(T(?=\\d+[HMS])(\\d+H)?(\\d+M)?(\\d+S)?)?\$"
private val PERIOD_PATTERN = Pattern.compile(REGEXP, Pattern.MULTILINE)

fun String.toDaysSince(since: LocalDateTime): Int {
    return between(ChronoUnit.DAYS, since, this)
}

fun String.toWeeksSince(since: LocalDateTime) =
    between(ChronoUnit.WEEKS, since, this)

fun String.toMonthsSince(since: LocalDateTime) =
    between(ChronoUnit.MONTHS, since, this)

fun String.toYearsSince(since: LocalDateTime) =
    between(ChronoUnit.YEARS, since, this)

private fun between(unit: ChronoUnit, since: LocalDateTime, iso8601DateStr: String?): Int {
    val matcher = PERIOD_PATTERN.matcher(iso8601DateStr ?: return 0).takeIf {
        it.find()
    } ?: return 0

    val numExtractor: (String?) -> Int = numExtractor@{
        return@numExtractor if (it.isNullOrEmpty()) {
            0
        } else {
            it.substring(0, it.length - 1).toInt()
        }
    }

    @SuppressWarnings("MagicNumber")
    val years = numExtractor(matcher.group(1))

    @SuppressWarnings("MagicNumber")
    val months = numExtractor(matcher.group(2))

    @SuppressWarnings("MagicNumber")
    val weeks = numExtractor(matcher.group(3))

    @SuppressWarnings("MagicNumber")
    val days = numExtractor(matcher.group(4))

    val endDate = since.plusDays(days.toLong())
        .plusWeeks(weeks.toLong())
        .plusMonths(months.toLong())
        .plusYears(years.toLong())

    return unit.between(since, endDate).toInt()
}
