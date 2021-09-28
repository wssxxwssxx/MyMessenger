package com.example.messenger.service

import com.example.messenger.data.remote.request.LoginRequestObject
import com.example.messenger.data.remote.request.MessageRequestObject
import com.example.messenger.data.remote.request.StatusUpdateRequestBody
import com.example.messenger.data.remote.request.UserRequestObject
import com.example.messenger.data.vo.ConversationVO
import com.example.messenger.data.vo.MessageVO
import com.example.messenger.data.vo.UserVo
import com.example.messenger.data.vo.list.ConversationListVO
import com.example.messenger.data.vo.list.UsersListVO
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MessangerApiService {

    @POST("login")
    @Headers("Content-Type: application/json")
    fun login(@Body user: LoginRequestObject): Observable<retrofit2.Response<ResponseBody>>

    @POST("users/registrations")
    fun createUser(@Body user: UserRequestObject): Observable<UserVo>

    @GET("users")
    fun listUsers(@Header("Authorization") authorization: String): Observable<UsersListVO>

    @PUT("users")
    fun updateUserStatus(
        @Body request: StatusUpdateRequestBody,
        @Header("Authorization") authorization: String): Observable<UserVo>

    @GET("users/{userId}")
    fun showUser(
        @Path("userId") userId: Long,
        @Header("Authorization") authorization: String): Observable<UserVo>

    @GET("users/details")
    fun echoDetails(@Header("Authorization") authorization: String): Observable<UserVo>


    @POST("messages")
    fun createMessage(
        @Body messageRequestObject: MessageRequestObject,
        @Header("Authorization") authorization: String): Observable<MessageVO>

    @GET("conversations")
    fun listConversations(@Header("Authorization") authorization: String): Observable<ConversationListVO>

    @GET("conversations/{conversationId}")
    fun showConversation(
        @Path("conversationId") conversationId: Long,
        @Header("Authorization") authorization: String): Observable<ConversationVO>

    companion object Factory {

        private var service: MessangerApiService? = null

        fun getInstance(): MessangerApiService {
            if (service == null) {

                val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://192.168.10.3:5000")
                    .build()

                service = retrofit.create(MessangerApiService::class.java)
            }

            return service as MessangerApiService
        }
    }

}
