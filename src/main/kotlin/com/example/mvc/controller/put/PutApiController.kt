package com.example.mvc.controller.put

import com.example.mvc.model.http.Result
import com.example.mvc.model.http.UserRequest
import com.example.mvc.model.http.UserResponse
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class PutApiController {

    @PutMapping("/put-mapping")
    fun putMapping(@RequestBody userRequest: UserRequest): UserResponse {
//        val result = Result("OK", "Success")
//        val description = "Put request"
//        val userList = mutableListOf<UserRequest>()
//        userList.add(userRequest)
//        return UserResponse(result, description, userList)

        return UserResponse().apply {
            result = Result().apply {
                resultCode = "OK"
                resultMessage = "Success"
            }

            description = "Put request"

            val userList = mutableListOf<UserRequest>()
            userList.add(userRequest)
            userRequests = userList
        }
    }
}
