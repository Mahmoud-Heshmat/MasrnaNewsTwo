package com.y2m.masrnanewstwo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by user on 3/28/2016.
 */
public class NotificationAdapter extends ArrayAdapter<News> {
    private final Context context;
    private final ArrayList<News> NewsArrayList;
    LayoutInflater inflater;
    Filter filter;
    private TextView titleapp,titlenews,time;
    private ImageView image;
    private Button share;
    public NotificationAdapter(Context context, ArrayList<News> array ) {
        super(context, 0 , array);
        this.context = context;
        this.NewsArrayList = array;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item,null);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id", NewsArrayList.get(position).getId());
                intent.putExtra("title", NewsArrayList.get(position).getTitle());
                intent.putExtra("image", NewsArrayList.get(position).get_image());
                intent.putExtra("body", NewsArrayList.get(position).getBody());
                intent.putExtra("link", NewsArrayList.get(position).getLink());
                intent.putExtra("video", NewsArrayList.get(position).getVideo());
                intent.putExtra("time", NewsArrayList.get(position).getTime());
                context.startActivity(intent);
            }
        });
        titleapp=(TextView)convertView.findViewById(R.id.newstitle);
        time=(TextView)convertView.findViewById(R.id.newstime);
        titlenews=(TextView)convertView.findViewById(R.id.title);
        titlenews.setText(NewsArrayList.get(position).getTitle());

        time.setText(getCurrentTimeStamp());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1;
        Date date2;
        try {
            Log.e("Time///////", "Time 1:///////////// "+NewsArrayList.get(position).getTime());
            date1 = dateFormat.parse(NewsArrayList.get(position).getTime());
            date2 = dateFormat.parse(getCurrentTimeStamp());
            Log.e("Time///////", "Time 2:///////////// "+ date1.getTime());
            Log.e("Time///////", "Time 3:///////////// "+ date2.getTime());
            //long dif=date2.getTime()- addHoure(date1.getTime(),2);
            TimeZone tz = TimeZone.getDefault();
            Log.d("time","*********************"+tz.getRawOffset());
            long dif=date2.getTime()- (date1.getTime()+tz.getRawOffset());
            Log.e("Time///////", "Time 3:///////////// "+dif);
            time.setText(getDurationBreakdown(dif));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

        String image_name="";
        image = (ImageView) convertView.findViewById(R.id.imagenews);
        if (NewsArrayList.get(position).get_image().contains("ytimg"))
        {
            String[]imagenames=NewsArrayList.get(position).get_image().split("/");
            image_name=imagenames[(imagenames.length)-2];
        }
        else if (!(NewsArrayList.get(position).get_image().contains("Misrna")))
        {
            String[]imagenames=NewsArrayList.get(position).get_image().split("/");
            image_name=imagenames[(imagenames.length)-1];
        }
        Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/Akhbar_Masrna" + "/" +image_name ));
        Picasso.with(context).load(uri).into(image);
        String font = "ae_AlMohanad.ttf";
        Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
        titlenews.setTypeface(tf);
        titleapp.setTypeface(tf);
        time.setTypeface(tf);
        return convertView;
    }
    public static String getDurationBreakdown(long millis)
    {
        if(millis < 0)
        {
            return " ";
        }
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        StringBuilder sb = new StringBuilder(64);
        if (days>0)
        {
            sb.append(days);
            sb.append(" يوم ");
            sb.append(hours);
            sb.append(" ساعه ");
        }
        else if (hours>0)
        {
            sb.append(hours);
            sb.append(" ساعه  ");
            sb.append(minutes);
            sb.append(" دقيقه  ");
        }
        else
        {
            sb.append(minutes);
            sb.append(" دقيقه  ");
            sb.append(seconds);
            sb.append(" ثانيه  ");
        }
        return(sb.toString());
    }
    public static String getCurrentTimeStamp(){
        /*try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
            return dateFormatLocal.parse( dateFormat.format(new Date()) );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        */
        String date = (android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()).toString());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        Log.d("time","date is : ///////////////////// "+date);
        Date startDate;
        String newDateString="";
        try {
            startDate = df.parse(date);
            newDateString = df.format(startDate);
            System.out.println(newDateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDateString;
    }
}
