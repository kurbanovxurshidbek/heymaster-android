package com.heymaster.heymaster.data.network

import com.heymaster.heymaster.model.*
import com.heymaster.heymaster.model.auth.ConfirmRequest
import com.heymaster.heymaster.model.auth.ConfirmResponse
import com.heymaster.heymaster.model.auth.LoginRequest
import com.heymaster.heymaster.model.auth.LoginResponse
import com.heymaster.heymaster.model.masterprofile.Portfolio
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("notification")
    suspend fun getNotification(): Response<List<Notification>>

    @GET("images")
    suspend fun getImages(): Response<List<Portfolio>>

    @GET("images/{id}")
    suspend fun getImage(@Path("id") id: Int): Response<Portfolio>

    @GET("services")
    suspend fun getServices(): Response<List<Service>>

    @GET("services/{id}")
    suspend fun getService(@Path("id") id : Int): Response<Service>

    @GET("services")
    suspend fun getPopularServices(): Response<List<Service>>

    @GET("services")
    suspend fun getPopularMasters(): Response<List<User>>

    @GET("ads")
    suspend fun getAds(): Response<List<Ads>>

    //Booking
    @GET("services")
    suspend fun getBookings(): Response<List<Service>>


    //Auth

    @POST("password")
    suspend fun login(@Body phoneNumber: LoginRequest): Response<LoginResponse>

    @POST("auth/login")
    suspend fun confirm(@Body confirmRequest: ConfirmRequest): Response<ConfirmResponse>

    @GET("auth/me")
    suspend fun currentUser(): Response<User>
    @POST("")
    suspend fun signUp(): Response<User>







}