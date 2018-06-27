package com.y2m.masrnanewstwo;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        TextView textview = (TextView) findViewById(R.id.content);
        textview.setClickable(true);
        textview.setMovementMethod(LinkMovementMethod.getInstance());
        String font = "ae_AlMohanad.ttf";
        Typeface tf = Typeface.createFromAsset(this.getAssets(), font);
        textview.setTypeface(tf);
        if(getIntent().hasExtra("num")) {
            int num = getIntent().getExtras().getInt("num");
            Log.d("not1", num + "");
            textview.setText(sharedPreferences.getString("content" + num, "content"));
        }
        else {
            Log.d("not1", sharedPreferences.getInt("last", 0) + "");
            Log.d("not1", sharedPreferences.getString("content" + sharedPreferences.getInt("last", 0), "content"));
            textview.setText(sharedPreferences.getString("content" + sharedPreferences.getInt("last", 0), "content"));
        }
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}