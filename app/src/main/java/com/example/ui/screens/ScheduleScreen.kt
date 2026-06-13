package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.notifications.NotificationHelper

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()
    var isRefreshing by remember { mutableStateOf(false) }
    var lastUpdated by remember { mutableStateOf("Just now") }
    var train82Delay by remember { mutableStateOf("+15 MIN") }
    val coroutineScope = rememberCoroutineScope()
    
    val context = LocalContext.current
    val notificationHelper = remember { NotificationHelper(context).apply { createNotificationChannel() } }
    
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, "Notifications enabled", Toast.LENGTH_SHORT).show()
        }
    }
    
    fun requestNotificationPermissionAndSchedule(trainName: String) {
        val scheduleAction = {
            notificationHelper.scheduleNotification(
                "Train Alert: $trainName",
                "Your train departs in 15 minutes. Please proceed to the platform.",
                5000 // 5 seconds for demonstration
            )
            Toast.makeText(context, "Notification scheduled for $trainName", Toast.LENGTH_SHORT).show()
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when (PackageManager.PERMISSION_GRANTED) {
                ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) -> {
                    scheduleAction()
                }
                else -> {
                    // Try to schedule anyway, it might need permission first
                    scheduleAction()
                    launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        } else {
            scheduleAction()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = 80.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("GMR", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    Text("LIVE BOARD", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                
                IconButton(onClick = {
                    coroutineScope.launch {
                        isRefreshing = true
                        delay(1000)
                        lastUpdated = "Updated just now"
                        train82Delay = "+18 MIN" // Simulation of delay increase
                        isRefreshing = false
                    }
                }) {
                    Icon(if(isRefreshing) Icons.Default.Warning else Icons.Default.Refresh, contentDescription = "Refresh", tint = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text("Gambir Station", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text(lastUpdated, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search train or destination...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest
                )
            )
        }

        if (isRefreshing) {
             LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        Column(modifier = Modifier.verticalScroll(scrollState).padding(horizontal = 16.dp)) {
            TrainRowItem(
                time = "14:45",
                status = "ON TIME",
                name = "Argo Bromo Anggrek",
                number = "KA 1 • Luxury Sleeper",
                destination = "Surabaya Pasar Turi",
                platform = "3",
                isDelayed = false,
                onTrack = { requestNotificationPermissionAndSchedule("Argo Bromo Anggrek") }
            )
            TrainRowItem(
                time = "15:00",
                status = train82Delay,
                name = "Taksaka",
                number = "KA 82 • Executive",
                destination = "Yogyakarta",
                platform = "1",
                isDelayed = true,
                warning = "Technical Signal Issue",
                onTrack = { requestNotificationPermissionAndSchedule("Taksaka") }
            )
            TrainRowItem(
                time = "15:30",
                status = "BOARDING",
                name = "Gajayana",
                number = "KA 56 • Priority",
                destination = "Malang",
                platform = "4",
                isDelayed = false,
                onTrack = { requestNotificationPermissionAndSchedule("Gajayana") }
            )
            TrainRowItem(
                time = "16:10",
                status = "ON TIME",
                name = "Sembrani",
                number = "KA 78 • Executive",
                destination = "Surabaya Pasar Turi",
                platform = "-",
                isDelayed = false,
                onTrack = { requestNotificationPermissionAndSchedule("Sembrani") }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            AnnouncementCard()
        }
    }
}

@Composable
fun TrainRowItem(
    time: String,
    status: String,
    name: String,
    number: String,
    destination: String,
    platform: String,
    isDelayed: Boolean,
    warning: String? = null,
    onTrack: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        border = androidx.compose.foundation.BorderStroke(if (isDelayed) 2.dp else 1.dp, if (isDelayed) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(time, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Surface(
                    color = if (status == "BOARDING") MaterialTheme.colorScheme.surfaceContainerHighest else if (isDelayed) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        status,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = if (status == "BOARDING") MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(name, style = MaterialTheme.typography.titleMedium, color = if(isDelayed) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary)
                    Text(number, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                IconButton(onClick = onTrack) {
                    Icon(Icons.Default.Notifications, contentDescription = "Track Train", tint = MaterialTheme.colorScheme.primary)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("To:", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Text(destination, style = MaterialTheme.typography.bodyMedium)
                }
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        platform,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            if (warning != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    Icon(Icons.Default.Warning, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(16.dp))
                    Text(warning, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun AnnouncementCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.outline)
                Text("STATION ANNOUNCEMENT", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "Platform 2 is currently closed for maintenance. Please use Platform 1 or 3 for departing northbound trains.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
