package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee
import org.springframework.web.multipart.MultipartFile

interface EmployeeFileParser {
    fun parse(file: MultipartFile): List<Employee>

    fun parse(files: List<MultipartFile>): List<Employee> {
        return files.flatMap { parse(it) }
    }
}
