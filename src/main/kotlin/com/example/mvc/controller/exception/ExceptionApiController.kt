package com.example.mvc.controller.exception

import com.example.mvc.model.http.UserRequest
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@RestController
@RequestMapping("/api/exception")
@Validated
class ExceptionApiController {


    @GetMapping
    fun get(
        @NotBlank
        @Size(min = 2, max = 8)
        @RequestParam name: String,

        @Min(value = 10)
        @RequestParam age: Int
    ): String {
        return name + Int
    }

    @PostMapping
    fun post(@Valid @RequestBody userRequest: UserRequest): UserRequest {
        return userRequest
    }
}
