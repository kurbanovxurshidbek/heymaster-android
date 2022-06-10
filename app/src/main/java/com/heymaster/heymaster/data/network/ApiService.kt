package com.heymaster.heymaster.data.network

import com.heymaster.heymaster.model.*
import com.heymaster.heymaster.model.auth.*
import com.heymaster.heymaster.model.masterprofile.Portfolio
import retrofit2.Response
import retrofit2.http.*
//ApiService
interface ApiService {

    @GET("notification")
    suspend fun getNotification(): Response<List<Notification>>

    //getting images
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
    suspend fun getPopularMasters(): Response<List<CurrentUser>>

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

    @POST("auth/client/register")
    suspend fun clientRegister(@Body clientRegisterRequest: ClientRegisterRequest): Response<ClientRegisterResponse>

    @POST("auth/master/register")
    suspend fun masterRegister(@Body masterRegisterRequest: MasterRegisterRequest): Response<MasterRegisterResponse>

    @GET("district/all")
    suspend fun getDistricts(): Response<List<District>>

    // client home page




    //Client
    @GET("auth/me")
    suspend fun currentUser(): Response<CurrentUser>









}