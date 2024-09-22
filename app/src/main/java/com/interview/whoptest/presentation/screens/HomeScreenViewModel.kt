package com.interview.whoptest.presentation.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.interview.whoptest.domain.model.Notification
import com.interview.whoptest.domain.repository.NotificationsRepository
import com.interview.whoptest.utils.Event
import com.interview.whoptest.utils.sendEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val notificationsRepository: NotificationsRepository
) : ViewModel() {
    private val _state = MutableStateFlow(HomeScreenViewState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(),
        HomeScreenViewState()
    )

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            notificationsRepository.getNotifications()
                .onRight { notifications ->
                    _state.update {
                        it.copy(notificationsList = notifications.filter { not -> not.title != null })
                    }
                }.onLeft { error ->
                    _state.update {
                        it.copy(error = error.error.message)
                    }
                    sendEvent(Event.Toast(error.error.message))
                }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun onNotificationRead(notification: Notification, isRead: Boolean) {
        viewModelScope.launch {
            val notList = state.value.notificationsList
            val indexOfUpdate = notList.indexOfFirst { it.id == notification.id }
            notList[indexOfUpdate].read = isRead
            _state.update { it.copy(notificationsList = notList) }
            _state.update { it.copy(isLoading = true) }
            notificationsRepository.markAsReadNotification(
                notification.id,
                notification.copy(read = isRead)
            )
                .onRight { _ ->

                }.onLeft { error ->
                    _state.update {
                        it.copy(error = error.error.message)
                    }
                    sendEvent(Event.Toast(error.error.message))
                }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun markAllUnRead(allUnread: Boolean) {
        state.value.notificationsList.forEach { item ->
            onNotificationRead(item, !allUnread)
        }
    }

    fun fetchMoreNotifications() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            notificationsRepository.getNotifications()
                .onRight { notifications ->
                    _state.update {
                        val newNots = notifications.filter { not -> not.title != null }
                        val updatedList = mutableListOf<Notification>()
                        updatedList.addAll(it.notificationsList)
                        updatedList.addAll(newNots)
                        it.copy(notificationsList = updatedList)
                    }
                }.onLeft { error ->
                    _state.update {
                        it.copy(error = error.error.message)
                    }
                    sendEvent(Event.Toast(error.error.message))
                }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun refresh() {
        _state.update { it.copy(notificationsList = emptyList()) }
        fetchMoreNotifications()
    }
}