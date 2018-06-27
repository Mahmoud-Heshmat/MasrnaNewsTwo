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

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.Random;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Created by user on 3/29/2016.
 */
public class GetNewsData {
    private RequestQueue mRequestQueue;
    ParseNews parseRecipe;
    private Context context;
    private String url;
    GetNewsData myobject;
    NewsDBHandler db;
    public static final int MESSAGE_NOTIFICATION_ID = 435345;
    public GetNewsData(Context context, String url) {
        this.context = context;
        this.url = url;
        myobject = this;
        db=new NewsDBHandler(context);
    }
    public void execute() {
        final Cache cache = new DiskBasedCache(context.getCacheDir(), 1); // 1byte cap
        final Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null && !response.equalsIgnoreCase("null") && !response.equalsIgnoreCase("\"Not found !\"")) {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            parseRecipe = new ParseNews(response);
                            final ArrayList<News> News_list = parseRecipe.GetNewsArray();
                            Log.d("count123", "succes ");
                            String title = News_list.get(0).getTitle();
                            String body = News_list.get(0).getBody();
                            String link = News_list.get(0).getLink();
                            String id = News_list.get(0).getId();
                            String image = News_list.get(0).get_image();
                            String type = News_list.get(0).getType();
                            String video = News_list.get(0).getVideo();
                            String time = News_list.get(0).getTime();
                            if ((title == "" && body == "" && link == "" && image == "" && video == "")) {

                            } else if (title.length() > 2 || body.length() > 2 || link.length() > 2 || image.length() > 2 || video.length() > 2) {
                                createNotification_video(id, title, body, link, image, video);
                                db.add_AllNews(News_list);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("last_id", News_list.get(News_list.size() - 1).getId());
                                editor.putBoolean("firstTimedata", true);
                                editor.commit();

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle erro
                        //Toast.makeText(activity, "أتصال الشبكة غير متوفر", Toast.LENGTH_LONG).show();
                    }
                });
        //stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(4 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(stringRequest);
    }
    private void createNotification_video(String id, String name, String body, String link , String image, String video) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int badgeCount = sharedPreferences.getInt("badgeCount", 0);
        badgeCount++;
        ShortcutBadger.removeCount(context); //for 1.1.4+
        ShortcutBadger.applyCount(context, badgeCount);
        sharedPreferences.edit().putInt("badgeCount", badgeCount).apply();
        /*
        Log.d("notiii", body);
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", name);
        intent.putExtra("image", image);
        intent.putExtra("body", body);
        intent.putExtra("link", link);
        intent.putExtra("video", video);
        */
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(context.getApplicationContext(), 1, new Intent[]{ intent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Context context1 = context.getApplicationContext();
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context1)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(name)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setContentIntent(pendingIntent);
        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        NotificationManager mNotificationManager = (NotificationManager) context1
                .getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(m, mBuilder.build());
    }
}