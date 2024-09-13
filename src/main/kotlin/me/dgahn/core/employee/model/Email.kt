package me.dgahn.core.employee.model

@JvmInline
value class Email private constructor(
    val value: String,
) {
    companion object {
        private val EMAIL_REGEX = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")

        fun of(value: String): Email {
            require(isValidEmail(value)) { "Invalid email format: $value" }
            return Email(value)
        }

        private fun isValidEmail(value: String): Boolean {
            return EMAIL_REGEX.matches(value)
        }
    }
}
