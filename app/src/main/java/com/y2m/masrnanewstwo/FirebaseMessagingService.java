package com.y2m.masrnanewstwo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.RemoteMessage;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by Mohamed Antar on 10/18/2016.
 */
public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{
        SharedPreferences sharedPreferences ;
        public static final int MESSAGE_NOTIFICATION_ID = 435345;

        @Override
        public void onMessageReceived(RemoteMessage remoteMessage) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String message;
                if(remoteMessage.getData().get("product")!=null) {
                        message = remoteMessage.getData().get("product");
                        int i = sharedPreferences.getInt("last", 0);
                        Log.d("notiii", i + "");
                        i++;
                        sharedPreferences.edit().putString("content"+i,message).apply();
                        Log.d("notiii", i + "");
                        sharedPreferences.edit().putInt("last", i).apply();
                        Log.d("notiii", message);
                        createNotification("أخبار مصرنا", message);
                        int badgeCount = sharedPreferences.getInt("badgeCount", 0);
                        badgeCount++;
                        ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
                        ShortcutBadger.applyCount(this, badgeCount);
                        sharedPreferences.edit().putInt("badgeCount", badgeCount).apply();
                }
                else {
                        if (remoteMessage.getData().get("id") != null) {
                                GetNewsData get_recipes = new GetNewsData(this, "http://y2m.esy.es/news/get_one.php?id="+remoteMessage.getData().get("id"));
                                get_recipes.execute();
                        }
                }


        }
        private void createNotification(String title, String body) {
                Log.d("not", body);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                // sharedPreferences.edit().putString("last", i).apply();

                Intent intent = new Intent(this, AllnotificationsActivity.class);
                Intent main = new Intent(this, MainActivity.class);
                Intent backIntent = new Intent(this, MainActivity.class);


                Log.d("not", sharedPreferences.getInt("last", 0) + "");
                Log.d("not", intent.getIntExtra("num", 0) + "");

                PendingIntent pendingIntent = PendingIntent.getActivities(getApplication(), 1, new Intent[]{main, backIntent, intent}, PendingIntent.FLAG_UPDATE_CURRENT);

                Context context = getBaseContext();
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                        .setContentText(body)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setContentIntent(pendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) context
                        .getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(MESSAGE_NOTIFICATION_ID, mBuilder.build());
        }
}