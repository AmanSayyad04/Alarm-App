package com.findpath.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class SetAlarm extends AppCompatActivity {
    private TimePicker timePicker;
    private Button selectToneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);


        timePicker = findViewById(R.id.timePicker);
        selectToneButton = findViewById(R.id.selectToneButton);

        selectToneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the tone selection
                // You can use an Intent to open a file picker or choose from predefined tones.
                // For simplicity, we'll use a built-in alarm tone.
                Uri defaultAlarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

                // Play the selected tone
                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), defaultAlarmUri);
                if (ringtone != null) {
                    ringtone.play();
                }
            }
        });
    }

    public void onSaveAlarmButtonClick(View view) {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        // Handle the selected time and alarm tone
        String alarmTime = hour + ":" + minute;
        Toast.makeText(this, "Alarm set for " + alarmTime, Toast.LENGTH_SHORT).show();

        // Schedule the alarm
        scheduleAlarm(hour, minute);
    }

    private void scheduleAlarm(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);

        if (calendar.before(Calendar.getInstance())) {
            // If the time is in the past, schedule for the next day
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
    public void onSnoozeButtonClick(View view) {
        // Implement snooze functionality.
        int snoozeMinutes = 10; // Define the snooze duration (e.g., 10 minutes).

        // Schedule the snooze alarm.
        scheduleSnooze(snoozeMinutes);

        // Notify the user that the alarm has been snoozed.
        Toast.makeText(this, "Alarm snoozed for " + snoozeMinutes + " minutes", Toast.LENGTH_SHORT).show();
    }

    private void scheduleSnooze(int snoozeMinutes) {
        Calendar snoozeTime = Calendar.getInstance();
        snoozeTime.add(Calendar.MINUTE, snoozeMinutes);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        Intent snoozeIntent = new Intent(this, AlarmReceiver.class); // Assuming you have an AlarmReceiver class.
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        alarmManager.set(AlarmManager.RTC_WAKEUP, snoozeTime.getTimeInMillis(), pendingIntent);
    }


}