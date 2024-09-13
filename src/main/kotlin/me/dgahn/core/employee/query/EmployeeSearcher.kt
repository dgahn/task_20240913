package me.dgahn.core.employee.query

import me.dgahn.core.employee.model.Employee
import me.dgahn.outbound.database.repository.EmployeeEntity
import me.dgahn.outbound.database.repository.EmployeeJpaRepository
import me.dgahn.outbound.database.repository.EmployeePageRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EmployeeSearcher(
    private val employeePageRepository: EmployeePageRepository,
    private val employeeJpaRepository: EmployeeJpaRepository,
) {
    @Transactional(readOnly = true)
    fun search(request: Pageable): Page<Employee> {
        val found = employeePageRepository.findAll(request).map { it.toDomain() }
        return PageImpl(found.toList(), request, found.totalElements)
    }

    fun search(name: String): Employee {
        return employeeJpaRepository.findByIdOrNull(name)?.toDomain()
            ?: throw IllegalArgumentException("긴급 연락망에 없는 이름입니다. $name")
    }
}
