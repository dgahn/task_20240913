package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee
import java.io.File

interface EmployeeFileParser {
    fun parse(file: File): List<Employee>

    fun parse(files: List<File>): List<Employee> {
        return files.flatMap { parse(it) }
    }
}
