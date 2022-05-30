package com.heymaster.heymaster.data.network

import android.provider.ContactsContract
import com.heymaster.heymaster.model.Ads
import com.heymaster.heymaster.model.Notification
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.User
import com.heymaster.heymaster.model.masterprofile.Portfolio
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
    @POST("")
    suspend fun login(): Response<Any>

    @POST("")
    suspend fun confirm(): Response<Boolean>

    @POST("")
    suspend fun signUp(): Response<User>







}