package com.example.screens

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.Assessment
import com.example.CareerNestViewModel

@Composable
fun CareerTab(viewModel: CareerNestViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (viewModel.currentSubScreen) {
            "MockInterviewSetup" -> MockInterviewSetupView(viewModel)
            "MockInterviewPlayer" -> MockInterviewPlayerView(viewModel)
            "ResumeBuilder" -> ResumeBuilderView(viewModel)
            "Roadmap_BA" -> RoadmapBAView(viewModel)
            else -> DiscoverYourPathView(viewModel)
        }
    }
}

// 1. Core Discover Your Path (Assessments) View
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiscoverYourPathView(viewModel: CareerNestViewModel) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CareerNest",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.currentSubScreen = "Notifications" }) {
                        Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
        ) {
            // Header Section
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Discover Your Path",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Complete assessments to unlock personalized career recommendations.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Card(
                        modifier = Modifier.padding(start = 8.dp),
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Profile ${viewModel.profileCompletePercentage}% Complete",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Quick Jump Menu Row
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.currentSubScreen = "MockInterviewSetup" },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Mock Prep", style = MaterialTheme.typography.labelMedium, color = Color.White)
                    }
                    Button(
                        onClick = { viewModel.currentSubScreen = "ResumeBuilder" },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Resumes", style = MaterialTheme.typography.labelMedium, color = Color.White)
                    }
                    Button(
                        onClick = { viewModel.currentSubScreen = "Roadmap_BA" },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Roadmaps", style = MaterialTheme.typography.labelMedium, color = Color.White)
                    }
                }
            }

            // List of Assessments
            items(viewModel.assessments) { assessment ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp)),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = when (assessment.iconType) {
                                        "psychology" -> Icons.Default.Info
                                        "construction" -> Icons.Default.Build
                                        else -> Icons.Default.Favorite
                                    },
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = assessment.title,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = assessment.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                if (assessment.progress > 0f && assessment.progress < 1.0f) {
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        LinearProgressIndicator(
                                            progress = { assessment.progress },
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(8.dp)
                                                .clip(CircleShape),
                                            color = Color(0xFFF57C00), // Amber
                                            trackColor = MaterialTheme.colorScheme.surfaceVariant
                                        )
                                        Text(
                                            text = "${(assessment.progress * 100).toInt()}%",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }

                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow, // Simulated timer icon
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (assessment.timeLeftText.isNotBlank()) assessment.timeLeftText else "${assessment.durationMinutes} mins",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            if (assessment.status == "Start Test") {
                                Button(
                                    onClick = {
                                        viewModel.completeAssessment(assessment.id)
                                        Toast.makeText(context, "Completed Personality Test! Profile updated to 60%.", Toast.LENGTH_SHORT).show()
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(text = "Start Test", color = Color.White)
                                }
                            } else if (assessment.status == "Retake Test") {
                                OutlinedButton(
                                    onClick = { viewModel.completeAssessment(assessment.id) },
                                    shape = RoundedCornerShape(8.dp),
                                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(text = "Retake Test", color = MaterialTheme.colorScheme.primary)
                                }
                            } else {
                                Button(
                                    onClick = { viewModel.completeAssessment(assessment.id) },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(text = "Continue", color = Color.White)
                                }
                            }
                        }
                    }
                }
            }

            // Locked AI Career Recommendation Card
            item {
                val isUnlocked = viewModel.profileCompletePercentage >= 60
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            1.dp,
                            if (isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                            RoundedCornerShape(12.dp)
                        ),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .background(
                                    if (isUnlocked) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isUnlocked) Icons.Default.Star else Icons.Default.Lock,
                                contentDescription = null,
                                tint = if (isUnlocked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "AI Career Recommendation",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isUnlocked)
                                    "Congratulations! Unlock to see tailored roles: Business Analyst (92% match) and Marketing Specialist (85% match)."
                                else
                                    "Complete all assessments to let our advanced AI model analyze your profile and suggest tailored career paths.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            if (isUnlocked) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        Toast.makeText(context, "AI Analysis Complete: Business Analyst strongly recommended!", Toast.LENGTH_LONG).show()
                                        viewModel.currentSubScreen = "Roadmap_BA"
                                    },
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                                ) {
                                    Text(text = "View BA Roadmap Insights", color = Color.White)
                                }
                            } else {
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {},
                                    enabled = false,
                                    shape = RoundedCornerShape(8.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Icon(imageVector = Icons.Outlined.Lock, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(text = "Unlock Insights", color = MaterialTheme.colorScheme.onSurfaceVariant)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// 2. Mock Interview Setup View (Mockup 1)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockInterviewSetupView(viewModel: CareerNestViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Mock Interview Setup", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.currentSubScreen = null }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                // Info description
                item {
                    Text(
                        text = "Prepare for your next big role. Choose an interview style below and get real-time feedback to boost your confidence.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Readiness summary card
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(
                                    text = "INTERVIEW READINESS",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Bold
                                )
                                Row(
                                    verticalAlignment = Alignment.Bottom,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "78%",
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.secondary,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(2.dp))
                                        Text(
                                            text = "+5% this week",
                                            style = MaterialTheme.typography.labelMedium,
                                            color = MaterialTheme.colorScheme.secondary,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Text(
                                    text = "You're making solid progress. Focus on technical pacing.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            // SVG Circular Chart drawing
                            Box(modifier = Modifier.size(64.dp), contentAlignment = Alignment.Center) {
                                Canvas(modifier = Modifier.size(64.dp)) {
                                    drawCircle(color = Color(0xFFEDEEEF), style = Stroke(width = 4.dp.toPx()))
                                    drawArc(
                                        color = Color(0xFF006B5C),
                                        startAngle = -90f,
                                        sweepAngle = 360f * 0.78f,
                                        useCenter = false,
                                        style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                                    )
                                }
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Select Interview Type",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                // AI Feedback Card
                item {
                    InterviewTypeCard(
                        title = "AI Interactive Feedback",
                        recommended = true,
                        description = "Dynamic questions adapted to your resume with instant conversational feedback and score analysis.",
                        durationText = "15-30 Min",
                        iconType = "psychology",
                        isSelected = viewModel.selectedInterviewType == "AI Interactive Feedback",
                        onClick = { viewModel.selectedInterviewType = "AI Interactive Feedback" }
                    )
                }

                // HR Card
                item {
                    InterviewTypeCard(
                        title = "HR & Behavioral",
                        recommended = false,
                        description = "Practice STAR method answers for cultural fit and soft skills evaluation.",
                        durationText = "10-20 Min",
                        iconType = "groups",
                        isSelected = viewModel.selectedInterviewType == "HR & Behavioral",
                        onClick = { viewModel.selectedInterviewType = "HR & Behavioral" }
                    )
                }

                // Technical Assessment
                item {
                    InterviewTypeCard(
                        title = "Technical Assessment",
                        recommended = false,
                        description = "Role-specific challenges, system design, and problem-solving scenarios.",
                        durationText = "20-45 Min",
                        iconType = "code",
                        isSelected = viewModel.selectedInterviewType == "Technical Assessment",
                        onClick = { viewModel.selectedInterviewType = "Technical Assessment" }
                    )
                }
            }

            // Bottom Sticky Bar (Action)
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Selected Mode",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = viewModel.selectedInterviewType,
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Button(
                        onClick = {
                            viewModel.startInterview()
                            viewModel.currentSubScreen = "MockInterviewPlayer"
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.width(180.dp)
                    ) {
                        Text(text = "Start Interview", color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, tint = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun InterviewTypeCard(
    title: String,
    recommended: Boolean,
    description: String,
    durationText: String,
    iconType: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .border(
                2.dp,
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (iconType) {
                        "psychology" -> Icons.Default.Info
                        "groups" -> Icons.Default.Person
                        else -> Icons.Default.Build
                    },
                    contentDescription = null,
                    tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    if (recommended) {
                        Card(
                            shape = CircleShape,
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                        ) {
                            Text(
                                text = "RECOMMENDED",
                                style = TextStyle(fontSize = 9.sp, fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                            )
                        }
                    }
                }

                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.primary)
                    Text(
                        text = durationText,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            RadioButton(selected = isSelected, onClick = onClick)
        }
    }
}

// 2b. Interactive Question Player View
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockInterviewPlayerView(viewModel: CareerNestViewModel) {
    var answerText by remember { mutableStateOf("") }
    val currentQuestion = viewModel.interviewQuestions[viewModel.currentQuestionIndex]

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Active Interview Section", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.exitInterview() }) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Exit")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (!viewModel.isInterviewFinished) {
                // Header progress
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Question ${viewModel.currentQuestionIndex + 1} of ${viewModel.interviewQuestions.size}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    LinearProgressIndicator(
                        progress = { (viewModel.currentQuestionIndex + 1) / viewModel.interviewQuestions.size.toFloat() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }

                // Question Prompt Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = currentQuestion,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                // Text Input
                OutlinedTextField(
                    value = answerText,
                    onValueChange = { answerText = it },
                    placeholder = { Text("Type your structured answer here...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(bottom = 24.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                // Submit answer
                Button(
                    onClick = {
                        viewModel.submitAnswer(answerText)
                        answerText = ""
                    },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        text = if (viewModel.currentQuestionIndex == viewModel.interviewQuestions.size - 1) "Finish Interview" else "Submit Answer",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            } else {
                // Finished/Feedback screen
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(72.dp)
                        )
                    }
                    item {
                        Text(
                            text = "Interview Complete!",
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp)),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest)
                        ) {
                            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(
                                    text = "AI Performance Insights",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = viewModel.mockFeedbackText,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = { viewModel.exitInterview() },
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Go to Career Board", color = Color.White)
                }
            }
        }
    }
}

// 3. Resume Builder View (Mockup 3)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeBuilderView(viewModel: CareerNestViewModel) {
    var selectedTemplate by remember { mutableStateOf("Modern") }
    var showCreateDialog by remember { mutableStateOf(false) }
    var newResumeTitle by remember { mutableStateOf("") }
    val context = LocalContext.current

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false },
            title = { Text(text = "Create New Resume") },
            text = {
                OutlinedTextField(
                    value = newResumeTitle,
                    onValueChange = { newResumeTitle = it },
                    placeholder = { Text("Resume Title (e.g. Android Engineer)") }
                )
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.createResume(newResumeTitle)
                    newResumeTitle = ""
                    showCreateDialog = false
                    Toast.makeText(context, "Added Resume successfully!", Toast.LENGTH_SHORT).show()
                }) {
                    Text(text = "Create")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false }) {
                    Text(text = "Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Resume Builder", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.currentSubScreen = null }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)
            ) {
                // Header
                item {
                    Text(
                        text = "Craft a professional resume to stand out.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Select Template Carousel
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Select Template",
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "View All",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {}
                            )
                        }

                        val templates = listOf(
                            Pair("Modern", "https://lh3.googleusercontent.com/aida-public/AB6AXuDgN1JhZ3Tfzr2KrdLlCMwLaLXCv5pc1hcHJa6iOWaPPAUXa3VqIpv2zHlDwmlUlMxUWmP6Z3HLVNNZJG1lGPXCrAoyqm_i7Ba1BFJyfsH7J2KZTnlOmSae0o30SgaZde6STdsCpzuf_B6CKKrcVeUe2Y3Hll8yWIuFvjI1fhra-yOV1KqtmF5bVhJ6_o-1vqt3m6QbKPhhhME5rGo81tyRe0vB3qzz1enviSF-ZHbdaEVlrZ5XakSj3sglI9PhImLB_D1a3LiXGF0x"),
                            Pair("Classic", "https://lh3.googleusercontent.com/aida-public/AB6AXuCJYgnYWVOzY8qa2EjUbPIIvZklQWhhBi14YZ0P-Vb6-HiHku48PsaDGB81Vakn4zIcu2QCPS74cgxDaCnRGmgF9eR7XAfb77ESIw7UJirL8SCRPu4c4GDYAWk6a1xXcGpCGQAkE1MvrLBfzs1lBkUMQdrgBGvfh_it0RkgKlFMbQIANh1rW-SqXE2DAms3vuJv7HK4jdjljQTbAb0PGR3ClHEfBejNaklpv94w_tc5ltcrnXxXKNKFcFuaTXNWCDQA-xO8ZoB1B5bn"),
                            Pair("Creative", "https://lh3.googleusercontent.com/aida-public/AB6AXuD-_Gpb9jBOzIQS6yFBMQmmoW9Gta-kj_IVuIXh1cncgccV0MxvXhaB4Tn-VCZZ2634czzwZh7xwH57mRRPQrabPawU1dIL33bvKKUVTo3yqtIJR3ss6RYFmCZwWO-bf8NTcXN7OZoEWj3nI0yNrvtVuvUbPzOKLQ8zQj5kptRM_aSQbD5vHwn3nSPCc-1zdlRdGDcn-IW0LbYafpTds3PUHalI77p_tnoJGhDupSiSo4OUnL2KOH8rkA8XqFBUjio4shMhj3lZO19d"),
                            Pair("Executive", "https://lh3.googleusercontent.com/aida-public/AB6AXuB7RadTDmOQI7OSeCamjDnf7TMNYCxNYxL4znnvohwvLN3SClFWs_VhcyGbopIUbQZyJG9SW3maYu8bhH0WeexP81NuqoSPhAK18H9Z4jSmJ1b5_Usy89nScDWgSpvrT7F7kB9B2QsT_-O7LiJ2y9LH-5UK-3xpYZ2lA7P6mPby_l4S8IG7JVXqdKUkdviCmm-G49yhoqXLTdR56NDiG0OGGAc2QH-anm2ZNnYbbAmuhhpNDwlCjnuxuKerGYxOg-_V85xKiMJ7p2nD")
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(templates) { (name, url) ->
                                val isSelected = selectedTemplate == name
                                Column(
                                    modifier = Modifier
                                        .width(130.dp)
                                        .clickable { selectedTemplate = name },
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f / 1.41f)
                                            .border(
                                                if (isSelected) 2.dp else 1.dp,
                                                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                                RoundedCornerShape(8.dp)
                                            )
                                            .clip(RoundedCornerShape(8.dp))
                                    ) {
                                        AsyncImage(
                                            model = url,
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                        if (isSelected) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                                                    .padding(8.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.CheckCircle,
                                                    contentDescription = null,
                                                    tint = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.size(32.dp)
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = name,
                                        style = MaterialTheme.typography.labelMedium,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "Your Resumes",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }

                // list of resumes
                items(viewModel.resumes) { resume ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(12.dp)),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Info, // Simulated file doc icon
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }

                                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                    Text(
                                        text = resume.title,
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = resume.lastUpdated,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }

                            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                IconButton(onClick = {
                                    Toast.makeText(context, "Editing ${resume.title}", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                IconButton(onClick = {
                                    Toast.makeText(context, "Downloading PDF for ${resume.title}...", Toast.LENGTH_SHORT).show()
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow, // Simulated download icon
                                        contentDescription = "Download",
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Create actions
            Button(
                onClick = { showCreateDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Create New Resume", color = Color.White, style = MaterialTheme.typography.headlineSmall)
            }
        }
    }
}

// 4. Career Roadmap view (Mockup 5)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoadmapBAView(viewModel: CareerNestViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Roadmap: Business Analyst", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { viewModel.currentSubScreen = null }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(top = 16.dp, bottom = 100.dp)
            ) {
                // Steps
                item { RoadmapNode(stepNumber = 1, title = "Learn Excel & Power BI", duration = "2-3 Months", isCompleted = true, isActive = false) }
                item { RoadmapNode(stepNumber = 2, title = "Build Strong Resume", duration = "1 Month", isCompleted = true, isActive = false) }
                item {
                    RoadmapNode(
                        stepNumber = 3,
                        title = "Find Internship",
                        duration = "2-3 Months",
                        isCompleted = false,
                        isActive = true,
                        actionText = "Browse Internships",
                        onAction = { viewModel.currentTab = "Jobs"; viewModel.currentSubScreen = null }
                    )
                }
                item { RoadmapNode(stepNumber = 4, title = "Mock Interview Preparation", duration = "1 Month", isCompleted = false, isActive = false, isFuture = true) }
                item { RoadmapNode(stepNumber = 5, title = "Final Placement", duration = "Your Goal", isCompleted = false, isActive = false, isFuture = true) }
                item { RoadmapNode(stepNumber = 6, title = "Career Success", duration = "Keep Growing", isCompleted = false, isActive = false, isFuture = true) }
            }

            // Progress Summary Sticky bar
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "Your Progress",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "60% Completed",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    LinearProgressIndicator(
                        progress = { 0.6f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(CircleShape),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun RoadmapNode(
    stepNumber: Int,
    title: String,
    duration: String,
    isCompleted: Boolean,
    isActive: Boolean,
    isFuture: Boolean = false,
    actionText: String = "",
    onAction: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Step Icon/Circle
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    if (isCompleted) MaterialTheme.colorScheme.primaryContainer
                    else if (isActive) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.surfaceVariant,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = stepNumber.toString(),
                    style = MaterialTheme.typography.headlineSmall,
                    color = if (isActive) Color.White else MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Card info
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = if (isFuture) MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f) else if (isActive) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isActive || isCompleted) FontWeight.Bold else FontWeight.Medium
            )
            Text(
                text = duration,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (isActive && actionText.isNotBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = onAction,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(text = actionText, color = Color.White)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }
        }

        if (isCompleted) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}
