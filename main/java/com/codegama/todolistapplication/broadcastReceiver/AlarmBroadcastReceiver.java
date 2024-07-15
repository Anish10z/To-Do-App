package com.codegama.todolistapplication.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.codegama.todolistapplication.activity.AlarmActivity;

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "AlarmBroadcastReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Alarm triggered");

        // Extract data from the intent
        String title = intent.getStringExtra("TITLE");
        String desc = intent.getStringExtra("DESC");
        String date = intent.getStringExtra("DATE");
        String time = intent.getStringExtra("TIME");

        Log.d(TAG, "Received alarm for: " + title + ", " + date + ", " + time);

        // Start the AlarmActivity
        Intent activityIntent = new Intent(context, AlarmActivity.class);
        activityIntent.putExtra("TITLE", title);
        activityIntent.putExtra("DESC", desc);
        activityIntent.putExtra("DATE", date);
        activityIntent.putExtra("TIME", time);
        activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityIntent);
    }
}
