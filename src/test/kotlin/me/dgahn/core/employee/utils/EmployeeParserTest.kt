package me.dgahn.core.employee.utils

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import io.mockk.verify
import me.dgahn.core.employee.model.Employee
import me.dgahn.web.utils.extension
import me.dgahn.web.utils.readText
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.multipart.MultipartFile

class EmployeeParserTest {
    private val mockFileParser = mockk<EmployeeFileParser>()
    private val mockStringParser = mockk<EmployeeStringParser>()
    private val employeeParser = EmployeeParser(
        fileParsers = listOf(mockFileParser),
        stringParsers = listOf(mockStringParser),
    )

    private val sampleEmployee1 = Employee.of("김클로", "clo@clovf.com", "010-1111-2424", "2012-01-05")
    private val sampleEmployee2 = Employee.of("박마블", "md@clovf.com", "010-3535-7979", "2013-07-01")
    private val sampleEmployee3 = Employee.of("홍커넥", "connect@clovf.com", "010-8531-7942", "2019-12-05")

    @BeforeEach
    fun setUp() {
        mockkStatic(MultipartFile::extension)
        mockkStatic(MultipartFile::readText)
    }

    @AfterEach
    fun cleanUp() {
        unmockkStatic(MultipartFile::extension)
        unmockkStatic(MultipartFile::readText)
    }

    @Test
    fun `문자열 데이터로부터 직원 객체를 올바르게 파싱할 수 있다`() {
        val data = """
            김클로, clo@clovf.com, 010-1111-2424, 2012-01-05
            박마블, md@clovf.com, 010-3535-7979, 2013-07-01
        """.trimIndent()
        every { mockStringParser.parse(data) } returns listOf(sampleEmployee1, sampleEmployee2)
        every { mockFileParser.parse(emptyList()) } returns emptyList()

        val employees = employeeParser.parse(data, emptyList())

        employees shouldBe listOf(sampleEmployee1, sampleEmployee2)
        verify { mockStringParser.parse(data) }
    }

    @Test
    fun `파일로부터 직원 객체를 올바르게 파싱할 수 있다`() {
        val file = mockk<MultipartFile>()
        every { file.extension() } returns "csv"
        every { mockFileParser.parse(listOf(file)) } returns listOf(sampleEmployee3)
        every { mockStringParser.parse("") } returns emptyList()

        val employees = employeeParser.parse("", listOf(file))

        employees shouldBe listOf(sampleEmployee3)
        verify { mockFileParser.parse(listOf(file)) }
    }

    @Test
    fun `파일이 존재하는데 파싱에 실패하면 예외가 발생해야 한다`() {
        val file = mockk<MultipartFile>()
        every { file.extension() } returns "csv"
        every { mockFileParser.parse(listOf(file)) } returns emptyList()
        every { mockStringParser.parse("") } returns emptyList()

        shouldThrow<IllegalStateException> {
            employeeParser.parse("", listOf(file))
        }
    }

    @Test
    fun `문자열 데이터가 존재하는데 파싱에 실패하면 예외가 발생해야 한다`() {
        val data = """
            김클로, clo@clovf.com, 010-1111-2424, 2012-01-05
        """.trimIndent()
        every { mockStringParser.parse(data) } returns emptyList()
        every { mockFileParser.parse(emptyList()) } returns emptyList()

        shouldThrow<IllegalStateException> {
            employeeParser.parse(data, emptyList())
        }
    }

    @Test
    fun `문자열 데이터와 파일 모두를 사용하여 직원 객체를 올바르게 파싱할 수 있다`() {
        val data = """
            김클로, clo@clovf.com, 010-1111-2424, 2012-01-05
        """.trimIndent()
        val file = mockk<MultipartFile>()
        every { file.extension() } returns "csv"
        every { mockFileParser.parse(listOf(file)) } returns listOf(sampleEmployee3)
        every { mockStringParser.parse(data) } returns listOf(sampleEmployee1)

        val employees = employeeParser.parse(data, listOf(file))

        employees shouldBe listOf(sampleEmployee1, sampleEmployee3)
        verify { mockFileParser.parse(listOf(file)) }
        verify { mockStringParser.parse(data) }
    }
}
