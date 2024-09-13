package me.dgahn.web.employee

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import me.dgahn.core.employee.query.EmployeeSearcher
import me.dgahn.core.employee.usecase.EmployeeMaker
import me.dgahn.web.employee.dto.Response
import me.dgahn.web.employee.dto.toResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping
@Tag(name = "긴급연락망 API", description = "긴급연락망을 관리하는 API")
class EmployeeController(
    private val maker: EmployeeMaker,
    private val searcher: EmployeeSearcher,
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
        return ResponseEntity(maker.make(data, files).map { it.toResponse() }, HttpStatus.CREATED)
    }

    @GetMapping("/api/employee")
    @Operation(
        summary = "긴급 연락망 목록을 조회",
        description = "긴급 연락망 목록을 조회합니다. page, size를 통해 원하는 만큼 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                content = [Content(schema = Schema(implementation = Response::class))],
            ),
            ApiResponse(responseCode = "400", description = "Invalid input"),
        ],
    )
    fun search(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "pageSize", defaultValue = "10") size: Int,
    ): ResponseEntity<Page<Response>> {
        val request = PageRequest.of(page, size)
        val found = searcher.search(request).map { it.toResponse() }
        return ResponseEntity.ok(PageImpl(found.toList(), request, found.totalElements))
    }

    @GetMapping("/api/employee/{name}")
    @Operation(
        summary = "이름으로 긴급 연락망 목록을 조회",
        description = "긴급 연락망 목록을 조회합니다. page, size를 통해 원하는 만큼 조회합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                content = [Content(schema = Schema(implementation = Response::class))],
            ),
            ApiResponse(responseCode = "400", description = "Invalid input"),
        ],
    )
    fun search(@PathVariable("name") name: String): ResponseEntity<Response> {
        return ResponseEntity.ok(searcher.search(name).toResponse())
    }
}
