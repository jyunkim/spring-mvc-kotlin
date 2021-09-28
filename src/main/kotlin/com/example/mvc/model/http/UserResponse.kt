package com.example.mvc.model.http

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming

data class UserResponse(
    var result: Result? = null,
    var description: String? = null,

    @JsonProperty("user") // Json에서 사용할 필드 이름 지정
    var userRequests: MutableList<UserRequest>? = null
)

// Json 필드 이름 형식 전체 적용
//@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class Result(
    var resultCode: String? = null,
    var resultMessage: String? = null
)
