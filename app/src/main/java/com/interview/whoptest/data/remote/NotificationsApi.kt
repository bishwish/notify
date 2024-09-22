package com.interview.whoptest.data.remote

import com.interview.whoptest.domain.model.Notification
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface NotificationsApi {
    @GET("notifications")
    suspend fun getNotifications(): List<Notification>

    @PUT("notifications/{id}")
    suspend fun markAsReadNotification(@Path("id")notificationId: String, @Body notification: Notification) : Response<Unit>
}