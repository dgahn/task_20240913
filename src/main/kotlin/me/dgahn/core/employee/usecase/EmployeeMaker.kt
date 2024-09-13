package me.dgahn.core.employee.usecase

import me.dgahn.core.employee.model.Employee
import me.dgahn.core.employee.utils.EmployeeParser
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class EmployeeMaker(
    private val parser: EmployeeParser,
) {
    fun make(data: String, files: List<MultipartFile>): List<Employee> {
        TODO("Not yet implemented")
    }
}
