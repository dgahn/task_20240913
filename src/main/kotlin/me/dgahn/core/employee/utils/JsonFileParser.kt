package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee
import org.springframework.stereotype.Component
import java.io.File

@Component
class JsonFileParser(
    private val jsonStringParser: JsonStringParser,
) : EmployeeFileParser {
    override fun parse(file: File): List<Employee> {
        if (file.extension != "json") {
            return emptyList()
        }
        return jsonStringParser.parse(file.readText())
    }
}
