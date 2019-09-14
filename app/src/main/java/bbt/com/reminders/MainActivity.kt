package bbt.com.reminders

import android.app.AlarmManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import bbt.com.reminders.helper.AlarmHelper
import bbt.com.reminders.helper.AppConstants
import bbt.com.reminders.helper.PrefUtils
import bbt.com.reminders.helper.showToast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {
    lateinit var mediaPlayer: MediaPlayer
    lateinit var alert: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        registerListeners()
    }

    private fun registerListeners() {
        switchReminder.setOnCheckedChangeListener(this)
    }

    fun init() {

        edtMin.setText(PrefUtils.getLastAlarmTimer(this).toString())
        if (PrefUtils.getAlarmStatus(this)) {
            switchReminder.isChecked = true
            edtMin.isEnabled = false
        } else {
            edtMin.isEnabled = true
        }

        edtMin.setSelection(edtMin.text.length)
        alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        mediaPlayer = MediaPlayer()
        val isAlarm = intent.getBooleanExtra(AppConstants.INTENT_FROM_ALARM, false)
        if (isAlarm) {
            PrefUtils.setAlarmStatus(this, false)
            switchReminder.isChecked = true
            if (switchReminder.isChecked) {
                mediaPlayer = MediaPlayer.create(this, alert)
                mediaPlayer.start()
            }
        }
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        if (switchReminder.isChecked) {
            edtMin.isEnabled = false
            if (edtMin.text.toString().isEmpty() || (edtMin.text.toString()).toInt() < 2) {
                showToast("please enter valid input")
                return
            }
            val cal = Calendar.getInstance()
            cal.add(Calendar.MINUTE, (edtMin.text.toString()).toInt())
            AlarmHelper().setReminder(this, cal.time, AppConstants.ALARM_ID, "sds", "sdsd")
            PrefUtils.setAlarmStatus(this, true)
            PrefUtils.setLastAlarmTimer(this,(edtMin.text.toString()).toInt())
            showToast("Alarm will fire in ${(edtMin.text.toString()).toInt()} mins")
        } else {
            edtMin.isEnabled = true
            PrefUtils.setAlarmStatus(this, false)
            AlarmHelper().cancelAlarm(this, AppConstants.ALARM_ID)
            if (::mediaPlayer.isInitialized) {
                mediaPlayer.stop()
            }
            showToast("Alarm closed")
        }
    }
}
