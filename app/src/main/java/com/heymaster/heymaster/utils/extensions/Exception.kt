package com.heymaster.heymaster.utils.extensions

import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

fun Throwable.userMessage() = when (this) {
    is HttpException -> when (this.code()) {
        304 -> "304 Not Modified"
        400 -> "400 Bad Request"
        401 -> "401 Unauthorized"
        403 -> "403 Forbidden"
        404 -> "404 Not Found"
        405 -> "405 Method Not Allowed"
        409 -> "Драйвер не найден"
        422 -> "422 Unprocessable"
        500 -> "500 Server Error"
        else -> "Something went wrong"
    }
    is IOException -> "Internet bilan aloqa yo'q"
    is JsonSyntaxException -> "Ma'lumot olishda xatolik"
    is ConnectException -> "Internet bilan aloqa yo'q"
    else -> "Unknown error"
}