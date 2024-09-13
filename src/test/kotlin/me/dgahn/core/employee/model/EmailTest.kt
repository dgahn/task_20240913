package me.dgahn.core.employee.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test

class EmailTest {
    @Test
    fun `이메일 형식이 아니면 예외가 발생한다`() {
        shouldThrow<IllegalArgumentException> { Email.of(value = "abc") }
        shouldThrow<IllegalArgumentException> { Email.of(value = "abc.com") }
        shouldThrow<IllegalArgumentException> { Email.of(value = "abc@.com") }
    }

    @Test
    fun `이메일 형식으로 이메일을 생성할 수 있다`() {
        Email.of("abc@abc.com").value shouldBe "abc@abc.com"
        Email.of("abc@clovf.com").value shouldBe "abc@clovf.com"
    }
}
