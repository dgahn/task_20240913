package me.dgahn.web.employee.dto

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import me.dgahn.core.employee.model.Employee
import java.time.LocalDate

data class Response(
    @Schema(description = "이름", example = "김철수")
    val name: String,
    @Schema(description = "이메일", example = "charles@clovf.com")
    val email: String,
    @Schema(description = "폰번호", example = "01075312468")
    val tel: String,
    @Schema(description = "입사일", example = "2018-03-07")
    @JsonFormat(pattern = "yyyy-MM-dd")
    val joined: LocalDate,
)

fun Employee.toResponse() = Response(
    name = name,
    email = email.value,
    tel = tel.value,
    joined = joined,
)
