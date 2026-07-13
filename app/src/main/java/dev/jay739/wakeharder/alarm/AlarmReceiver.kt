package dev.jay739.wakeharder.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dev.jay739.wakeharder.ring.RingService

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val alarmId = AlarmScheduler.alarmIdFromIntent(intent)
        val serviceIntent = Intent(context, RingService::class.java).apply {
            putExtra(RingService.EXTRA_ALARM_ID, alarmId)
        }
        context.startForegroundService(serviceIntent)
    }
}
