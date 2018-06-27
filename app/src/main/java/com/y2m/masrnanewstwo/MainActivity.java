package com.y2m.masrnanewstwo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.share.Sharer;
import com.facebook.share.model.AppInviteContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.AppInviteDialog;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import bolts.AppLinks;
import me.leolin.shortcutbadger.ShortcutBadger;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    EditText filterText;
    ListView listView;
    int id;
    private RelativeLayout menu_item0,menu_item1,menu_item2,menu_item3,menu_item4,menu_item5;
    private TextView textView0,textView1,textView2,textView3,textView4,textView5,title1,title2;
    private LinearLayout weatherData;
    private TextView date,high,low,conditions;
    private ImageView icon;
    GetALLNews getALLNews;
    InterstitialAd mInterstitialAd;
    CallbackManager callbackManager;
    NewsDBHandler db;
    private float lastTranslate = 0.0f;
    private ImageButton menu;
    private DrawerLayout drawer;
    private FrameLayout frame;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private View view_line;
    ArrayList<News> all_Newses;
    ArrayAdapter adapter;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END))
        {
            drawer.closeDrawer(GravityCompat.END);
        }
        else
        {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "إضغط مره أخري للخروج من التطبيق", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic("user");
        FirebaseInstanceId.getInstance().getToken();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        Uri targetUrl = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null) {
            Log.i("Activity", "App Link Target URL: " + targetUrl.toString());
        }
        db = new NewsDBHandler (MainActivity.this);
        callbackManager = CallbackManager.Factory.create();
        view_line=(View)findViewById(R.id.view_line);
        menu =(ImageButton)findViewById(R.id.menu);
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                }
                else
                {
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        frame = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        {
            @SuppressLint("NewApi")
            public void onDrawerSlide(View drawerView, float slideOffset)
            {
                super.onDrawerSlide(drawerView, slideOffset);
                slideOffset=slideOffset*-1;
                float moveFactor = (view_line.getWidth() * slideOffset);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                {
                    frame.setTranslationX(moveFactor);
                }
                else
                {
                    TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
                    anim.setDuration(0);
                    anim.setFillAfter(true);
                    frame.startAnimation(anim);
                    lastTranslate = moveFactor;
                }
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        AppPreferences.getInstance(getApplicationContext()).incrementLaunchCount();
        showRateAppDialogIfNeeded();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("reset_Data", false) ) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("last_id", "0");
            editor.putBoolean("reset_Data", true);
            editor.putBoolean("resetData", false);
            editor.commit();
        }
        if (!prefs.getBoolean("firstTime", false)) {
            ShareDialog shareDialog = new ShareDialog(this);
            shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    //Toast.makeText(MainActivity.this, "share Success", Toast.LENGTH_SHORT).show();
                    isStoragePermissionGranted();
                }

                @Override
                public void onCancel() {
                    //Toast.makeText(MainActivity.this, "share Cancel", Toast.LENGTH_SHORT).show();
                    isStoragePermissionGranted();
                }

                @Override
                public void onError(FacebookException error) {
                    //Toast.makeText(MainActivity.this, "share Error", Toast.LENGTH_SHORT).show();
                    isStoragePermissionGranted();
                }
            });
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle("أخبار مصرنا")
                        .setContentUrl(Uri.parse("https://fb.me/1387683631245445"))
                        .setContentDescription("لقد قمت بتحميل تطبيق أخبار مصرنا حيث يقدم الاخبار العاجله التى تحدث فى مصر ")
                        .setImageUrl(Uri.parse("http://y2m.esy.es/news/uploadedimages/MisrnaNewsLogo.jpg"))
                        .build();
                shareDialog.show(linkContent);
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        } else {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId("ca-app-pub-2664238069150772/7578384042");
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                }
            });
            requestNewInterstitial();
            mInterstitialAd.setAdListener(new AdListener() {
                public void onAdLoaded() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    }
                }
            });
            isStoragePermissionGranted();
        }

        date=(TextView)findViewById(R.id.date);
        high=(TextView)findViewById(R.id.high);
        low=(TextView)findViewById(R.id.low);
        conditions=(TextView)findViewById(R.id.conditions);
        icon=(ImageView) findViewById(R.id.icon);


        textView0=(TextView)findViewById(R.id.text0);
        textView1=(TextView)findViewById(R.id.text1);
        textView2=(TextView)findViewById(R.id.text2);
        textView3=(TextView)findViewById(R.id.text3);
        textView4=(TextView)findViewById(R.id.text4);
        textView5=(TextView)findViewById(R.id.text5);
        title1=(TextView)findViewById(R.id.title);
        title2=(TextView)findViewById(R.id.nav_title);
        String font = "DroidKufi-Regular.ttf";
        Typeface tf = Typeface.createFromAsset(this.getAssets(), font);
        textView0.setTypeface(tf);
        textView2.setTypeface(tf);
        textView2.setTypeface(tf);
        textView3.setTypeface(tf);
        textView4.setTypeface(tf);
        textView5.setTypeface(tf);
        title1.setTypeface(tf);
        title2.setTypeface(tf);
        date.setTypeface(tf);
        high.setTypeface(tf);
        low.setTypeface(tf);
        conditions.setTypeface(tf);

        menu_item1=(RelativeLayout)findViewById(R.id.menu_item1);
        menu_item2=(RelativeLayout)findViewById(R.id.menu_item2);
        menu_item3=(RelativeLayout)findViewById(R.id.menu_item3);
        menu_item4=(RelativeLayout)findViewById(R.id.menu_item4);
        menu_item5=(RelativeLayout)findViewById(R.id.menu_item5);
        menu_item0=(RelativeLayout)findViewById(R.id.menu_item0);
        menu_item0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(MainIntent);
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        menu_item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String appLinkUrl, previewImageUrl;
                appLinkUrl = "https://fb.me/1387683631245445";
                previewImageUrl = "http://y2m.esy.es/news/uploadedimages/MisrnaNewsLogo.jpg";
                if (AppInviteDialog.canShow()) {
                    AppInviteContent content = new AppInviteContent.Builder()
                            .setApplinkUrl(appLinkUrl)
                            .setPreviewImageUrl(previewImageUrl)
                            .build();
                    AppInviteDialog.show(MainActivity.this, content);
                }
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        menu_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, Notification_Activity.class);
                startActivity(MainIntent);
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        menu_item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + MainActivity.this.getPackageName())));
                } catch (android.content.ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName())));
                }
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        menu_item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id=Y2M+Modern+Technology")));
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        menu_item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(MainIntent);
                drawer.closeDrawer(GravityCompat.END);
            }
        });
        weatherData = (LinearLayout) this.findViewById(R.id.weatherData);
        weatherData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainIntent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(MainIntent);
            }
        });
        all_Newses= new ArrayList<News>();
        all_Newses=db.getAllNews("1");
        Log.d("getdata", all_Newses.size() + "");
        listView = (ListView) this.findViewById(R.id.category_list);
        adapter = (ArrayAdapter) listView.getAdapter();
        Log.d("null", all_Newses.size() + "");
        Collections.reverse(all_Newses);
        adapter = new CustomAdapter(this, all_Newses);
        Log.d("getdata", adapter.getCount() + "");
        listView.setAdapter(adapter);
        filterText = (EditText) findViewById(R.id.inputSearch);
        filterText.addTextChangedListener(filterTextWatcher);
        listView.setTextFilterEnabled(true);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("badgeCount",0);
        editor.commit();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("id", all_Newses.get(position).getId());
                intent.putExtra("title", all_Newses.get(position).getTitle());
                intent.putExtra("image", all_Newses.get(position).get_image());
                intent.putExtra("body", all_Newses.get(position).getBody());
                intent.putExtra("link", all_Newses.get(position).getLink());
                intent.putExtra("video", all_Newses.get(position).getVideo());
                intent.putExtra("time", all_Newses.get(position).getTime());
                MainActivity.this.startActivity(intent);
                if (all_Newses.get(position).getIs_seen()==0)
                    all_Newses.get(position).setIs_seen(1);
                adapter.notifyDataSetChanged();
            }
        });
        //UpdateWatherData();

    }
    @Override
    public void onResume()
    {
        adapter.notifyDataSetChanged();
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        NewsDBHandler wdb =new NewsDBHandler(MainActivity.this);
        ArrayList<DayWeatherData> WeatherData;
        WeatherData=wdb.getAllDay();
        if (WeatherData.size()>0)
        {
            date.setText(WeatherData.get(0).getDate());
            String unit = prefs.getString(this.getString(R.string.pref_selection_key),this.getString(R.string.pref_selection_default));
            if (unit.equals(this.getString(R.string.pref_selection_celsius)))
            {
                high.setText(WeatherData.get(0).getHighcelsius() + " C");
                low.setText(WeatherData.get(0).getLowcelsius() + " C");
            }
            else {
                high.setText(WeatherData.get(0).getHighfahrenheit() + " F");
                low.setText(WeatherData.get(0).getLowfahrenheit() + " F");
            }
            conditions.setText(WeatherData.get(0).getConditions() );
            Picasso.with(MainActivity.this).load(WeatherData.get(0).getIcon_url()).into(icon);
        }
        UpdateWatherData();


        super.onResume();
    }
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                loadData();
                return true;
            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            loadData();
            return true;
        }
    }
    private void showRateAppDialogIfNeeded() {
        boolean bool = AppPreferences.getInstance(getApplicationContext()).getAppRate();
        int i = AppPreferences.getInstance(getApplicationContext()).getLaunchCount();
        if ((bool) && (i == 3)) {
            createAppRatingDialog(getString(R.string.rate_app_title), getString(R.string.rate_app_message)).show();
        }
    }
    private AlertDialog createAppRatingDialog(String rateAppTitle, String rateAppMessage) {
        AlertDialog dialog = new AlertDialog.Builder(this).setPositiveButton(getString(R.string.dialog_app_rate), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                openAppInPlayStore(MainActivity.this);
                AppPreferences.getInstance(MainActivity.this.getApplicationContext()).setAppRate(false);
            }
        }).setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                AppPreferences.getInstance(MainActivity.this.getApplicationContext()).setAppRate(false);
            }
        }).setNeutralButton(getString(R.string.dialog_ask_later), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.dismiss();
                AppPreferences.getInstance(MainActivity.this.getApplicationContext()).resetLaunchCount();
            }
        }).setMessage(rateAppMessage).setTitle(rateAppTitle).create();
        return dialog;
    }
    public static void openAppInPlayStore(Context paramContext)
    {
        paramContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.y2m.masrnanews")));
    }
    boolean tmp=false;
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            loadData();
        }
        else
        {
            if (tmp==false) {
                Toast.makeText(MainActivity.this, "Accept Permission To Application To work Correctly", Toast.LENGTH_SHORT).show();
                isStoragePermissionGranted();
                tmp=true;
            }
            else
            {
                Toast.makeText(MainActivity.this, "Some Application Function not Work Correctly", Toast.LENGTH_SHORT).show();
                loadData();
            }
        }
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    private void loadData()
    {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        getALLNews = new GetALLNews(this, "http://y2m.esy.es/news/get_limit.php?last_id="+prefs.getString("last_id","0"));
        getALLNews.execute();
    }
    private void UpdateWatherData() {
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String place = prefs.getString(this.getString(R.string.pref_selection_place_key),this.getString(R.string.pref_selection_default_place));
        String[] places = getResources().getStringArray(R.array.pref_selection_place_values);
        int index=0;
        index= Arrays.asList(places).indexOf(place);
        index++;
        String URL_STR = "http://y2m.esy.es/news/get_weather_data.php?id="+index;
        Log.d("url","url"+URL_STR);
        GetWeatherData getData=new GetWeatherData(MainActivity.this,URL_STR);
        getData.execute();
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequest);
    }
    private TextWatcher filterTextWatcher = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            adapter = (ArrayAdapter) listView.getAdapter();
            if (adapter != null) {
                adapter.getFilter().filter(s);
                Log.d("filter", "filter 1");

            } else {
                Log.d("filter", "no filter availible");
            }
        }
    };
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }
    public class GetWeatherData {
        private final String LOG_TAG = GetWeatherData.class.getSimpleName();
        private RequestQueue mRequestQueue;
        private Context context;
        private String url;
        public ArrayList<DayWeatherData> WeatherData;
        private final String TAG = MainActivity.class.getSimpleName();
        public GetWeatherData(Context context, String url) {
            this.context = context;
            this.url = url;
        }
        public void execute() {
            final Cache cache = new DiskBasedCache(context.getCacheDir(), 1024 * 1024); // 1 M byte cap
            final Network network = new BasicNetwork(new HurlStack());

            mRequestQueue = new RequestQueue(cache, network);
            mRequestQueue.start();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //your code
                            Log.d(TAG, "The Result is :" + response);
                            try {
                                WeatherData=ParseJsonRespond(response);

                                InsertInDataBase(WeatherData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            stringRequest.setShouldCache(false);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(4 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(stringRequest);
        }
        private void InsertInDataBase(ArrayList<DayWeatherData> WeatherData) {
            NewsDBHandler wdb =new NewsDBHandler(context);
            int deleted = 0;
            deleted = wdb.DeleteWeatherData();
            Log.d(LOG_TAG, "//////////////////////////////////////////////////Row Deleted From " + "Table " + deleted);
            int inserted = 0;
            wdb.add_AllWeatherData(WeatherData);
            Log.d(LOG_TAG, "////////////////////////////////////////////////// Inserted " + "Table " + inserted);
        }
        private ArrayList<DayWeatherData> ParseJsonRespond(String forecastJsonStr) throws JSONException {
            ArrayList<DayWeatherData> WeatherData = new ArrayList<DayWeatherData>();
            JSONObject forecastJson = new JSONObject(forecastJsonStr);
            JSONObject forecast = forecastJson.getJSONObject("forecast");
            JSONObject txt_forecast = forecast.getJSONObject("txt_forecast");
            JSONObject simpleforecast = forecast.getJSONObject("simpleforecast");
            JSONArray forecastday_txt = txt_forecast.getJSONArray("forecastday");
            JSONArray forecastday_simple= simpleforecast.getJSONArray("forecastday");

            String day_title;
            String day_icon_url;
            String day_fcttext;
            String day_fcttext_metric;
            String night_title;
            String night_icon_url;
            String night_fcttext;
            String night_fcttext_metric;

            String Date;
            String day;
            String year;
            String monthname;
            String weekday;

            String highfahrenheit;
            String highcelsius;
            String lowfahrenheit;
            String lowcelsius;
            String conditions;
            String icon_url;
            JSONObject dayOpj;
            for (int i = 0,j=0; i < forecastday_simple.length(); i++,j++)
            {
                dayOpj = forecastday_txt.getJSONObject(j);
                day_title=dayOpj.getString("title");
                day_icon_url=dayOpj.getString("icon_url");
                day_fcttext=dayOpj.getString("fcttext");
                day_fcttext_metric=dayOpj.getString("fcttext_metric");
                j++;
                dayOpj = forecastday_txt.getJSONObject(j);

                night_title=dayOpj.getString("title");
                night_icon_url=dayOpj.getString("icon_url");
                night_fcttext=dayOpj.getString("fcttext");
                night_fcttext_metric=dayOpj.getString("fcttext_metric");
                dayOpj = forecastday_simple.getJSONObject(i);
                JSONObject date=dayOpj.getJSONObject("date");
                day=date.getString("day");
                year=date.getString("year");
                monthname=date.getString("monthname");
                weekday=date.getString("weekday");
                JSONObject high=dayOpj.getJSONObject("high");
                highcelsius =high.getString("celsius");
                highfahrenheit=high.getString("fahrenheit");
                JSONObject low=dayOpj.getJSONObject("low");
                lowcelsius =low.getString("celsius");
                lowfahrenheit=low.getString("fahrenheit");
                conditions=dayOpj.getString("conditions");
                icon_url=dayOpj.getString("icon_url");

                Date=weekday +", "+day+" "+monthname;
                DayWeatherData tmp=new DayWeatherData (day_title,
                        day_icon_url,
                        day_fcttext,
                        day_fcttext_metric,
                        night_title,
                        night_icon_url,
                        night_fcttext,
                        night_fcttext_metric,
                        Date,
                        highfahrenheit,
                        highcelsius,
                        lowfahrenheit,
                        lowcelsius,
                        conditions,
                        icon_url);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////day_title"+ day_title);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////day_icon_url"+ day_icon_url);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////day_fcttext"+ day_fcttext);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////day_fcttext"+ day_fcttext_metric);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////night_title"+ night_title);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////night_icon_url"+ night_icon_url);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////night_fcttext"+ night_fcttext);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////night_fcttext_metric"+ night_fcttext_metric);

                Log.d(LOG_TAG, "//////////////////////////////////////////////////Date"+ Date);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////day"+ day);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////year"+ year);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////monthname"+ monthname);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////weekday"+ weekday);

                Log.d(LOG_TAG, "//////////////////////////////////////////////////highfahrenheit"+ highfahrenheit);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////highcelsius"+ highcelsius);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////lowfahrenheit"+ lowfahrenheit);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////lowcelsius"+ lowcelsius);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////conditions"+ conditions);
                Log.d(LOG_TAG, "//////////////////////////////////////////////////icon_url"+ icon_url);
                WeatherData.add(tmp);
            }
            return WeatherData;
        }
    }
    public class GetALLNews {
        private RequestQueue mRequestQueue;
        ParseNews parsenews;
        private Context context;
        private String url;
        GetALLNews myobject;
        SharedPreferences prefs;
        int i;
        NewsDBHandler db;
        public GetALLNews(Context context, String url) {
            this.context = context;
            this.url = url;
            myobject = this;
            db=new NewsDBHandler(context);
        }
        public void execute() {
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
            final Cache cache = new DiskBasedCache(context.getCacheDir(), 1); // 1byte cap
            final Network network = new BasicNetwork(new HurlStack());
            mRequestQueue = new RequestQueue(cache, network);
            Log.d("count123", "execute ");
            mRequestQueue.start();
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("count123", "response ");
                            if (response != null && !response.equalsIgnoreCase("null") && !response.equalsIgnoreCase("\"Not found !\"")) {
                                parsenews = new ParseNews(response);
                                final ArrayList<News> News_list =  parsenews.GetNewsArray();
                                Log.d("count123", "succes ");
                                if (News_list!=null)
                                {
                                    db.add_AllNews(News_list);
                                    int count =db.getNewsCount();
                                    if (all_Newses.size()==0)
                                    {
                                        all_Newses.addAll(News_list);
                                        Collections.reverse(all_Newses);
                                        adapter.notifyDataSetChanged();
                                    }
                                    if (count>0 &&News_list.size()>0)
                                    {
                                        SharedPreferences.Editor editor = prefs.edit();
                                        editor.putString("last_id", News_list.get(News_list.size() - 1).getId());
                                        editor.putBoolean("firstTimedata", true);
                                        editor.commit();
                                    }

                                }
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle erro
                            if(!prefs.getBoolean("firstTimedata",false)) {
                                new android.support.v7.app.AlertDialog.Builder(context)
                                        .setTitle("مشكلة أتصال")
                                        .setMessage("يوجد مشكله فى الاتصال بالانترنت تأكد من الاتصال")
                                        .setPositiveButton("إعادة التحميل", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                myobject.execute();
                                                // continue with delete
                                            }
                                        })
                                        .setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                            //Toast.makeText(activity, "أتصال الشبكة غير متوفر", Toast.LENGTH_LONG).show();
                        }
                    });
            //stringRequest.setShouldCache(false);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(4 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(stringRequest);
        }
    }
}
