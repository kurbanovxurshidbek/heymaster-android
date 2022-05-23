package com.heymaster.heymaster.data.network

import com.heymaster.heymaster.model.Ads
import com.heymaster.heymaster.model.Service
import com.heymaster.heymaster.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

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






}