package me.dgahn.web.utils

import org.springframework.web.multipart.MultipartFile

fun MultipartFile.extension(): String = this.originalFilename?.substringAfterLast('.')
    ?: this.contentType?.substringAfterLast('/')
    ?: throw IllegalArgumentException("확장자를 알 수 없습니다.")

fun MultipartFile.readText(): String = String(this.bytes)
