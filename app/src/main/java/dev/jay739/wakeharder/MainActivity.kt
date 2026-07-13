package dev.jay739.wakeharder

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import dev.jay739.wakeharder.alarm.AlarmScheduler
import dev.jay739.wakeharder.data.Alarm
import dev.jay739.wakeharder.data.AppDatabase
import kotlinx.coroutines.launch
import java.util.Calendar

class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { /* no-op: ring notification still posts, just silently if denied pre-33 behavior */ }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            WakeharderApp(
                onScheduleTestAlarm = { hour, minute -> scheduleTestAlarm(hour, minute) },
                canScheduleExact = { AlarmScheduler.canScheduleExact(this) },
                onRequestExactAlarmPermission = { requestExactAlarmPermission() },
            )
        }
    }

    private fun scheduleTestAlarm(hour: Int, minute: Int) {
        lifecycleScope.launch {
            val dao = AppDatabase.get(applicationContext).alarmDao()
            val id = dao.upsert(Alarm(hour = hour, minute = minute))
            val alarm = dao.getById(id) ?: return@launch
            AlarmScheduler.schedule(applicationContext, alarm)
        }
    }

    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).apply {
                data = Uri.parse("package:$packageName")
            }
            startActivity(intent)
        }
    }
}

@Composable
fun WakeharderApp(
    onScheduleTestAlarm: (Int, Int) -> Unit,
    canScheduleExact: () -> Boolean,
    onRequestExactAlarmPermission: () -> Unit,
) {
    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var status by remember { mutableStateOf("wakeharder") }
                Text(status)

                Button(onClick = {
                    if (!canScheduleExact()) {
                        onRequestExactAlarmPermission()
                        status = "Grant exact alarm permission, then try again"
                        return@Button
                    }
                    val now = Calendar.getInstance()
                    val minute = (now.get(Calendar.MINUTE) + 1) % 60
                    onScheduleTestAlarm(now.get(Calendar.HOUR_OF_DAY), minute)
                    status = "Test alarm set for next minute mark"
                }) {
                    Text("Schedule test alarm (1 min out)")
                }
            }
        }
    }
}
