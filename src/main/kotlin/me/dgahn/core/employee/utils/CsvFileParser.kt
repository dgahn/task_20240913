package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee
import org.springframework.stereotype.Component
import java.io.File

@Component
class CsvFileParser(
    private val csvStringParser: CsvStringParser,
) : EmployeeFileParser {
    override fun parse(file: File): List<Employee> {
        if (file.extension != "csv") {
            return emptyList()
        }
        return csvStringParser.parse(file.readText())
    }
}
