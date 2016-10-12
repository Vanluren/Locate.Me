package xyz.vanluren.locateme;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

class ReminderReceiver extends BroadcastReceiver {
    int notifyId=1;
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder mNotify=new NotificationCompat.Builder(context);
        mNotify.setContentTitle("Coding");
        mNotify.setContentText("INVENTO: Coding competition is going to be conducted today.");
        Intent resultIntent=new Intent(context,ReminderListActivity.class);
        TaskStackBuilder stackBuilder=TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ReminderListActivity.class); //add the to-be-displayed activity to the top of stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mNotify.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager=(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notifyId,mNotify.build());
    }
}