package me.dgahn.core.employee.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class Employee(
    val name: String,
    val email: Email,
    val tel: PhoneNumber,
    val joined: LocalDate,
) {
    companion object {
        private val DATE_FORMAT_REGEX_DASH = Regex("^\\d{4}-\\d{2}-\\d{2}$")
        private val DATE_FORMAT_REGEX_DOT = Regex("^\\d{4}\\.\\d{2}\\.\\d{2}$")
        private val FORMATTER_DASH = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        private val FORMATTER_DOT = DateTimeFormatter.ofPattern("yyyy.MM.dd")

        private fun isValidDateFormat(dateString: String): Boolean {
            return DATE_FORMAT_REGEX_DASH.matches(dateString) || DATE_FORMAT_REGEX_DOT.matches(dateString)
        }

        private fun parseJoinedDate(dateString: String): LocalDate {
            return when {
                DATE_FORMAT_REGEX_DASH.matches(dateString) -> LocalDate.parse(dateString, FORMATTER_DASH)
                DATE_FORMAT_REGEX_DOT.matches(dateString) -> LocalDate.parse(dateString, FORMATTER_DOT)
                else -> throw IllegalArgumentException(
                    "Invalid date format: $dateString. Expected formats are yyyy-MM-dd or yyyy.MM.dd.",
                )
            }
        }

        fun of(name: String, email: String, tel: String, joined: String): Employee {
            val joinedDate = parseJoinedDate(joined)
            return Employee(name = name, email = Email.of(email), tel = PhoneNumber.of(tel), joined = joinedDate)
        }

        fun of(name: String, email: String, tel: String, joined: LocalDate): Employee {
            return Employee(name = name, email = Email.of(email), tel = PhoneNumber.of(tel), joined = joined)
        }
    }
}
