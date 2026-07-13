package dev.jay739.wakeharder.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Stage 1: re-schedule all persisted alarms from Room here.
        }
    }
}
