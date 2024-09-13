package me.dgahn.core.employee.utils

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import java.time.LocalDate

class JsonStringParserTest {
    private val objectMapper = ObjectMapper()
    private val parser = JsonStringParser(objectMapper)

    @Test
    fun `유효한 JSON 데이터로 Employee 리스트를 생성할 수 있다`() {
        val jsonData = """
        [
            {
                "name": "김클로",
                "email": "clo@clovf.com",
                "tel": "010-1111-2424",
                "joined": "2012-01-05"
            },
            {
                "name": "박마블",
                "email": "md@clovf.com",
                "tel": "010-3535-7979",
                "joined": "2013-07-01"
            },
            {
                "name": "홍커넥",
                "email": "connect@clovf.com",
                "tel": "010-8531-7942",
                "joined": "2019-12-05"
            }
        ]
        """.trimIndent()

        val employees = parser.parse(jsonData)

        employees.size shouldBe 3

        employees[0].name shouldBe "김클로"
        employees[0].email.value shouldBe "clo@clovf.com"
        employees[0].tel.value shouldBe "01011112424"
        employees[0].joined shouldBe LocalDate.of(2012, 1, 5)

        employees[1].name shouldBe "박마블"
        employees[1].email.value shouldBe "md@clovf.com"
        employees[1].tel.value shouldBe "01035357979"
        employees[1].joined shouldBe LocalDate.of(2013, 7, 1)

        employees[2].name shouldBe "홍커넥"
        employees[2].email.value shouldBe "connect@clovf.com"
        employees[2].tel.value shouldBe "01085317942"
        employees[2].joined shouldBe LocalDate.of(2019, 12, 5)
    }
}
