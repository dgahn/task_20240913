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

class JsonFileParserTest {
    private val jsonStringParser: JsonStringParser = mockk()
    private val jsonFileParser = JsonFileParser(jsonStringParser)

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
    fun `Json 파일을 읽어서 Employee 리스트를 반환한다`() {
        val mockFile = mockk<File>()
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
        val employees = listOf(
            Employee.of("김철수", "charles@clovf.com", "01075312468", "2018.03.07"),
            Employee.of("박영희", "matilda@clovf.com", "01087654321", "2021.04.28"),
            Employee.of("홍길동", "kildong.hong@clovf.com", "01012345678", "2015.08.15"),
        )

        every { mockFile.extension } returns "json"
        every { mockFile.readText() } returns jsonData
        every { jsonStringParser.parse(jsonData) } returns employees

        val actual = jsonFileParser.parse(mockFile)

        actual shouldBe employees
        verify { jsonStringParser.parse(jsonData) }
    }

    @Test
    fun `json 파일이 아닐 경우 빈 리스트를 반환한다`() {
        val mockFile = mockk<File>()

        every { mockFile.extension } returns "csv"

        val actual = jsonFileParser.parse(mockFile)

        actual shouldBe emptyList()
        verify(exactly = 0) { jsonStringParser.parse(any()) }
    }
}
