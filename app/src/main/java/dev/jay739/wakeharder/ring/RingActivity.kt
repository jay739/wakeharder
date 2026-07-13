package dev.jay739.wakeharder.ring

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

class RingActivity : ComponentActivity() {

    private var alarmId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alarmId = intent.getLongExtra(RingService.EXTRA_ALARM_ID, -1)

        setShowWhenLocked(true)
        setTurnScreenOn(true)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        // Stage 1: plain stop button. Stage 2+: routes to the dismiss task instead.
                        Button(onClick = { stopRinging() }) {
                            Text("Stop")
                        }
                    }
                }
            }
        }
    }

    private fun stopRinging() {
        stopService(android.content.Intent(this, RingService::class.java))
        finish()
    }

    override fun onBackPressed() {
        // Intentionally intercepted: dismissing the alarm must go through the task, not the back button.
    }
}
