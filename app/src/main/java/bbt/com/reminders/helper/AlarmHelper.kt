package bbt.com.reminders.helper

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

import java.util.Calendar
import java.util.Date

import bbt.com.reminders.MainActivity


/**
 * Created by anish on 27-03-2017.
 */

class AlarmHelper : BroadcastReceiver() {
    private var alarmManager: AlarmManager? = null

    override fun onReceive(context: Context, intent: Intent) {
//        val title: String = intent.getStringExtra(AppConstants.INTENT_TITLE)
//        val subtitle: String = intent.getStringExtra(AppConstants.INTENT_SUBTITLE)

        if (!PrefUtils.getAlarmStatus(context)) {
            return // means not active
        }

        val intent1 = Intent(context, MainActivity::class.java)
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent1.putExtra(AppConstants.INTENT_FROM_ALARM, true)
//        intent1.putExtra(AppConstants.INTENT_TITLE, title)
//        intent1.putExtra(AppConstants.INTENT_SUBTITLE, subtitle)

        context.startActivity(intent1)
    }

    fun setReminder(context: Context, date: Date, calId: Int, title: String, description: String) {
        cancelAlarm(context, calId)
        val calendar = Calendar.getInstance()
        calendar.time = date
        Log.e("calendar time", "" + calendar)
        val pendingIntent: PendingIntent

        val intent = Intent(context, AlarmHelper::class.java)
        intent.putExtra(AppConstants.INTENT_TITLE, title)
        intent.putExtra(AppConstants.INTENT_SUBTITLE, description)
        pendingIntent = PendingIntent.getBroadcast(context, calId, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = getAlarmManager(context)
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)

    }

    fun cancelAlarm(context: Context, alarmID: Int) { // to cancel the alarm of a specific id.
        val intent = Intent(context, AlarmHelper::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
                context,
                alarmID,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        )
        val alarmManager = getAlarmManager(context)
        alarmManager.cancel(pendingIntent)
    }

    private fun getAlarmManager(context: Context): AlarmManager { //using the alarm service of device

        if (this.alarmManager == null) {
            alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        }
        return alarmManager!!
    }
}