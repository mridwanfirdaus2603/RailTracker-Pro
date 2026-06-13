package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Train
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.AppDatabase
import com.example.data.TravelRepository
import com.example.navigation.Feed
import com.example.navigation.History
import com.example.navigation.Map
import com.example.navigation.Schedule
import com.example.navigation.Stations
import com.example.navigation.StationDetail
import com.example.navigation.TrainDetail
import com.example.ui.screens.FeedScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HistoryViewModel
import com.example.ui.screens.HistoryViewModelFactory
import com.example.ui.screens.MapScreen
import com.example.ui.screens.ScheduleScreen
import com.example.ui.screens.StationsScreen
import com.example.ui.screens.StationDetailScreen
import com.example.ui.screens.TrainDetailScreen
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        RailTrackApp()
      }
    }
  }
}

@Composable
fun RailTrackApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val isTopLevelRoute = currentRoute?.contains("Feed") == true ||
            currentRoute?.contains("Map") == true ||
            currentRoute?.contains("Schedule") == true ||
            currentRoute?.contains("Stations") == true ||
            currentRoute?.contains("History") == true

    // Initialize DB and ViewModel
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val repository = TravelRepository(database.travelHistoryDao())
    val historyViewModel: HistoryViewModel = viewModel(factory = HistoryViewModelFactory(repository))

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isTopLevelRoute || currentRoute == null) {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                ) {
                    NavigationBarItem(
                        selected = currentRoute?.contains("Map") == true,
                        onClick = {
                            navController.navigate(Map) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Map, contentDescription = "Map") },
                        label = { Text("Live Map") }
                    )
                    NavigationBarItem(
                        selected = currentRoute?.contains("Schedule") == true,
                        onClick = {
                            navController.navigate(Schedule) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Train, contentDescription = "Schedule") },
                        label = { Text("Schedule") }
                    )
                    NavigationBarItem(
                        selected = currentRoute?.contains("Stations") == true,
                        onClick = {
                            navController.navigate(Stations) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Place, contentDescription = "Stations") },
                        label = { Text("Stations") }
                    )
                    NavigationBarItem(
                        selected = currentRoute?.contains("Feed") == true,
                        onClick = {
                            navController.navigate(Feed) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.DynamicFeed, contentDescription = "Feed") },
                        label = { Text("Feed") }
                    )
                    NavigationBarItem(
                        selected = currentRoute?.contains("History") == true,
                        onClick = {
                            navController.navigate(History) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.History, contentDescription = "Logs") },
                        label = { Text("Logs") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Feed,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Feed> {
                FeedScreen(onNavigateToTrainDetail = { id -> navController.navigate(TrainDetail(id)) })
            }
            composable<Map> {
                MapScreen(onNavigateToTrainDetail = { id -> navController.navigate(TrainDetail(id)) })
            }
            composable<Schedule> {
                ScheduleScreen()
            }
            composable<Stations> {
                StationsScreen(onNavigateToStation = { id -> navController.navigate(StationDetail(id)) })
            }
            composable<History> {
                HistoryScreen(viewModel = historyViewModel)
            }
            composable<TrainDetail> { backStackEntry ->
                TrainDetailScreen(
                    trainId = "1",
                    onNavigateBack = { navController.navigateUp() }
                )
            }
            composable<StationDetail> { backStackEntry ->
                StationDetailScreen(
                    stationId = "GMR",
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        }
    }
}
