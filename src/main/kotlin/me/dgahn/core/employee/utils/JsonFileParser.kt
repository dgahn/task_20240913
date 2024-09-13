package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee
import me.dgahn.web.utils.extension
import me.dgahn.web.utils.readText
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class JsonFileParser(
    private val jsonStringParser: JsonStringParser,
) : EmployeeFileParser {
    override fun parse(file: MultipartFile): List<Employee> {
        if (file.extension() != "json") {
            return emptyList()
        }
        return jsonStringParser.parse(file.readText())
    }
}
