package me.dgahn.core.employee.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class PhoneNumberTest {
    @Test
    fun `유효한 폰번호를 생성할 수 있다`() {
        PhoneNumber.of("010-1234-1234").value shouldBe "01012341234"
        PhoneNumber.of("01012341234").value shouldBe "01012341234"
    }

    @Test
    fun `유효하지 않은 폰번호로 생성하려면 예외가 발생한다`() {
        shouldThrow<IllegalArgumentException> { PhoneNumber.of("010-1234-123") }
        shouldThrow<IllegalArgumentException> { PhoneNumber.of("020-1234-1234") }
        shouldThrow<IllegalArgumentException> { PhoneNumber.of("0101234-1234") }
        shouldThrow<IllegalArgumentException> { PhoneNumber.of("010-123-1234") }
        shouldThrow<IllegalArgumentException> { PhoneNumber.of("010-1234-12345") }
        shouldThrow<IllegalArgumentException> { PhoneNumber.of("123-4567-8901") }
    }
}
