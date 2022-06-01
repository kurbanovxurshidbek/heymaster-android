package com.heymaster.heymaster.utils

object Constants {

    private var IS_TESTER = false
    private val SERVER_DEVELOPMENT = "https://628a284ce5e5a9ad322183ca.mockapi.io"
    private val SERVER_PRODUCTION = "http://localhost:8080/"

    fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }


    val BASE_URL = "https://628a284ce5e5a9ad322183ca.mockapi.io"
 //   val BASE_URL = "https://628b684f667aea3a3e2eae0d.mockapi.io/api/"
}