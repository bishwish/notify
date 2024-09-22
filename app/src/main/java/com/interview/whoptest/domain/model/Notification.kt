package com.interview.whoptest.domain.model

data class Notification(
    val id: String,
    val title: String?,
    val message: String?,
    var read: Boolean,
    val timestamp: String?
)