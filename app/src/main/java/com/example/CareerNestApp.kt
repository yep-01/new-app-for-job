package com.example

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.screens.*
import com.example.ui.theme.MyApplicationTheme

@Composable
fun CareerNestApp(viewModel: CareerNestViewModel = viewModel()) {
    // Wrap in our custom theme with dynamic dark/light configuration
    MyApplicationTheme(darkTheme = viewModel.isDarkMode) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            // Check auth state
            if (!viewModel.isLoggedIn) {
                // If sub-screen is SignUp, show SignUp, otherwise show Login
                if (viewModel.currentSubScreen == "SignUp") {
                    SignUpScreen(
                        viewModel = viewModel,
                        onNavigateToLogin = { viewModel.currentSubScreen = "Login" }
                    )
                } else {
                    LoginScreen(
                        viewModel = viewModel,
                        onNavigateToSignUp = { viewModel.currentSubScreen = "SignUp" }
                    )
                }
            } else {
                // Logged in: Main Scaffold
                // Hide bottom bar if we are in full screen sub-screens
                val isSubscreenActive = viewModel.currentSubScreen != null
                val showBottomBar = !isSubscreenActive

                Scaffold(
                    bottomBar = {
                        if (showBottomBar) {
                            NavigationBar(
                                containerColor = MaterialTheme.colorScheme.surface,
                                tonalElevation = 4.dp
                            ) {
                                NavigationBarItem(
                                    selected = viewModel.currentTab == "Home",
                                    onClick = { viewModel.currentTab = "Home" },
                                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") },
                                    label = { Text("Home", fontWeight = FontWeight.Medium) }
                                )
                                NavigationBarItem(
                                    selected = viewModel.currentTab == "Career",
                                    onClick = { viewModel.currentTab = "Career"; viewModel.currentSubScreen = null },
                                    icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Career") },
                                    label = { Text("Career", fontWeight = FontWeight.Medium) }
                                )
                                NavigationBarItem(
                                    selected = viewModel.currentTab == "Jobs",
                                    onClick = { viewModel.currentTab = "Jobs" },
                                    icon = { Icon(imageVector = Icons.Default.Build, contentDescription = "Jobs") },
                                    label = { Text("Jobs", fontWeight = FontWeight.Medium) }
                                )
                                NavigationBarItem(
                                    selected = viewModel.currentTab == "Learn",
                                    onClick = { viewModel.currentTab = "Learn" },
                                    icon = { Icon(imageVector = Icons.Default.Info, contentDescription = "Learn") },
                                    label = { Text("Learn", fontWeight = FontWeight.Medium) }
                                )
                                NavigationBarItem(
                                    selected = viewModel.currentTab == "Profile",
                                    onClick = { viewModel.currentTab = "Profile" },
                                    icon = { Icon(imageVector = Icons.Default.Person, contentDescription = "Profile") },
                                    label = { Text("Profile", fontWeight = FontWeight.Medium) }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = if (showBottomBar) 0.dp else 0.dp) // Layout handles its own content padding
                    ) {
                        if (isSubscreenActive) {
                            // Render active subscreen
                            when (viewModel.currentSubScreen) {
                                "Notifications" -> NotificationsScreen(viewModel)
                                else -> {
                                    // Let CareerTab handle nested screens: Roadmap_BA, MockInterviewSetup, ResumeBuilder, MockInterviewPlayer
                                    CareerTab(viewModel)
                                }
                            }
                        } else {
                            // Render active main tab
                            when (viewModel.currentTab) {
                                "Home" -> HomeTab(viewModel)
                                "Career" -> CareerTab(viewModel)
                                "Jobs" -> JobsTab(viewModel)
                                "Learn" -> LearnTab(viewModel)
                                "Profile" -> SettingsScreen(viewModel)
                                else -> HomeTab(viewModel)
                            }
                        }
                    }
                }
            }
        }
    }
}
