package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee
import me.dgahn.web.utils.extension
import me.dgahn.web.utils.readText
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class CsvFileParser(
    private val csvStringParser: CsvStringParser,
) : EmployeeFileParser {
    override fun parse(file: MultipartFile): List<Employee> {
        if (file.extension() != "csv") {
            return emptyList()
        }
        return csvStringParser.parse(file.readText())
    }
}
