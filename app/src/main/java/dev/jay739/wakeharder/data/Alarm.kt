package dev.jay739.wakeharder.data

// Stage 1: Room entity for a persisted alarm (time, repeat days, dismiss task type).
data class Alarm(
    val id: Long = 0,
    val hour: Int,
    val minute: Int,
)
