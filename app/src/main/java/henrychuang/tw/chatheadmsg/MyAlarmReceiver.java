package henrychuang.tw.chatheadmsg;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
        import java.util.ArrayList;


/**
 * Created by Bogdan Tirca on 04.06.2015.
 */
public class MyAlarmReceiver extends BroadcastReceiver {

    public Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        // sendNotification("alarm", "ente alarm");
        // BaseApp.getApp().mixtrack("enter alarm", "");
        mContext = context;

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())){
            Log.v("dobrin","boot alarm");
        } else {
            Log.v("dobrin", "alarm");
           // sendNotification("test", "test");
            //cancel(BaseApp.getApp().getApplicationContext());
            //start(BaseApp.getApp().getApplicationContext());
        }
    }

    public static void start(Context context) {
        cancel(context);
        Intent alarmIntent = new Intent(context, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) BaseApp.getApp().getApplicationContext(). getSystemService(Context.ALARM_SERVICE);
        int interval =  3;
        int minute =  60 * 1000;
        // interval = getPower(interval, Preferences.getAlarmMultiplier() - 1)
        //  Log.v("dobrin",Integer.toString(interval) + "multiplier : " + Integer.toString(Preferences.getAlarmMultiplier()));
        //  sendNotification("alarm", Integer.toString(interval) + "multiplier : " + Integer.toString(Preferences.getAlarmMultiplier()));
        if(MyDialog.active == false){
            Intent it = new Intent(BaseApp.getApp().getApplicationContext(),MyDialog.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            BaseApp.getApp().startActivity(it);
        }
        manager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis() + interval , interval * minute , pendingIntent);
    }

    private static void cancel(Context context) {
        Intent alarmIntent = new Intent(context, MyAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);
    }

    public void sendNotification(String from,String notificationDetails) {
        // Create an explicit content Intent that starts the main Activity.
        Intent notificationIntent = new Intent(BaseApp.getApp().getApplicationContext(), Main.class);

        // Construct a task stack.
        android.app.TaskStackBuilder stackBuilder = android.app.TaskStackBuilder.create(BaseApp.getApp().getApplicationContext());

        // Add the main Activity to the task stack as the parent.
        stackBuilder.addParentStack(Main.class);

        // Push the content Intent onto the stack.
        stackBuilder.addNextIntent(notificationIntent);

        // Get a PendingIntent containing the entire back stack.
        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(BaseApp.getApp().getApplicationContext());

        // Define the notification settings.
        builder.setSmallIcon(R.drawable.ic_launcher)
                // In a real app, you may want to use a library like Volley
                // to decode the Bitmap.
                .setLargeIcon(BitmapFactory.decodeResource(BaseApp.getApp().getApplicationContext().getResources(),
                        R.drawable.ic_launcher))
                .setContentTitle(from)
                .setContentText(notificationDetails)
                .setContentIntent(notificationPendingIntent);

        // Dismiss notification once the user touches it.
        NotificationCompat.BigTextStyle bigTextStyle =
                new NotificationCompat.BigTextStyle();
        bigTextStyle.setBigContentTitle(from);
        bigTextStyle.bigText(notificationDetails);
        builder.setContentText(notificationDetails);
        builder.setStyle(bigTextStyle);

        builder.setAutoCancel(true);

        // Get an instance of the Notification manager
        NotificationManager mNotificationManager =
                (NotificationManager) BaseApp.getApp().getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        // Issue the notification
        mNotificationManager.notify((int)(System.currentTimeMillis()%10000), builder.build());
    }
}
