package com.interview.whoptest.domain.repository

import arrow.core.Either
import com.interview.whoptest.domain.model.NetworkError
import com.interview.whoptest.domain.model.Notification
import retrofit2.Response

interface NotificationsRepository {

    suspend fun getNotifications(): Either<NetworkError, List<Notification>>

    suspend fun markAsReadNotification(notificationId: String, notification: Notification): Either<NetworkError, Response<Unit>>
}