package me.dgahn.core.employee.query

import me.dgahn.core.employee.model.Employee
import me.dgahn.outbound.database.repository.EmployeeJpaRepository
import me.dgahn.outbound.database.repository.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeCreator(
    private val employeeJpaRepository: EmployeeJpaRepository,
) {
    @Transactional
    fun create(employees: List<Employee>): List<Employee> {
        return employeeJpaRepository
            .saveAll(employees.map { it.toEntity() })
            .map { it.toEmployee() }
    }
}
