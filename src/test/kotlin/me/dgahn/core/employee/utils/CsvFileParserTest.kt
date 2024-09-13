package me.dgahn.core.employee.utils

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import me.dgahn.core.employee.model.Employee
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File

class CsvFileParserTest {
    private val csvStringParser: CsvStringParser = mockk()
    private val csvFileParser = CsvFileParser(csvStringParser)

    @BeforeEach
    fun setUp() {
        mockkStatic(File::extension)
        mockkStatic(File::readText)
    }

    @AfterEach
    fun cleanUp() {
        unmockkStatic(File::extension)
        unmockkStatic(File::readText)
    }

    @Test
    fun `CSV 파일을 읽어서 Employee 리스트를 반환한다`() {
        val mockFile = mockk<File>()
        val csvData = """
            김철수, charles@clovf.com, 01075312468, 2018.03.07
            박영희, matilda@clovf.com, 01087654321, 2021.04.28
            홍길동, kildong.hong@clovf.com, 01012345678, 2015.08.15
        """.trimIndent()
        val employees = listOf(
            Employee.of("김철수", "charles@clovf.com", "01075312468", "2018.03.07"),
            Employee.of("박영희", "matilda@clovf.com", "01087654321", "2021.04.28"),
            Employee.of("홍길동", "kildong.hong@clovf.com", "01012345678", "2015.08.15"),
        )

        every { mockFile.extension } returns "csv"
        every { mockFile.readText() } returns csvData
        every { csvStringParser.parse(csvData) } returns employees

        val actual = csvFileParser.parse(mockFile)

        actual shouldBe employees
        verify { csvStringParser.parse(csvData) }
    }

    @Test
    fun `csv 파일이 아닐 경우 빈 리스트를 반환한다`() {
        val mockFile = mockk<File>()

        every { mockFile.extension } returns "json"

        val actual = csvFileParser.parse(mockFile)

        actual shouldBe emptyList()
        verify(exactly = 0) { csvStringParser.parse(any()) }
    }
}
