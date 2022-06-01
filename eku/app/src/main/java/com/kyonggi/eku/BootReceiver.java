package com.kyonggi.eku;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.GregorianCalendar;

public class BootReceiver extends BroadcastReceiver {
    public AlarmManager alarmManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            GregorianCalendar twopm = new GregorianCalendar();
            alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            twopm.set(GregorianCalendar.HOUR_OF_DAY, 8);
            twopm.set(GregorianCalendar.MINUTE, 30);
            twopm.set(GregorianCalendar.SECOND, 0);
            twopm.set(GregorianCalendar.MILLISECOND, 0);
            if(twopm.before(new GregorianCalendar())){
                twopm.add(GregorianCalendar.DAY_OF_MONTH, 1);
            }
            PendingIntent alarmReceiver;
            alarmReceiver = PendingIntent.getBroadcast(context,1, new Intent(context,AlarmReceiver.class),0);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, twopm.getTimeInMillis()+(1000*60*60*24), alarmReceiver);
            } else {
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, twopm.getTimeInMillis()+(1000*60*60*24), alarmReceiver);
            }

        }
    }


}