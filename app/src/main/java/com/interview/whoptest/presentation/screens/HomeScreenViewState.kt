package com.interview.whoptest.presentation.screens

import com.interview.whoptest.domain.model.Notification

data class HomeScreenViewState(
    val isLoading: Boolean = false,
    val notificationsList: List<Notification> = emptyList(),
    val error: String? = null
)