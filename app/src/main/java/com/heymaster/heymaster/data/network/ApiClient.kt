package com.heymaster.heymaster.data.network

import com.heymaster.heymaster.BuildConfig
import com.heymaster.heymaster.utils.Constants.BASE_URL
import com.heymaster.heymaster.utils.Constants.server
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

object ApiClient {

    private val client = buildClient()

    private val retrofit = buildRetrofit(client)

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    private fun buildClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
        try {
            builder.callTimeout(1, TimeUnit.MINUTES)
                .addNetworkInterceptor(Interceptor { chain ->
                    var request = chain.request()
                    val builder = request.newBuilder()
                    request = builder.build()
                    chain.proceed(request)
                })
        } catch (e: Exception) {
            when (e) {
                is SocketTimeoutException -> {
                    throw SocketTimeoutException()
                }
            }
        }

        if (BuildConfig.DEBUG) {
            // Debug holatdan keyin o`chirish kerak!!!!!!!!!!!!!!!!!!!!!!!
            builder.addInterceptor(interceptor)
            builder.addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        return builder.build()
    }

    fun <T> createServiceWithAuth(service: Class<T>?): T {
        val newClient =
            client.newBuilder().addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
//                builder.addHeader("Authorization", "APP_TOKEN")
//                builder.header("Content-Type", "application/json")
                chain.proceed(builder.build())
            })
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        /*.authenticator(CustomAuthenticator.getInstance(tokenManager)).build()*/
        val newRetrofit = retrofit.newBuilder().client(newClient).build()
        return newRetrofit.create(service!!)


    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }

}

