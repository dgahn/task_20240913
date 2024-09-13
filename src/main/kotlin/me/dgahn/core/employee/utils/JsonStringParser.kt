package me.dgahn.core.employee.utils

import com.fasterxml.jackson.databind.ObjectMapper
import me.dgahn.core.employee.model.Employee
import org.springframework.stereotype.Component

@Component
class JsonStringParser(
    private val objectMapper: ObjectMapper,
) : EmployeeStringParser {
    override fun parse(data: String): List<Employee> {
        return runCatching {
            val rootNode = objectMapper.readTree(data)
            rootNode.map { jsonNode ->
                Employee.of(
                    name = jsonNode.get("name").asText(),
                    email = jsonNode.get("email").asText(),
                    tel = jsonNode.get("tel").asText(),
                    joined = jsonNode.get("joined").asText(),
                )
            }
        }.getOrElse { emptyList() }
    }
}
