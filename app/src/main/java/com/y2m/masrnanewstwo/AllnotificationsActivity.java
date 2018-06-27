package com.y2m.masrnanewstwo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AllnotificationsActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allnotifications);

        ListView listView = (ListView) findViewById(R.id.list);
        context = this;
        // Defined Array values to show in ListView
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int last = sharedPreferences.getInt("last", 0);
        Log.d("not", last + "");
        String[] values = new String[last] ;

        for(int i=1;i<=last;i++)
        {

            values[i-1]=sharedPreferences.getString("content" + i, "content");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.notification_item, R.id.notification_content, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                Intent intent = new Intent(context, NotificationActivity.class);
                intent.putExtra("num", position + 1 );

                startActivity(intent);

            }
        });
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_allnotifications, menu);
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
