package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee
import org.springframework.stereotype.Component

@Component
class CsvStringParser : EmployeeStringParser {
    override fun parse(data: String): List<Employee> {
        return runCatching {
            data
                .lines()
                .filter { it.isNotBlank() }
                .map { line ->
                    val parts = line.split(",").map { it.trim() }
                    Employee.of(
                        name = parts[0],
                        email = parts[1],
                        tel = parts[2],
                        joined = parts[3],
                    )
                }
        }.getOrElse { emptyList() }
    }
}
