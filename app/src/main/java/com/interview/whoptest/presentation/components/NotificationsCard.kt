package com.interview.whoptest.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.interview.whoptest.domain.model.Notification
import com.interview.whoptest.ui.theme.Pink40
import com.interview.whoptest.ui.theme.Pink80
import com.interview.whoptest.ui.theme.Purple80
import com.interview.whoptest.ui.theme.PurpleGrey40
import com.interview.whoptest.ui.theme.PurpleGrey80
import com.interview.whoptest.utils.formattedDate

@Composable
fun NotificationsCard(
    modifier: Modifier = Modifier,
    notification: Notification,
    onNotificationRead: (notification: Notification, isRead: Boolean) -> Unit,
    onVisible: () -> Unit
) {
    SideEffect {
        onVisible()
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = if (!notification.read) Pink40 else PurpleGrey80)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(), verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = modifier
                        .weight(4f)
                        .padding(4.dp)
                ) {
                    Text(
                        text = if (notification.timestamp == null) "" else notification.timestamp.formattedDate(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = if (!notification.read) Purple80 else Color.DarkGray,
                        fontWeight = FontWeight.Normal,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = notification.title ?: "",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = if (!notification.read) PurpleGrey80 else Color.DarkGray,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        thickness = 0.9.dp,
                        color = if (!notification.read) PurpleGrey80 else Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = notification.message ?: "",
                        maxLines = 5,
                        color = if (!notification.read) PurpleGrey80 else Color.DarkGray,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Light,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                Checkbox(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp),
                    checked = notification.read,
                    onCheckedChange = { checked ->
                        onNotificationRead(notification, checked)
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.DarkGray,
                        uncheckedColor = PurpleGrey80,
                        checkmarkColor = Color.White
                    )
                )
            }

        }
    }
}