package eu.szwiec.checkittravelkit

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException

fun isValidFormat(format: String, value: String): Boolean {
    var ldt: LocalDateTime? = null
    val fomatter = DateTimeFormatter.ofPattern(format)

    try {
        ldt = LocalDateTime.parse(value, fomatter)
        val result = ldt!!.format(fomatter)
        return result == value
    } catch (e: DateTimeParseException) {
        try {
            val ld = LocalDate.parse(value, fomatter)
            val result = ld.format(fomatter)
            return result == value
        } catch (exp: DateTimeParseException) {
            try {
                val lt = LocalTime.parse(value, fomatter)
                val result = lt.format(fomatter)
                return result == value
            } catch (e2: DateTimeParseException) {
                e2.printStackTrace()
            }
        }
    }

    return false
}