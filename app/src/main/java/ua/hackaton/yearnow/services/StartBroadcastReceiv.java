package ua.hackaton.yearnow.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by andriy on 05.04.15.
 */
public class StartBroadcastReceiv extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test", "broadcast autostarted");
        context.startService(new Intent(context, NewService.class));
    }
}
