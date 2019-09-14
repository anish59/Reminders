package bbt.com.reminders.helper

import android.content.Context


object PrefUtils {

    var is_ALARM_ACTIVE = "is_ALARM_ACTIVE"
    var LAST_TIMER_MINUTE = "LAST_TIMER_MINUTE"


    fun setAlarmStatus(context: Context, isActive: Boolean) {
        Prefs.with(context).save(is_ALARM_ACTIVE, isActive)
    }

    fun getAlarmStatus(context: Context): Boolean {
        return Prefs.with(context).getBoolean(is_ALARM_ACTIVE, false)
    }

    fun setLastAlarmTimer(context: Context, isActive: Int) {
        Prefs.with(context).save(LAST_TIMER_MINUTE, isActive)
    }

    fun getLastAlarmTimer(context: Context): Int {
        return Prefs.with(context).getInt(LAST_TIMER_MINUTE, 7)
    }

    /**
     * Demo of storing an object as accessing the same is as below
     */

    /************************************************************************************
     * public static void setNextDateListResponse(Context context, NextDateListResponse nextDateListResponse) {
     * String text = AppApplication.getGson().toJson(nextDateListResponse);
     * Prefs.with(context).save(NEXTDATE, text);
     * }
     *
     * public static NextDateListResponse getNextDateListResponse(Context context) {
     * NextDateListResponse nextDateListResponse = new NextDateListResponse();
     * String text = Prefs.with(context).getString(NEXTDATE, "");
     * if (!TextUtils.isEmpty(text)) {
     * nextDateListResponse = AppApplication.getGson().fromJson(text, NextDateListResponse.class);
     * }
     * return nextDateListResponse;
     * }
     */
}