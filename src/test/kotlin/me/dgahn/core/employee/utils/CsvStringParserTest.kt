package me.dgahn.core.employee.utils

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import me.dgahn.core.employee.model.Email
import me.dgahn.core.employee.model.PhoneNumber
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CsvStringParserTest {
    private val parser = CsvStringParser()

    @Test
    fun `유효한 CSV 문자열로 Employee 리스트를 생성할 수 있다`() {
        val csvData = """
            김철수, charles@clovf.com, 01075312468, 2018-03-07
            박영희, matilda@clovf.com, 01087654321, 2021-04-28
            홍길동, kildong.hong@clovf.com, 01012345678, 2015.08.15
        """.trimIndent()

        val employees = parser.parse(csvData)

        employees.size shouldBe 3

        employees[0].apply {
            name shouldBe "김철수"
            email shouldBe Email.of("charles@clovf.com")
            tel shouldBe PhoneNumber.of("01075312468")
            joined shouldBe LocalDate.of(2018, 3, 7)
        }

        employees[1].apply {
            name shouldBe "박영희"
            email shouldBe Email.of("matilda@clovf.com")
            tel shouldBe PhoneNumber.of("01087654321")
            joined shouldBe LocalDate.of(2021, 4, 28)
        }

        employees[2].apply {
            name shouldBe "홍길동"
            email shouldBe Email.of("kildong.hong@clovf.com")
            tel shouldBe PhoneNumber.of("01012345678")
            joined shouldBe LocalDate.of(2015, 8, 15)
        }
    }

    @Test
    fun `유효하지 않은 CSV 문자열로 Employee 리스트를 생성하려고 빈리스트가 반환한다`() {
        val invalidCsvData = """
            김철수, charles@clovf.com, 01075312468
            박영희, matilda@clovf.com, 01087654321, 2021-04-28, extra
            홍길동, kildong.hong@clovf.com, 01012345678, 2015.08.15
        """.trimIndent()

        parser.parse(invalidCsvData) shouldHaveSize 0
    }

    @Test
    fun `빈 줄이 있는 CSV 문자열을 처리할 수 있다`() {
        val csvDataWithEmptyLines = """
            김철수, charles@clovf.com, 01075312468, 2018-03-07

            박영희, matilda@clovf.com, 01087654321, 2021-04-28

            홍길동, kildong.hong@clovf.com, 01012345678, 2015.08.15
        """.trimIndent()

        val employees = parser.parse(csvDataWithEmptyLines)

        employees.size shouldBe 3

        employees[0].apply {
            name shouldBe "김철수"
            email shouldBe Email.of("charles@clovf.com")
            tel shouldBe PhoneNumber.of("01075312468")
            joined shouldBe LocalDate.of(2018, 3, 7)
        }

        employees[1].apply {
            name shouldBe "박영희"
            email shouldBe Email.of("matilda@clovf.com")
            tel shouldBe PhoneNumber.of("01087654321")
            joined shouldBe LocalDate.of(2021, 4, 28)
        }

        employees[2].apply {
            name shouldBe "홍길동"
            email shouldBe Email.of("kildong.hong@clovf.com")
            tel shouldBe PhoneNumber.of("01012345678")
            joined shouldBe LocalDate.of(2015, 8, 15)
        }
    }
}
