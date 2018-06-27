package com.y2m.masrnanewstwo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/*
31001142503993
30810072501124
31102152505766
285072502776
125008107631
*/
public class WeatherDetailsActivity extends AppCompatActivity {
    TextView dayView ;
    TextView dateView ;
    TextView dayhighView;
    TextView daylowView ;
    TextView dayconditionView;
    TextView daydescritponView;
    ImageView dayiconView;
    TextView dayView_;
    TextView dateView_;
    TextView dayhighView_;
    TextView daylowView_ ;
    TextView dayconditionView_;
    TextView daydescritponView_;
    ImageView dayiconView_ ;
    Context context;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_details);
        context=this.getApplicationContext();
    }
    @Override
    public void onResume()
    {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        dayView = (TextView) findViewById(R.id.day_detail_day_textview);
        dateView = (TextView) findViewById(R.id.day_detail_date_textview);
        dayhighView = (TextView) findViewById(R.id.day_detail_high_textview);
        daylowView = (TextView) findViewById(R.id.day_detail_low_textview);
        dayconditionView = (TextView) findViewById(R.id.day_detail_forecast_textview);
        daydescritponView = (TextView) findViewById(R.id.day_fcttext_metric);
        dayiconView = (ImageView) findViewById(R.id.day_detail_icon);
        dayView_ = (TextView) findViewById(R.id.detail_day_textview);
        dateView_ = (TextView) findViewById(R.id.detail_date_textview);
        dayhighView_ = (TextView) findViewById(R.id.detail_high_textview);
        daylowView_ = (TextView) findViewById(R.id.detail_low_textview);
        dayconditionView_ = (TextView) findViewById(R.id.detail_forecast_textview);
        daydescritponView_ = (TextView) findViewById(R.id.fcttext_metric);
        dayiconView_ = (ImageView) findViewById(R.id.detail_icon);
        Intent intent = getIntent();
        DayWeatherData dayWeatherData= (DayWeatherData) intent.getSerializableExtra("object");
        Picasso.with(context).load(dayWeatherData.getDay_icon_url()).into(dayiconView);
        Picasso.with(context).load(dayWeatherData.getNight_icon_url()).into(dayiconView_);
        dayView.setText(dayWeatherData.getDate());
        dayView_.setText(dayWeatherData.getDate());
        dateView.setText(dayWeatherData.getDay_title());
        dateView_.setText(dayWeatherData.getNight_title());
        String unit = prefs.getString(context.getString(R.string.pref_selection_key),context.getString(R.string.pref_selection_default));
        if (unit.equals(context .getString(R.string.pref_selection_celsius)))
        {
            dayhighView.setText(dayWeatherData.getHighcelsius()+ " C ");
            dayhighView_.setText(dayWeatherData.getHighcelsius()+ " C ");
            daylowView.setText(dayWeatherData.getLowcelsius()+ " C ");
            daylowView_.setText(dayWeatherData.getLowcelsius()+ " C ");
            daydescritponView.setText(dayWeatherData.getDay_fcttext_metric());
            daydescritponView_.setText(dayWeatherData.getNight_fcttext_metric());
        }
        else
        {
            dayhighView.setText(dayWeatherData.getHighfahrenheit()+ " F");
            dayhighView_.setText(dayWeatherData.getHighfahrenheit()+ " F");
            daylowView.setText(dayWeatherData.getLowfahrenheit()+ " F");
            daylowView_.setText(dayWeatherData.getLowfahrenheit()+ " F");
            daydescritponView.setText(dayWeatherData.getDay_fcttext());
            daydescritponView_.setText(dayWeatherData.getNight_fcttext());
        }
        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("TAG", "share1");
        if (id == R.id.action_settings) {
            Intent MainIntent = new Intent(WeatherDetailsActivity.this, SettingActivity.class);
            startActivity(MainIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
