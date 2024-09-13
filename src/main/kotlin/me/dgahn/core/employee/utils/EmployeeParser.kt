package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee
import org.springframework.stereotype.Component
import java.io.File

@Component
class EmployeeParser(
    private val fileParsers: List<EmployeeFileParser>,
    private val stringParsers: List<EmployeeStringParser>,
) {
    fun parse(data: String, files: List<File>): List<Employee> {
        return stringParsers.flatMap { it.parse(data) } + fileParsers.flatMap { it.parse(files) }
    }
}
