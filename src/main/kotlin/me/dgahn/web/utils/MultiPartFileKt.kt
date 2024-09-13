package me.dgahn.web.utils

import org.springframework.web.multipart.MultipartFile
import java.io.File

fun MultipartFile.toFile(): File {
    val file = File(this.originalFilename ?: throw IllegalArgumentException("파일 이름이 존재하지 않습니다."))
    this.transferTo(file)
    return file
}

fun List<MultipartFile>.toFiles(): List<File> = this.map { it.toFile() }
