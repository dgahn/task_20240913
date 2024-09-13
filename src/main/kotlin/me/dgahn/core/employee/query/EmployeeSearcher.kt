package me.dgahn.core.employee.query

import me.dgahn.core.employee.model.Employee
import me.dgahn.outbound.database.repository.EmployeePageRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeSearcher(
    private val employeePageRepository: EmployeePageRepository
) {
    @Transactional(readOnly = true)
    fun search(request: Pageable): Page<Employee> {
        val found = employeePageRepository.findAll(request).map { it.toDomain() }
        return PageImpl(found.toList(), request, found.totalElements)
    }
}
