package com.y2m.masrnanewstwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Collections;

public class Notification_Activity extends AppCompatActivity {
    ArrayAdapter adapter;
    ListView listView;
    int Index;
    int id;
    NewsDBHandler db;
    String title,image, link, body,video,type,time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification2);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2664238069150772/6101650848");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        db=new NewsDBHandler(this.getApplicationContext());

        ArrayList<News> all_Notification= new ArrayList<News>();
        News one_recipe;
        all_Notification=db.getAllNews("2");
        Log.d("getdata", all_Notification.size()+"");
        listView = (ListView) this.findViewById(R.id.category_list);
        ArrayAdapter adapter = (ArrayAdapter) listView.getAdapter();
        Log.d("null", all_Notification.size()+"");
        Collections.reverse(all_Notification);
        adapter = new NotificationAdapter(this, all_Notification);
        Log.d("getdata", adapter.getCount() + "");
        listView.setAdapter(adapter);
        setTitle("الاشــعــارات");
    }
}
