package me.dgahn.web.common

data class PageResponse<T>(
    val currentPage: Int,
    val totalSize: Long,
    val content: List<T>,
)
