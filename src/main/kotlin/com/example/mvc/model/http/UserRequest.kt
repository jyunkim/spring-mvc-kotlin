package com.example.mvc.model.http

data class UserRequest(
    var name: String,
    var age: Int,
    var address: String,
    var email: String
)