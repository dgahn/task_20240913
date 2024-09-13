package me.dgahn.core.employee.utils

import me.dgahn.core.employee.model.Employee
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile

@Component
class EmployeeParser(
    private val fileParsers: List<EmployeeFileParser>,
    private val stringParsers: List<EmployeeStringParser>,
) {
    fun parse(data: String, files: List<MultipartFile>): List<Employee> {
        return getEmployeesFromString(data) + getEmployeesFromFile(files)
    }

    private fun getEmployeesFromFile(files: List<MultipartFile>): List<Employee> {
        return fileParsers
            .flatMap { it.parse(files) }
            .also { employees ->
                if (files.isNotEmpty() && employees.isEmpty()) {
                    throw IllegalStateException("string data에서 데이터 추출을 실패하였습니다.")
                }
            }
    }

    private fun getEmployeesFromString(data: String): List<Employee> {
        return stringParsers
            .flatMap { it.parse(data) }
            .also { employees ->
                if (data.isNotBlank() && employees.isEmpty()) {
                    throw IllegalStateException("string data에서 데이터 추출을 실패하였습니다.")
                }
            }
    }
}
