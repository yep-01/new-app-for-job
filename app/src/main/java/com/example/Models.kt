package com.example

data class Job(
    val id: String,
    val title: String,
    val company: String,
    val location: String,
    val timeAgo: String,
    val logoUrl: String,
    val isApplied: Boolean,
    val type: String // "Internships" or "Jobs"
)

data class Resume(
    val id: String,
    val title: String,
    val lastUpdated: String
)

data class Notification(
    val id: String,
    val title: String,
    val body: String,
    val time: String,
    val category: String, // "Jobs", "Interviews", "Alerts"
    val isUnread: Boolean,
    val iconType: String, // "work", "avatar", "assignment", "school"
    val avatarUrl: String = "",
    val hasAction: Boolean = false,
    val actionTextPrimary: String = "",
    val actionTextSecondary: String = ""
)

data class Assessment(
    val id: String,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val status: String, // "Start Test", "Retake Test", "Continue"
    val progress: Float = 0f, // 0.0 to 1.0
    val timeLeftText: String = "",
    val iconType: String // "psychology", "construction", "favorite"
)
