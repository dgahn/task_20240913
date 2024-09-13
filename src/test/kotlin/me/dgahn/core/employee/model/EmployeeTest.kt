package me.dgahn.core.employee.model

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.format.DateTimeParseException

class EmployeeTest {
    @Test
    fun `유효한 날짜 문자열로 Employee 객체를 생성할 수 있다`() {
        val employee1 = Employee.of(
            name = "홍길동",
            email = "example@example.com",
            tel = "010-1234-5678",
            joined = "2015.08.15",
        )
        employee1.name shouldBe "홍길동"
        employee1.email.value shouldBe "example@example.com"
        employee1.tel.value shouldBe "01012345678"
        employee1.joined shouldBe LocalDate.of(2015, 8, 15)

        val employee2 = Employee.of(
            name = "김철수",
            email = "example@example.com",
            tel = "010-1234-5678",
            joined = "2015.08.15",
        )
        employee2.name shouldBe "김철수"
        employee2.email.value shouldBe "example@example.com"
        employee2.tel.value shouldBe "01012345678"
        employee2.joined shouldBe LocalDate.of(2015, 8, 15)
    }

    @Test
    fun `유효하지 않은 날짜 문자열로 Employee 객체를 생성하려고 하면 예외가 발생한다`() {
        shouldThrow<IllegalArgumentException> {
            Employee.of(
                name = "이영희",
                email = "example@example.com",
                tel = "010-1234-5678",
                joined = "2018/03/07",
            )
        }
        shouldThrow<IllegalArgumentException> {
            Employee.of(
                name = "이영희",
                email = "example@example.com",
                tel = "010-1234-5678",
                joined = "20180307",
            )
        }
        shouldThrow<DateTimeParseException> {
            Employee.of(
                name = "이영희",
                email = "example@example.com",
                tel = "010-1234-5678",
                joined = "2018-13-07",
            )
        }
        shouldThrow<DateTimeParseException> {
            Employee.of(
                name = "이영희",
                email = "example@example.com",
                tel = "010-1234-5678",
                joined = "2018-03-32",
            )
        }
    }
}
