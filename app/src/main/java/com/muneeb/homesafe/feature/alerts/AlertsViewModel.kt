package com.muneeb.homesafe.feature.alerts

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muneeb.homesafe.R
import com.muneeb.homesafe.data.identity.UserIdentityProvider
import com.muneeb.homesafe.domain.model.HomeSafeNotification
import com.muneeb.homesafe.domain.model.NotificationType
import com.muneeb.homesafe.domain.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class AlertsViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val userIdentityProvider: UserIdentityProvider,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlertsUiState(sections = emptyList()))
    val uiState: StateFlow<AlertsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val uid = userIdentityProvider.currentUid()
            notificationRepository.observeNotificationsForRecipient(uid).collect { notifications ->
                _uiState.value = AlertsUiState(sections = toSections(notifications))
            }
        }
    }

    private fun toSections(notifications: List<HomeSafeNotification>): List<AlertSection> {
        val sorted = notifications.sortedByDescending { it.createdAt ?: Date(0) }
        val cutoff = System.currentTimeMillis() - TimeUnit.HOURS.toMillis(24)
        val (recent, earlier) = sorted.partition { (it.createdAt?.time ?: 0) >= cutoff }

        val sections = mutableListOf<AlertSection>()
        if (recent.isNotEmpty()) {
            sections += AlertSection(
                label = context.getString(R.string.alerts_section_new),
                alerts = recent.map { it.toAlertItem(isUnread = true) }
            )
        }
        if (earlier.isNotEmpty()) {
            sections += AlertSection(
                label = context.getString(R.string.alerts_section_earlier),
                alerts = earlier.map { it.toAlertItem(isUnread = false) }
            )
        }
        return sections
    }

    private fun HomeSafeNotification.toAlertItem(isUnread: Boolean): AlertItem = AlertItem(
        id = id,
        type = if (type == NotificationType.INVITE) AlertType.INVITE else AlertType.ARRIVAL,
        title = context.getString(R.string.notification_arrived_title_format, arriverName),
        meta = arrivedAt?.let(::formatTime).orEmpty(),
        timeLabel = createdAt?.let(::formatRelativeTime).orEmpty(),
        isUnread = isUnread
    )

    private fun formatTime(date: Date): String =
        SimpleDateFormat("h:mm a", Locale.getDefault()).format(date)

    private fun formatRelativeTime(date: Date): String {
        val diffMillis = System.currentTimeMillis() - date.time
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diffMillis)
        val hours = TimeUnit.MILLISECONDS.toHours(diffMillis)
        val days = TimeUnit.MILLISECONDS.toDays(diffMillis)
        return when {
            minutes < 1 -> context.getString(R.string.alerts_time_now)
            minutes < 60 -> context.getString(R.string.alerts_time_minutes_ago_format, minutes.toInt())
            hours < 24 -> context.getString(R.string.alerts_time_hours_ago_format, hours.toInt())
            days == 1L -> context.getString(R.string.alerts_time_yesterday)
            else -> context.getString(R.string.alerts_time_days_ago_format, days.toInt())
        }
    }
}
