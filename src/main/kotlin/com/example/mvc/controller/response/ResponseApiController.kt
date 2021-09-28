package com.example.mvc.controller.response

import com.example.mvc.model.http.UserRequest
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/response")
class ResponseApiController {

    // ResponseEntity - 상황에 따라 Http 상태 코드 설정 가능

    // @RequestParam(required = false) 대신 ? 사용 가능
    @GetMapping
    fun getMapping(@RequestParam age: Int?): ResponseEntity<String> {
//        if (age == null) {
//            return ResponseEntity.status(400).body("age 값이 누락되었습니다.")
//        } else if (age < 0) {
//            return ResponseEntity.status(400).body("age는 0 이상이어야 합니다.")
//        }
//        return ResponseEntity.ok("OK")
//    }
        return age?.let {
            if (it < 20) {
                ResponseEntity.status(400).body("age는 0 이상이어야 합니다.")
            } else {
                ResponseEntity.ok("OK")
            }
        } ?: kotlin.run {
            ResponseEntity.status(400).body("age 값이 누락되었습니다.")
        }
    }

    @GetMapping("/{age}")
    fun getMappingPath(@PathVariable age: Int): Int {
        return age
    }

    // JSR-380 Bean Validation
    // @Vaild - 객체에 정의된 validation 적용
    @PostMapping
    fun postMapping(@Valid @RequestBody userRequest: UserRequest, bindingResult: BindingResult): ResponseEntity<Any> {
        // 500 에러
        if (bindingResult.hasErrors()) {
            val msg = StringBuilder()
            bindingResult.allErrors.forEach {
                val field = it as FieldError
                val message = it.defaultMessage
                msg.append("${field.field} : $message\n")
            }
            return ResponseEntity.badRequest().body(msg.toString())
        }
        // 기본값은 200
        return ResponseEntity.status(201).body(userRequest)
    }
}
