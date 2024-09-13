package me.dgahn.outbound.database.repository

import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeJpaRepository : JpaRepository<EmployeeEntity, String> {

}
