package dev.jay739.wakeharder.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dev.jay739.wakeharder.ring.RingService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val serviceIntent = Intent(context, RingService::class.java)
        context.startForegroundService(serviceIntent)
    }
}
