package me.dgahn.outbound.database.repository

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import me.dgahn.core.employee.model.Employee
import java.time.LocalDate

@Entity
class EmployeeEntity(
    @Id
    @Column(name = "employee_id")
    val name: String,
    @Column(name = "phone_number")
    val tel: String,
    @Column(name = "email")
    val email: String,
    @Column(name = "joined_date")
    val joinedDate: LocalDate,
) {
    fun toEmployee(): Employee {
        return Employee.of(
            name = this.name,
            email = this.email,
            tel = this.tel,
            joined = this.joinedDate,
        )
    }
}

fun Employee.toEntity(): EmployeeEntity {
    return EmployeeEntity(
        name = this.name,
        tel = this.tel.value,
        email = this.email.value,
        joinedDate = this.joined,
    )
}
