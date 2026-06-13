package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
