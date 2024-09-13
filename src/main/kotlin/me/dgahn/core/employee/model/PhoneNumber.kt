package me.dgahn.core.employee.model

@JvmInline
value class PhoneNumber private constructor(
    val value: String,
) {
    init {
        require(isValidPhoneNumber(value)) { "Invalid phone number format: $value" }
    }

    companion object {
        private val PHONE_REGEX_WITH_DASH = Regex("^010-\\d{4}-\\d{4}$")
        private val PHONE_REGEX_WITHOUT_DASH = Regex("^010\\d{4}\\d{4}$")

        fun of(value: String): PhoneNumber {
            require(isValidPhoneNumber(value)) { "Invalid phone number format: $value" }
            return PhoneNumber(value.replace("-", ""))
        }

        private fun isValidPhoneNumber(value: String): Boolean {
            return PHONE_REGEX_WITH_DASH.matches(value) || PHONE_REGEX_WITHOUT_DASH.matches(value)
        }
    }
}
