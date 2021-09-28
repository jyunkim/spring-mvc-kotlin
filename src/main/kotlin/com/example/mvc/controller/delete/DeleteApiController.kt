package com.example.mvc.controller.delete

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.Min

@RestController
@RequestMapping("/api")
@Validated // 컨트롤러 전체에 적용(일반적으로는 dto에 지정)
class DeleteApiController {

    // GET과 동일한 방식
    // 여기선 DB가 없으므로 실제 동작 x

    // 유효성 검사에 걸리면 500 에러
    @DeleteMapping("/delete-mapping")
    fun deleteMapping(@RequestParam name: String,
                      @RequestParam
                      @Min(value = 20, message = "age는 20보다 커야 합니다.")
                      age: Int) :String {
        return name + age
    }

    @DeleteMapping("/delete-mapping/user/{id}")
    fun deleteMappingPath(@PathVariable id: Long): Long {
        return id
    }
}
