package dev.jay739.wakeharder.alarm

import java.util.Calendar
import org.junit.Assert.assertEquals
import org.junit.Test

class AlarmSchedulerTest {

    private fun at(hour: Int, minute: Int, second: Int = 0): Calendar =
        Calendar.getInstance().apply {
            set(2026, Calendar.JULY, 14, hour, minute, second)
            set(Calendar.MILLISECOND, 0)
        }

    @Test
    fun `alarm later today fires today`() {
        val now = at(6, 0)
        val trigger = AlarmScheduler.nextTriggerMillis(7, 30, now)
        val cal = Calendar.getInstance().apply { timeInMillis = trigger }
        assertEquals(14, cal.get(Calendar.DAY_OF_MONTH))
        assertEquals(7, cal.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, cal.get(Calendar.MINUTE))
        assertEquals(0, cal.get(Calendar.SECOND))
    }

    @Test
    fun `alarm earlier today rolls to tomorrow`() {
        val now = at(8, 0)
        val trigger = AlarmScheduler.nextTriggerMillis(7, 30, now)
        val cal = Calendar.getInstance().apply { timeInMillis = trigger }
        assertEquals(15, cal.get(Calendar.DAY_OF_MONTH))
        assertEquals(7, cal.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, cal.get(Calendar.MINUTE))
    }

    @Test
    fun `alarm at exactly now fires now, not tomorrow`() {
        val now = at(7, 30)
        val trigger = AlarmScheduler.nextTriggerMillis(7, 30, now)
        assertEquals(now.timeInMillis, trigger)
    }

    @Test
    fun `seconds are zeroed so a mid-minute set does not drift`() {
        val now = at(6, 0, second = 45)
        val trigger = AlarmScheduler.nextTriggerMillis(7, 30, now)
        val cal = Calendar.getInstance().apply { timeInMillis = trigger }
        assertEquals(0, cal.get(Calendar.SECOND))
        assertEquals(0, cal.get(Calendar.MILLISECOND))
    }

    @Test
    fun `midnight alarm set during the day rolls to next midnight`() {
        val now = at(13, 0)
        val trigger = AlarmScheduler.nextTriggerMillis(0, 0, now)
        val cal = Calendar.getInstance().apply { timeInMillis = trigger }
        assertEquals(15, cal.get(Calendar.DAY_OF_MONTH))
        assertEquals(0, cal.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, cal.get(Calendar.MINUTE))
    }
}
