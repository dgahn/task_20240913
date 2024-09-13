package me.dgahn.core.employee.usecase

import me.dgahn.core.employee.model.Employee
import me.dgahn.core.employee.query.EmployeeCreator
import me.dgahn.core.employee.utils.EmployeeParser
import org.springframework.stereotype.Service
import java.io.File

@Service
class EmployeeMaker(
    private val parser: EmployeeParser,
    private val employeeCreator: EmployeeCreator,
) {
    fun make(data: String, files: List<File>): List<Employee> {
        val employees = parser.parse(data, files)
        return employeeCreator.create(employees)
    }
}
