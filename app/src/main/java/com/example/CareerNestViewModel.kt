package com.example

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CareerNestViewModel : ViewModel() {

    // Auth States
    var isLoggedIn by mutableStateOf(false)
        private set
    var username by mutableStateOf("Ashwini")
        private set
    var userEmail by mutableStateOf("janamathiyalagan@gmail.com")
        private set

    // Navigation and Subscreens
    var currentTab by mutableStateOf("Home")
    var currentSubScreen by mutableStateOf<String?>(null) // "Roadmap_BA", "Notifications", "MockInterviewSetup", "ResumeBuilder", etc.

    // Stats
    var careerReadinessScore by mutableStateOf(78)
        private set
    var profileCompletePercentage by mutableStateOf(40)
        private set

    // Global Preferences
    var isDarkMode by mutableStateOf(false)
        private set

    // Settings States
    var activeLanguage by mutableStateOf("English")
        private set

    // Interview setup choice
    var selectedInterviewType by mutableStateOf("AI Interactive Feedback")

    // Filter and search states
    var notificationsFilter by mutableStateOf("All")
    var searchQuery by mutableStateOf("")
    var selectedJobCategory by mutableStateOf("All")

    // Notification List
    var notifications by mutableStateOf(
        listOf(
            Notification(
                id = "1",
                title = "New internship matches found",
                body = "We found 5 new Data Science internships that match your preferences and skills.",
                time = "2h",
                category = "Jobs",
                isUnread = true,
                iconType = "work"
            ),
            Notification(
                id = "2",
                title = "Interview scheduled with XYZ Company",
                body = "Sarah Jenkins invited you to a technical interview round on Thursday, Oct 12th at 10:00 AM.",
                time = "5h",
                category = "Interviews",
                isUnread = true,
                iconType = "avatar",
                avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA4zztqfg3R7LCrUWS5aiqZcOSbl6J2x2au8MOdlFakCibYPZteeQvLytTUlJUUHo5KwZK1zgFJpNTvJf1hXeknBUu7oMKiHbvDax4onl0VQrv4YrSq6kRzWO2G8pJWoRJIDeiWyDLniYcD4_KZ2k5gnxoNh59GBe1QtWovmfH04kBZMy6e71Y3dQx6iBod5gw0kbLW5QNxyNcEjPB_kQQoWB_1KAk2_pINKH_4baesuSh1yQgTXlOl42jx6UdTxnKU-GJh-lf9Cn3a",
                hasAction = true,
                actionTextPrimary = "Prepare",
                actionTextSecondary = "Reschedule"
            ),
            Notification(
                id = "3",
                title = "Application status updated",
                body = "Your application for \"Junior Frontend Developer\" at TechFlow has been moved to Under Review.",
                time = "1d",
                category = "Alerts",
                isUnread = false,
                iconType = "assignment"
            ),
            Notification(
                id = "4",
                title = "Weekly learning goal met!",
                body = "You completed 3 modules this week. Keep up the great momentum for your certification.",
                time = "2d",
                category = "Alerts",
                isUnread = false,
                iconType = "school"
            )
        )
    )
        private set

    // Jobs list
    var jobs by mutableStateOf(
        listOf(
            Job(
                id = "j1",
                title = "Business Development Intern",
                company = "XYZ Company",
                location = "Chennai, Tamil Nadu",
                timeAgo = "2d ago",
                logoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDmR1l5K3xsuuqPT-KQQG46bHfL8S5-kcQbhFmiJy1uz-MCe9fSYtjtQRsMYyK0srQGYCYFscWAqZ_sdSoIITvxFVo-2HOV7uGBhDGuBrS7f1Zx_sEJYUThVjZUUv2H0oILKqJvnmu6DqFHw-gkWgnz4bbNqL-rRhWUFfqG74f2T8dckNzg8ptyAd3xVVNdHt5xg4UrqlL-u1LD3P50WOdULDUvUzMQIAxnH52bXoikO5MZC-RFw5oFr73bOVOph_d1iEtvVMuf0tnK",
                isApplied = false,
                type = "Internships"
            ),
            Job(
                id = "j2",
                title = "Marketing Intern",
                company = "ABC Pvt Ltd",
                location = "Bangalore, Karnataka",
                timeAgo = "5d ago",
                logoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAlyhPhpUwsr9zqRPYXp6904k8U-8nHEwYGDBXWnDB6W1xFndjzEG-yNqNT4bb9K4gqUFtYh-xijvBELJXGYQ1W3qJh4JdS_JSvRe1Ov4jrUZQUmyUW1NkytTQsINSIestRHWGJ_Dn7II5jTJbqq0Px-_kTacSkizAERfu5SflWXtcrR7wA07I959f9_FsybdXpBnz4ZCthXzIIDCufjNiztpeVSTfL4RP05q3m9Xxq9t7AuL9nSnHHoBo_eD8Oi_KW7iTJIsLXna47",
                isApplied = false,
                type = "Internships"
            ),
            Job(
                id = "j3",
                title = "HR Intern",
                company = "People First Pvt Ltd",
                location = "Chennai, Tamil Nadu",
                timeAgo = "6d ago",
                logoUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCCa7Ew9oLcovFpsMI8M-D9NhsWUT3aCldfMYy81ShwC4bFuoK4ZfG4hjnSBCbfhZ9-TjMa3I4HcInqH-S8Pd_FshYdiLdy39fUpluLalp7pew4iPhjFQnMRzkyt7KSIo5KkX4eY-1ME4SzVW_mp6QxuTjmvayoHDykoCE1B3VFbjUEKsXlNQOxih_hMbhrz9Cu_CpklkEMbmdl3B9YTbY2kIMrTBRj5l9SeyC1uOJ1ghussIt_DS-XbRPEFyV4FhTjeUwAqPfiWjYf",
                isApplied = false,
                type = "Internships"
            )
        )
    )
        private set

    // Resumes list
    var resumes by mutableStateOf(
        listOf(
            Resume("r1", "Software Engineer - Tech", "Last updated: 2 days ago"),
            Resume("r2", "Data Analyst - Finance", "Last updated: 1 week ago"),
            Resume("r3", "General Purpose - 2023", "Last updated: 1 month ago")
        )
    )
        private set

    // Assessments list
    var assessments by mutableStateOf(
        listOf(
            Assessment(
                id = "a1",
                title = "Personality Profile",
                description = "Understand your working style and ideal team dynamics.",
                durationMinutes = 10,
                status = "Start Test",
                iconType = "psychology"
            ),
            Assessment(
                id = "a2",
                title = "Technical Skills",
                description = "Evaluate your proficiency in industry-standard tools.",
                durationMinutes = 15,
                status = "Retake Test",
                iconType = "construction"
            ),
            Assessment(
                id = "a3",
                title = "Career Interests",
                description = "Map what you love doing to potential career pathways.",
                durationMinutes = 5,
                status = "Continue",
                progress = 0.5f,
                timeLeftText = "5 mins left",
                iconType = "favorite"
            )
        )
    )
        private set

    // Active Mock Interview States
    var isMockInterviewActive by mutableStateOf(false)
        private set
    var isInterviewFinished by mutableStateOf(false)
        private set
    var currentQuestionIndex by mutableStateOf(0)
        private set
    var mockFeedbackText by mutableStateOf("")
        private set

    val interviewQuestions = listOf(
        "Tell me about a challenging project you worked on and how you handled obstacles.",
        "How do you stay updated with industry-standard skills and tools in your daily learning?",
        "Describe a situation where you had to work with a diverse team to deliver a project on time."
    )

    private val userAnswers = mutableListOf("", "", "")

    // Auth Actions
    fun login(email: String, name: String = "Ashwini") {
        username = name.ifBlank { "Ashwini" }
        userEmail = email
        isLoggedIn = true
        currentTab = "Home"
        currentSubScreen = null
    }

    fun signup(name: String, email: String) {
        username = name.ifBlank { "Ashwini" }
        userEmail = email
        isLoggedIn = true
        currentTab = "Home"
        currentSubScreen = null
    }

    fun logout() {
        isLoggedIn = false
        currentTab = "Home"
        currentSubScreen = "Login"
    }

    // Toggle Dark Mode
    fun toggleDarkMode() {
        isDarkMode = !isDarkMode
    }

    // Set Language
    fun updateLanguage(lang: String) {
        activeLanguage = lang
    }

    // Apply to jobs
    fun applyToJob(id: String) {
        jobs = jobs.map {
            if (it.id == id) it.copy(isApplied = true) else it
        }
    }

    // Add Resume
    fun createResume(title: String) {
        val newTitle = title.ifBlank { "New Resume ${resumes.size + 1}" }
        resumes = resumes + Resume(
            id = "r${System.currentTimeMillis()}",
            title = newTitle,
            lastUpdated = "Last updated: Just now"
        )
    }

    // Delete Resume
    fun deleteResume(id: String) {
        resumes = resumes.filter { it.id != id }
    }

    // Simulate completing test
    fun completeAssessment(id: String) {
        assessments = assessments.map {
            if (it.id == id) {
                if (it.id == "a1" && it.status == "Start Test") {
                    // Update profile percentage from 40% to 60%
                    profileCompletePercentage = 60
                }
                it.copy(status = "Retake Test", progress = 1.0f, timeLeftText = "Completed")
            } else {
                it
            }
        }
        // Increment readiness score slightly on completion
        if (careerReadinessScore < 95) {
            careerReadinessScore += 2
        }
    }

    // Mark notifications read
    fun markNotificationRead(id: String) {
        notifications = notifications.map {
            if (it.id == id) it.copy(isUnread = false) else it
        }
    }

    // Mock Interview Cycle
    fun startInterview() {
        isMockInterviewActive = true
        isInterviewFinished = false
        currentQuestionIndex = 0
        userAnswers.clear()
        userAnswers.addAll(listOf("", "", ""))
        mockFeedbackText = ""
    }

    fun submitAnswer(answer: String) {
        if (currentQuestionIndex < userAnswers.size) {
            userAnswers[currentQuestionIndex] = answer
        }
        if (currentQuestionIndex < interviewQuestions.size - 1) {
            currentQuestionIndex++
        } else {
            // Finished!
            isInterviewFinished = true
            isMockInterviewActive = false
            // Generate mock feedback
            mockFeedbackText = """
                Mock Interview Score: 84% (Excellent)
                
                Strengths:
                - Clear structured answer layout using the STAR method.
                - Strong vocal tone and pacing, emphasizing leadership.
                
                Areas for Improvement:
                - Expand on exact technical parameters of the database tools.
                - Try incorporating more collaborative outcome metrics.
                
                Keep up the solid progress! We've updated your Interview Readiness to 80% (+2%).
            """.trimIndent()
            careerReadinessScore = 80
        }
    }

    fun exitInterview() {
        isMockInterviewActive = false
        isInterviewFinished = false
        currentSubScreen = null
    }
}
