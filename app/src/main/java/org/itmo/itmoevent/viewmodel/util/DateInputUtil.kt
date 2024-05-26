package org.itmo.itmoevent.viewmodel.util

import org.itmo.itmoevent.viewmodel.GeneralEventsViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date

class DateInputUtil {
    companion object {

        private const val FILTER_DATE_REGEX: String =
            "^(0?[1-9]|[12][0-9]|3[01])\\.(0?[1-9]|1[012])\\.\\d{4}( ([0-1]?\\d|2[0-3])(:([0-5]?\\d)))?\$"
        private const val FILTER_DATE_PATTERN: String = "dd.MM.yyyy"
        private const val FILTER_DATETIME_PATTERN: String = "dd.MM.yyyy HH:mm"


        fun isDateValid(dateString: String) =
            FILTER_DATE_REGEX.toRegex().matches(dateString)

        fun parseDate(dateString: String?): LocalDateTime? {
            val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(FILTER_DATE_PATTERN)
            val dateTimeFormatter: DateTimeFormatter =
                DateTimeFormatter.ofPattern(FILTER_DATETIME_PATTERN)
            val dateTime: LocalDateTime = try {
                LocalDate.parse(dateString, dateFormatter).atStartOfDay()
            } catch (ex: DateTimeParseException) {
                try {
                    LocalDateTime.parse(dateString, dateTimeFormatter)
                } catch (ex: DateTimeParseException) {
                    return null
                }
            }
            return dateTime
        }

        fun isFutureDate(date: LocalDateTime) = date.isAfter(LocalDateTime.now())

        fun parseLocalDateTime(dateTime: LocalDateTime?) = dateTime?.let {
            Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
        }
    }
}