package com.example.mvc.controller.get

import com.example.mvc.model.http.UserRequest
import org.springframework.web.bind.annotation.*

@RestController // REST API로 동작
@RequestMapping("/api") // http://localhost:8080/api
class GetApiController {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello"
    }

    // 여러 메서드, 경로 지정 가능
    @RequestMapping(method = [RequestMethod.GET], path = ["/request-mapping", "/request"])
    fun requestMapping(): String {
        return "request-mapping"
    }

    // path variable이랑 이름이 다르면 value나 name으로 지정 가능
    @GetMapping("/get-mapping/{name}")
    fun pathVariable(@PathVariable(value = "name") _name: String): String {
        val name = "kotlin"
        return _name + name
    }

    // 쿼리 파라미터
    // uri에는 가독성을 위해 -을 쓰는게 좋음
    // 디폴트 - required=true
    @GetMapping("/get-mapping")
    fun queryParam(@RequestParam name: String, @RequestParam(name = "phone-number") phoneNumber: String): String {
        return name + phoneNumber
    }

    // 쿼리 파라미터가 많을 경우 객체로 받을 수 있음(@requestParam 필요 x)
    // @RestController를 사용하면 object mapper를 통해 객체를 json 형태로 변환하여 반환
    @GetMapping("/get-mapping/object")
    fun queryParamObject(userRequest: UserRequest): UserRequest {
        return userRequest
    }

    // 클래스에 정의된 필드 이름과 다른 이름의 키 값을 사용하고 싶은 경우 map을 사용
    @GetMapping("/get-mapping/map")
    fun queryParamMap(@RequestParam map: Map<String, Any>): Map<String, Any> {
        return map
    }
}
