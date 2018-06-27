package com.y2m.masrnanewstwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class WeatherActivity extends AppCompatActivity {
    private WeatherAdapter myadp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2664238069150772/6101650848");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        ListView listView = (ListView)findViewById(R.id.weatherListView);
        NewsDBHandler db=new NewsDBHandler(this);
        final ArrayList<DayWeatherData> WeatherData;
        WeatherData=db.getAllDay();
        Log.d("Test", "////////////////////////////////////////////////// size"+ WeatherData.size());
        myadp = new WeatherAdapter(this, WeatherData);
        setTitle("حالة الطقس");
        listView.setAdapter(myadp);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(WeatherActivity.this, WeatherDetailsActivity.class);
                intent.putExtra("object", WeatherData.get(position));
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public void onResume()
    {
        myadp.notifyDataSetChanged();
        super.onResume();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("TAG", "share1");
        if (id == R.id.action_settings) {
            Intent MainIntent = new Intent(WeatherActivity.this, SettingActivity.class);
            startActivity(MainIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
