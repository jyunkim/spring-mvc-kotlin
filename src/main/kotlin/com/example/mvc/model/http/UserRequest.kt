package com.example.mvc.model.http

import com.example.mvc.annotation.StringFormatDateTime
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.constraints.*

data class UserRequest(
    // 코틀린에서는 field:를 붙여줘야 함
    @field:NotEmpty
    @field:Size(min = 2, max = 8)
    var name: String? = null,

    @field:PositiveOrZero
    var age: Int? = null,

    @field:Email
    var email: String? = null,

    @field:Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}\$")
    var phoneNumber: String? = null,

    @field:StringFormatDateTime
    var createdAt: String? = null
) {
    // 커스텀 validation 메서드 적용
    // 일반적으로 커스텀 애노테이션을 사용
//    @AssertTrue(message = "생성 일자는 yyyy-MM-dd HH:mm:ss 형식이어야 합니다.")
//    private fun isValidCreatedAt(): Boolean {
//        return try {
//            LocalDateTime.parse(createdAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//            true
//        } catch (e: Exception) {
//            false
//        }
//    }
}
