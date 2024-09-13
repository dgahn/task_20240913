package me.dgahn.outbound.database.repository

import org.springframework.data.repository.PagingAndSortingRepository

interface EmployeePageRepository: PagingAndSortingRepository<EmployeeEntity, String>
