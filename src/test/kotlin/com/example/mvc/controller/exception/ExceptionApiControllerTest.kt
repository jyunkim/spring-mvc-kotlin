package com.example.mvc.controller.exception

import com.example.mvc.model.http.UserRequest
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.util.LinkedMultiValueMap

@WebMvcTest // 전체 통합이 아닌 웹 관련 부분만 가지고 테스트
@AutoConfigureMockMvc
class ExceptionApiControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc // 가상의 요청을 만들 수 있음

    @Test
    fun getTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "kim")
        queryParams.add("age", "25")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception").queryParams(queryParams)
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        ).andExpect(
            MockMvcResultMatchers.content().string("kim 25")
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun getFailTest() {
        val queryParams = LinkedMultiValueMap<String, String>()
        queryParams.add("name", "kim")
        queryParams.add("age", "0")

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/exception").queryParams(queryParams)
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest
        ).andExpect(
            // \$: Json의 루트 위치
            MockMvcResultMatchers.jsonPath("\$.resultCode").value("Fail")
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun postTest() {
        val userRequest = UserRequest().apply {
            name = "kim"
            age = 25
            phoneNumber = "010-1234-1234"
            email = "asdf@naver.com"
            createdAt = "2021-09-29 12:23:33"
        }

        // 객체를 json string으로 변환
        val json = jacksonObjectMapper().writeValueAsString(userRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/exception")
                .content(json) // String으로 변환 필요
                .contentType("application/json")
        ).andExpect(
            MockMvcResultMatchers.status().isOk
        ).andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun postFailTest() {
        val userRequest = UserRequest().apply {
            name = "kim"
            age = 25
            phoneNumber = "010-1234-1234"
            email = "asdf@naver.com"
            createdAt = "2021-9-29 12:23:33"
        }

        // 객체를 json string으로 변환
        val json = jacksonObjectMapper().writeValueAsString(userRequest)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/exception")
                .content(json) // String으로 변환 필요
                .contentType("application/json")
        ).andExpect(
            MockMvcResultMatchers.status().isBadRequest
        ).andDo(MockMvcResultHandlers.print())
    }
}