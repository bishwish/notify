package com.interview.whoptest.data.repository

import arrow.core.Either
import com.interview.whoptest.data.mapper.toGeneralError
import com.interview.whoptest.data.remote.NotificationsApi
import com.interview.whoptest.domain.model.NetworkError
import com.interview.whoptest.domain.model.Notification
import com.interview.whoptest.domain.repository.NotificationsRepository
import retrofit2.Response
import javax.inject.Inject

class NotificationsRepositoryImpl @Inject constructor(
    private val notificationsApi: NotificationsApi
) : NotificationsRepository {
    override suspend fun getNotifications(): Either<NetworkError, List<Notification>> {
        return Either.catch {
            notificationsApi.getNotifications()
        }.mapLeft { it.toGeneralError() }
    }

    override suspend fun markAsReadNotification(notificationId: String, notification: Notification): Either<NetworkError, Response<Unit>> {
        return Either.catch {
            notificationsApi.markAsReadNotification(notificationId, notification)
        }.mapLeft { it.toGeneralError() }
    }
}