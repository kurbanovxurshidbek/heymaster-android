package com.heymaster.heymaster.utils

object Constants {


    const val KEY_INTRO_SAVED = "intro_done"
    const val KEY_LOGIN_SAVED = "login_done"
    const val KEY_ACCESS_TOKEN = "access_token"

    val TEST = "https://628a284ce5e5a9ad322183ca.mockapi.io"


    private var IS_TESTER = true
    private val SERVER_DEVELOPMENT = "https://hey-masterrr.herokuapp.com/api/"
    private val SERVER_PRODUCTION = "https://hey-masterrr.herokuapp.com/api/"

    fun server(): String {
        if (IS_TESTER) return SERVER_DEVELOPMENT
        return SERVER_PRODUCTION
    }


}