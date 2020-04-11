package com.gornostaevas.pushify.new_authorization

enum class AuthenticationResult {
    SUCCESS, FAIL
}

object AuthenticationRequest {
    const val networkType : String = "networkType"
}