package me.dgahn

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(info = Info(title = "My API", version = "v1", description = "My API Description"))
@SpringBootApplication
class PhoneBookApplication

fun main() {
    runApplication<PhoneBookApplication>()
}
