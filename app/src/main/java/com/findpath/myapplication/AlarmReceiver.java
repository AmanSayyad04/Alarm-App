package com.findpath.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the alarm when it's triggered, e.g., play the alarm tone
        Uri defaultAlarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        // Play the alarm tone
        Ringtone ringtone = RingtoneManager.getRingtone(context, defaultAlarmUri);
        if (ringtone != null) {
            ringtone.play();
        }
    }
}
