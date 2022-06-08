package com.heymaster.heymaster.data.network

import com.heymaster.heymaster.BuildConfig
import com.heymaster.heymaster.utils.Constants.TEST
import com.heymaster.heymaster.utils.Constants.server
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException
import java.util.concurrent.TimeUnit

object ApiClient {

    private val client = getClient()

    private val retrofit = buildRetrofit(client)

    private fun buildRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(TEST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun getClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout( 60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        //   .addInterceptor(ChuckInterceptor(context))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .addInterceptor(Interceptor { chain ->
            val builder = chain.request().newBuilder()
            builder.header("Content-Type", "application/json")
            builder.header("Accept", "application/json")
            chain.proceed(builder.build())
        })
        .build()

    private fun buildClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val builder = OkHttpClient.Builder()
        try {
            builder.connectTimeout(60,TimeUnit.SECONDS)
            .readTimeout(60,TimeUnit.SECONDS)
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

    fun <T> createServiceWithAuth(service: Class<T>?, token: String): T {
        val newClient =
            client.newBuilder().addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Authorization", "Bearer $token")
                builder.header("Content-Type", "application/json")
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

