package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MapScreen(modifier: Modifier = Modifier, onNavigateToTrainDetail: (String) -> Unit) {
    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0a0c0f))
    ) {
        // Simulated map background routes
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height
            // Simple track lines
            drawLine(Color.DarkGray, Offset(width * 0.1f, height * 0.2f), Offset(width * 0.9f, height * 0.8f), strokeWidth = 4f)
            drawLine(Color.DarkGray, Offset(width * 0.2f, height * 0.7f), Offset(width * 0.8f, height * 0.3f), strokeWidth = 4f)
        }

        // Active Trains
        TrainMarker(
            x = 0.45f, y = 0.35f, 
            name = "KA 1 Argo Bromo", speed = "118 km/h", 
            containerColor = MaterialTheme.colorScheme.primary, 
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = { onNavigateToTrainDetail("ka1") }
        )

        TrainMarker(
            x = 0.65f, y = 0.55f, 
            name = "KA 5 Argo Wilis", speed = "105 km/h", 
            containerColor = MaterialTheme.colorScheme.secondary, 
            contentColor = MaterialTheme.colorScheme.onSecondary,
            onClick = { onNavigateToTrainDetail("ka5") }
        )
        
        TrainMarker(
            x = 0.35f, y = 0.65f, 
            name = "KA 71 Gajayana", speed = "90 km/h", 
            containerColor = MaterialTheme.colorScheme.primary, 
            contentColor = MaterialTheme.colorScheme.onPrimary,
            onClick = { onNavigateToTrainDetail("ka71") }
        )

        // Top info overlay
        Card(
            modifier = Modifier.fillMaxWidth().padding(16.dp).padding(top = 24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
        ) {
            Row(
                modifier = Modifier.padding(16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(modifier = Modifier.size(8.dp).clip(RoundedCornerShape(4.dp)).background(Color.Green))
                    Text("GAPEKA 2025 LIVE", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface)
                }
                Text("RAILTRACK PRO", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
            }
        }
        // Live Trip Assistant Widget
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
                .padding(bottom = 80.dp), // slightly above navigation bar
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(Icons.Default.NearMe, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
                        Text("Nearest Train", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface)
                        Icon(Icons.Default.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp))
                    }
                    Surface(
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text("Delayed 5m", modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Speed Indicator Circular
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(80.dp)) {
                        androidx.compose.material3.CircularProgressIndicator(
                            progress = { 105f / 150f },
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 6.dp,
                            trackColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
                        )
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("105", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                            Text("km/h", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        }
                    }
                    
                    // Train Details
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(4.dp)) {
                                Text("CC 206 13 01", modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                            Text("GE CM20EMP", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.outline)
                        }
                        Text("KA 5 Argo Wilis", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(top = 4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Approaching SGU", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text("|", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline.copy(alpha=0.5f))
                            Text("ETA 12:45 (+2m)", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
                        }
                        
                        Surface(
                            modifier = Modifier.padding(top = 8.dp),
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Notifications, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(12.dp))
                                Text("ACTIVE MONITORING", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    
                    // Distance
                    Surface(
                        shape = RoundedCornerShape(32.dp),
                        border = androidx.compose.foundation.BorderStroke(2.dp, Color.Green),
                        color = Color.Green.copy(alpha = 0.1f),
                        modifier = Modifier.size(64.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            Text("2", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.Green)
                            Text("km", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.Green)
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha=0.2f))
                Spacer(modifier = Modifier.height(16.dp))
                
                Button(
                    onClick = { onNavigateToTrainDetail("ka5") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Track Trip")
                }
            }
        }
    }
}

@Composable
fun BoxWithConstraintsScope.TrainMarker(
    x: Float, y: Float, 
    name: String, speed: String, 
    containerColor: Color, contentColor: Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .align(Alignment.TopStart)
            .offset(
                x = (this@TrainMarker.maxWidth * x) - 24.dp, 
                y = (this@TrainMarker.maxHeight * y) - 24.dp
            )
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = containerColor,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(Icons.Default.Train, contentDescription = null, tint = contentColor, modifier = Modifier.padding(4.dp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
            shape = RoundedCornerShape(4.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)) {
                Text(name, style = MaterialTheme.typography.labelSmall)
                Text(speed, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
