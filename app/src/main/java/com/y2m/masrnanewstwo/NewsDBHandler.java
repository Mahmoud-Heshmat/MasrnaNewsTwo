package com.y2m.masrnanewstwo;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Antar on 10/19/2016.
 */
public class NewsDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "NewsDB";

    private static final String TABLE_NAME1= "News_table";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE= "name";
    private static final String KEY_BODY= "body";
    private static final String KEY_LINK= "link";
    private static final String KEY_IMAGE= "image";
    private static final String KEY_VIDEO= "video";
    private static final String KEY_TYPE= "type";
    private static final String KEY_TIME= "time";
    private static final String KEY_IS_SEEN= "isseen";

    private static final String TABLE_NAME2= "weather_table";
    public static final String KEY_Day_ID= "day_id";
    public static final String KEY_day_title= "day_title";
    public static final String KEY_day_icon_url= "day_icon_url";
    public static final String KEY_day_fcttext= "day_fcttext";
    public static final String KEY_day_fcttext_metric= "day_fcttext_metric";
    public static final String KEY_night_title= "night_title";
    public static final String KEY_night_icon_url= "night_icon_url";
    public static final String KEY_night_fcttext= "night_fcttext";
    public static final String KEY_night_fcttext_metric= "night_fcttext_metric";
    public static final String KEY_date= "date";
    public static final String KEY_highfahrenheit= "highfahrenheit";
    public static final String KEY_highcelsius= "highcelsius";
    public static final String KEY_lowfahrenheit= "lowfahrenheit";
    public static final String KEY_lowcelsius= "lowcelsius";
    public static final String KEY_conditions= "conditions";
    public static final String KEY_icon_url= "icon_url";

    SharedPreferences prefs;
    public NewsDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NEWS_TABLE =
                "CREATE TABLE " + TABLE_NAME1+ "(" +
                        KEY_ID+ " INTEGER PRIMARY KEY," +
                        KEY_TITLE+ " TEXT," +
                        KEY_BODY+ " TEXT," +
                        KEY_LINK+ " TEXT," +
                        KEY_IMAGE+ " TEXT," +
                        KEY_VIDEO+ " TEXT," +
                        KEY_TYPE+ " TEXT," +
                        KEY_TIME+ " TEXT," +
                        KEY_IS_SEEN+ " INTEGER" + ")";
        final String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_NAME2 + " (" +
                KEY_Day_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_day_title + " TEXT , " +
                KEY_day_icon_url + " TEXT , " +
                KEY_day_fcttext + " TEXT , " +
                KEY_day_fcttext_metric + " TEXT, " +
                KEY_night_title + " TEXT, " +
                KEY_night_icon_url + " TEXT, " +
                KEY_night_fcttext + " TEXT , " +
                KEY_night_fcttext_metric + " TEXT , " +
                KEY_date + " TEXT , " +
                KEY_highfahrenheit + " TEXT , " +
                KEY_highcelsius + " TEXT , " +
                KEY_lowfahrenheit + " TEXT, " +
                KEY_lowcelsius + " TEXT, " +
                KEY_conditions + " TEXT, " +
                KEY_icon_url + " TEXT );";

        db.execSQL(CREATE_NEWS_TABLE);
        db.execSQL(CREATE_WEATHER_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);

        onCreate(db);
    }
    public long addWeather(DayWeatherData dayWeatherData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_day_title, dayWeatherData.getDay_title());
        values.put(KEY_day_icon_url, dayWeatherData.getDay_icon_url());
        values.put(KEY_day_fcttext, dayWeatherData.getDay_fcttext());
        values.put(KEY_day_fcttext_metric, dayWeatherData.getDay_fcttext_metric());
        values.put(KEY_night_title, dayWeatherData.getNight_title());
        values.put(KEY_night_icon_url, dayWeatherData.getNight_icon_url());
        values.put(KEY_night_fcttext, dayWeatherData.getNight_fcttext());
        values.put(KEY_night_fcttext_metric, dayWeatherData.getNight_fcttext_metric());
        values.put(KEY_date, dayWeatherData.getDate());
        values.put(KEY_highfahrenheit, dayWeatherData.getHighfahrenheit());
        values.put(KEY_highcelsius, dayWeatherData.getHighcelsius());
        values.put(KEY_lowfahrenheit, dayWeatherData.getLowfahrenheit());
        values.put(KEY_lowcelsius, dayWeatherData.getLowcelsius());
        values.put(KEY_conditions, dayWeatherData.getConditions());
        values.put(KEY_icon_url, dayWeatherData.getIcon_url());

        long i=db.insert(TABLE_NAME2, null, values);
        Log.d("Insert","I = " +i+" "+dayWeatherData.getDay_title());
        db.close();
        return i;
    }
    public long add_AllWeatherData(List<DayWeatherData> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        long i=0;
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (DayWeatherData dayWeatherData : list) {
                values.put(KEY_day_title, dayWeatherData.getDay_title());
                values.put(KEY_day_icon_url, dayWeatherData.getDay_icon_url());
                values.put(KEY_day_fcttext, dayWeatherData.getDay_fcttext());
                values.put(KEY_day_fcttext_metric, dayWeatherData.getDay_fcttext_metric());
                values.put(KEY_night_title, dayWeatherData.getNight_title());
                values.put(KEY_night_icon_url, dayWeatherData.getNight_icon_url());
                values.put(KEY_night_fcttext, dayWeatherData.getNight_fcttext());
                values.put(KEY_night_fcttext_metric, dayWeatherData.getNight_fcttext_metric());
                values.put(KEY_date, dayWeatherData.getDate());
                values.put(KEY_highfahrenheit, dayWeatherData.getHighfahrenheit());
                values.put(KEY_highcelsius, dayWeatherData.getHighcelsius());
                values.put(KEY_lowfahrenheit, dayWeatherData.getLowfahrenheit());
                values.put(KEY_lowcelsius, dayWeatherData.getLowcelsius());
                values.put(KEY_conditions, dayWeatherData.getConditions());
                values.put(KEY_icon_url, dayWeatherData.getIcon_url());
                i = db.insert(TABLE_NAME2, null, values);
                Log.d("Insert", "I = " + i + " " + dayWeatherData.getDay_title());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return i;
    }
    public ArrayList<DayWeatherData> getAllDay() {
        ArrayList<DayWeatherData> DataList = new ArrayList<DayWeatherData>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor!=null) {
            if (cursor.moveToFirst()) {
                do {
                    DayWeatherData dayWeatherData = new DayWeatherData(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9),
                            cursor.getString(10),
                            cursor.getString(11),
                            cursor.getString(12),
                            cursor.getString(13),
                            cursor.getString(14),
                            cursor.getString(15));
                    DataList.add(dayWeatherData);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        db.close();
        return DataList;
    }
    public int getDayCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    public int DeleteWeatherData() {
        String countQuery = "Delete FROM " + TABLE_NAME2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public long addNews(News newsItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, newsItem.getId());
        values.put(KEY_TITLE, newsItem.getTitle());
        values.put(KEY_BODY, newsItem.getBody());
        values.put(KEY_LINK, newsItem.getLink());
        values.put(KEY_IMAGE, newsItem.getTime());
        values.put(KEY_VIDEO, newsItem.getVideo());
        values.put(KEY_TYPE, newsItem.getType());
        values.put(KEY_TIME, newsItem.getTime());
        values.put(KEY_IS_SEEN, newsItem.getIs_seen());
        long i=db.insert(TABLE_NAME1, null, values);
        Log.d("Insert","I = " +i+" "+newsItem.getTitle());
        db.close();
        return i;
    }
    public long add_AllNews(List<News> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        long i=0;
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (News newsItem : list) {
                values.put(KEY_ID, newsItem.getId());
                values.put(KEY_TITLE, newsItem.getTitle());
                values.put(KEY_BODY, newsItem.getBody());
                values.put(KEY_LINK, newsItem.getLink());
                values.put(KEY_IMAGE, newsItem.getTime());
                values.put(KEY_VIDEO, newsItem.getVideo());
                values.put(KEY_TYPE, newsItem.getType());
                values.put(KEY_TIME, newsItem.getTime());
                values.put(KEY_IS_SEEN, newsItem.getIs_seen());

                i = db.insert(TABLE_NAME1, null, values);
                Log.d("Insert", "I = " + i + " " + newsItem.getTitle());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return i;
    }
    public News getNews(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME1, new String[]{
                KEY_ID, KEY_TITLE,KEY_BODY,KEY_LINK,KEY_IMAGE,KEY_VIDEO,KEY_TYPE,KEY_TIME,KEY_IS_SEEN}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
            News news = new News(cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    Integer.valueOf(cursor.getString(8)));
        cursor.close();
        db.close();
        return news ;
    }
    public ArrayList<News> getAllNews(String type) {
        ArrayList<News> DataList = new ArrayList<News>();
        int count=0;
        count=getNewsCount();
        if (count>300)
            count-=300;
        String selectQuery = "SELECT * FROM " + TABLE_NAME1+" WHERE "+KEY_TYPE+ " = " +type+" AND "+KEY_ID +" > " + String.valueOf(count) ;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        count=0;
        News tmp =new News("adds","adds","adds","adds","adds","adds","adds","adds",0);
        if (cursor.moveToFirst()) {
            do {
                count++;
                News news = new News(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        Integer.valueOf(cursor.getString(8)));
                DataList.add(news);
                if (!type.equals("2"))
                    if (count % 3 == 0) {
                        DataList.add(tmp);
                    }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return DataList;
    }
    public  boolean CheckIsSeenOrNot(int id) {
        String Query = "Select * from " + TABLE_NAME1+ " where " + KEY_ID + " = " + String.valueOf(id) + " and "+KEY_IS_SEEN+" = 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }
    public int getNewsCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    public int updateNews(News newsItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, newsItem.getId());
        values.put(KEY_TITLE, newsItem.getTitle());
        values.put(KEY_BODY, newsItem.getBody());
        values.put(KEY_LINK, newsItem.getLink());
        values.put(KEY_IMAGE, newsItem.getTime());
        values.put(KEY_VIDEO, newsItem.getVideo());
        values.put(KEY_TYPE, newsItem.getType());
        values.put(KEY_TIME, newsItem.getTime());
        values.put(KEY_IS_SEEN, newsItem.getIs_seen());
        int count=db.update(TABLE_NAME1, values, KEY_ID + " = ? ",
                new String[]{String.valueOf(newsItem.getId())});
        db.close();
        return count;
    }
}