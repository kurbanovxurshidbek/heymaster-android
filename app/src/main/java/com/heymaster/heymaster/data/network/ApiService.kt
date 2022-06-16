package com.heymaster.heymaster.data.network

import com.heymaster.heymaster.model.*
import com.heymaster.heymaster.model.auth.*
import com.heymaster.heymaster.model.auth.Object
import com.heymaster.heymaster.model.booking.BookingResponse
import com.heymaster.heymaster.model.home.Advertising
import com.heymaster.heymaster.model.home.HomeResponse
import com.heymaster.heymaster.model.masterdetailpage.MasterDetail
import com.heymaster.heymaster.model.masterprofile.Portfolio
import retrofit2.Call
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



    //Booking
    @GET("services")
    suspend fun getBookings(): Response<List<Service>>

    @POST("booking/{id}")
    suspend fun booking(@Path("id") id: Int): Response<BookingResponse>


    //Auth
    @POST("password")
    suspend fun login(@Body phoneNumber: LoginRequest): Response<LoginResponse>

    @POST("auth/login")
    suspend fun confirm(@Body confirmRequest: ConfirmRequest): Response<ConfirmResponse>

    @POST("auth/client/register")
    suspend fun clientRegister(@Body clientRegisterRequest: ClientRegisterRequest): Response<ClientRegisterResponse>

    @POST("auth/master/register")
    suspend fun masterRegister(@Body masterRegisterRequest: MasterRegisterRequest): Response<MasterRegisterResponse>

    @GET("district/region/{id}")
    suspend fun getDistrictsFromRegion(@Path("id") id: Int): Response<List<District>>

    @GET("region/all")
    suspend fun getRegions(): Response<List<Region>>

    @GET("profession/getAllActive")
    suspend fun getProfessions(): Response<Profession>


    // client home page
    @GET("advertising/all")
    suspend fun getAds(): Response<Advertising>

    @GET("home")
    suspend fun getHome(): Response<HomeResponse>

    @GET("profession/category/{id}")
    suspend fun getProfessionFromCategory(@Path("id") id: String): Response<List<Object>>




    //Client
    @GET("auth/me")
    suspend fun currentUser(): Response<CurrentUser>


    //Master
    @GET("master/{id}")
    suspend fun getMasterInfo(@Path("id") id: Int): Response<MasterDetail>










}