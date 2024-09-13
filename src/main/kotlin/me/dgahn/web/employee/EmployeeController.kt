package me.dgahn.web.employee

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import me.dgahn.core.employee.usecase.EmployeeMaker
import me.dgahn.web.employee.dto.Response
import me.dgahn.web.employee.dto.toResponse
import me.dgahn.web.utils.toFiles
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping
@Tag(name = "긴급연락망 API", description = "긴급연락망을 관리하는 API")
class EmployeeController(
    private val maker: EmployeeMaker,
) {
    @PostMapping("/api/employee", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @Operation(
        summary = "긴급 연락망을 추가합니다.",
        description = "csv,json 데이터 또는 파일로 긴급연락망을 추가합니다.",
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "긴급 연락 추가에 성공하였습니다.",
                content = [Content(schema = Schema(implementation = Response::class))],
            ),
            ApiResponse(responseCode = "400", description = "Invalid input"),
        ],
    )
    fun create(
        @RequestPart("data", required = false)
        @Parameter(content = [Content(mediaType = "text/plain;utf-8")])
        data: String = "",
        @RequestPart("files", required = false)
        @Parameter(content = [Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)])
        files: List<MultipartFile> = emptyList(),
    ): ResponseEntity<List<Response>> {
        return ResponseEntity(maker.make(data, files.toFiles()).map { it.toResponse() }, HttpStatus.CREATED)
    }
}
