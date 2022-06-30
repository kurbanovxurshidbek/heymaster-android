package com.heymaster.heymaster.data.network

import com.heymaster.heymaster.model.*
import com.heymaster.heymaster.model.District
import com.heymaster.heymaster.model.Region
import com.heymaster.heymaster.model.auth.*
import com.heymaster.heymaster.model.auth.Object
import com.heymaster.heymaster.model.auth.Profession
import com.heymaster.heymaster.model.booking.*
import com.heymaster.heymaster.model.editmasterprofile.EditMasterRequest
import com.heymaster.heymaster.model.editmasterprofile.EditMasterResponse
import com.heymaster.heymaster.model.home.*
import com.heymaster.heymaster.model.masterdetail.MasterDetail
import com.heymaster.heymaster.model.masterprofile.MasterToClientResponse
import com.heymaster.heymaster.model.masterprofile.Portfolio
import okhttp3.RequestBody
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

    @GET("booking/client")
    suspend fun getClientActiveBooking(): Response<ClientActiveBooking>

    @GET("booking/{id}")
    suspend fun getBookingById(): Response<BookingResponse>

    @GET("booking/master")
    suspend fun getMasterActiveBooking(): Response<MasterActiveBooking>

    @GET("booking/finish/{id}")
    suspend fun bookingFinish(@Path("id") id: Int)

    @GET("booking/master/history")
    suspend fun getBookingMasterHistory(): Response<MasterHistoryBooking>

    @GET("booking/client/history")
    suspend fun getBookingClientHistory(): Response<MasterHistoryBooking>

    @POST("booking/accept")
    suspend fun bookingAcceptOrCancel(@Body bookingAcceptRequest: BookingAcceptRequest): Response<BookingAcceptResponse>



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

    @POST("master/to/client")
    suspend fun masterToClient(): Response<MasterToClientResponse>

    @POST("user/to/master")
    suspend fun clientToMaster(@Body clientToMasterRequest: ClientToMasterRequest): Response<ClientToMasterResponse>

    @GET("master/roleToMaster")
    suspend fun clientToMasterIsAlreadyMaster() :Response<ClientToMasterResponse>


    // client home page
    @GET("advertising/all")
    suspend fun getAds(): Response<Advertising>

    @GET("home")
    suspend fun getHome(): Response<HomeResponse>

    @GET("category/all")
    suspend fun getAllCategory(): Response<CategoryResponse>

    @GET("profession/category/{id}")
    suspend fun getProfessionFromCategory(@Path("id") id: String): Response<List<Object>>


    @POST("attachment/upload")
    suspend fun uploadAttachment(@Body body: RequestBody): Response<Any>

    @POST("attachment/upload/profilePhoto")
    suspend fun uploadProfilePhoto(@Body body: RequestBody): Response<Any>

    @GET("attachment/info")
    suspend fun attachmentInfo(): Response<List<AttachmentInfo>>


    @GET("master/{id}")
    suspend fun getMasterById(@Path("id") id: Int): Response<MasterResponse>



    //Client
    @GET("auth/me")
    suspend fun currentUser(): Response<CurrentUser>

    //Master
    @GET("master/{id}")
    suspend fun getMasterDetailInfo(@Path("id") id: Int):Response<MasterDetail>

    @GET("master/allActive")
    suspend fun getActiveMasters(): Response<ActiveMasters>

    @GET("profession/findMastersByProfessionId/{id}")
    suspend fun getMasterFromProfession(@Path("id") id: Int): Response<MastersResponse>

    @GET("master/deleteAttPhoto/{id}")
    suspend fun deleteMasterAttachment(@Path("id") id: Int)


    //Master Profile Edit
    @PUT("master/edit")
    suspend fun editMasterProfile(@Body editMasterRequest: EditMasterRequest): Response<EditMasterResponse>






    //Search
    @GET("master/search/{name}")
    suspend fun searchMasterWithName(@Path("name") name: String)

    @GET("master/search/region/{id}")
    suspend fun searchMasterWithRegion(@Path("id") id: Int)

    @GET("master/search/district/{id}")
    suspend fun searchMasterWithDistrict(@Path("id") id: Int)

    @GET("profession/search/{name}")
    suspend fun searchProfessionWithName(@Path("name") name: String)

    @GET("category/search/{name}")
    suspend fun searchCategoryWithName(@Path("name") name: String)





    //Rating
    @POST("rate")
    suspend fun rate()

    @GET("rate/master")
    suspend fun getMasterRate()

    @GET("rate/getOne")
    suspend fun getMasterRateOne()


    //notification
    @GET("notification/user/all")
    suspend fun getClientNotifications()














}