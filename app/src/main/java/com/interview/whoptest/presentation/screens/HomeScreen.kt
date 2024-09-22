package com.interview.whoptest.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.interview.whoptest.domain.model.Notification
import com.interview.whoptest.presentation.components.NotificationsCard
import com.interview.whoptest.ui.theme.Purple40
import com.interview.whoptest.ui.theme.PurpleGrey40
import com.interview.whoptest.ui.theme.PurpleGrey80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreen() {
    val viewModel: HomeScreenViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeContent(
        state = state,
        onNotificationRead = { notification, isRead ->
            viewModel.onNotificationRead(
                notification,
                isRead
            )
        },
        onAllUnRead = { unread -> viewModel.markAllUnRead(unread) },
        fetchMoreNotifications = { viewModel.fetchMoreNotifications() },
        refresh = { viewModel.refresh() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeScreenViewState,
    onNotificationRead: (notification: Notification, isRead: Boolean) -> Unit,
    onAllUnRead: (unRead: Boolean) -> Unit,
    fetchMoreNotifications: () -> Unit,
    refresh: () -> Unit
) {
    var allUnRead by remember { mutableStateOf(false) }

    if (state.isLoading) {
        CircularProgressIndicator()
    }

    Box(contentAlignment = Alignment.Center) {
        Scaffold(
            containerColor = PurpleGrey80,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Notify",
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = PurpleGrey40),
                    modifier = Modifier.shadow(
                        elevation = 5.dp,
                        spotColor = Color.DarkGray,
                    )
                )
            }
        )
        { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Checkbox(
                            modifier = Modifier
                                .padding(start = 4.dp),
                            checked = allUnRead,
                            onCheckedChange = { checked ->
                                allUnRead = checked
                                onAllUnRead(checked)
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Purple40,
                                uncheckedColor = Purple40,
                                checkmarkColor = Color.White
                            )
                        )
                        Text(
                            modifier = Modifier.padding(horizontal = 4.dp),
                            text = "All Unread",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Purple40,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Left,
                            style = MaterialTheme.typography.bodySmall
                        )

                    }

                    IconButton(onClick = { refresh() }) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Favorite",
                            tint = Purple40
                        )
                    }
                }

                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(state.notificationsList) { notification ->
                        var isVisible by remember { mutableStateOf(false) }
                        LaunchedEffect(isVisible) {
                            if (isVisible) {
                                launch {
                                    // After 3 second delay mark notification as read.
                                    delay(3000)
                                    onNotificationRead(notification, true)
                                }
                            }
                        }
                        NotificationsCard(
                            notification = notification,
                            onNotificationRead = onNotificationRead,
                            onVisible = { isVisible = true }
                        )
                        //Infinite Scroll
                        if (state.notificationsList.lastOrNull() == notification
                            && !state.isLoading
                        ) {
                            fetchMoreNotifications()
                        }
                    }
                }
            }
        }
    }
}

