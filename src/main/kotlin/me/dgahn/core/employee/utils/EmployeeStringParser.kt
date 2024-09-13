package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee

interface EmployeeStringParser {
    fun parse(data: String): List<Employee>
}
