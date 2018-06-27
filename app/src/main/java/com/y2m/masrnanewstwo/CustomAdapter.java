package com.y2m.masrnanewstwo;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;

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
public class CustomAdapter extends ArrayAdapter<News> {
    private final Context context;
    private final ArrayList<News> NewsArrayList;
    LayoutInflater inflater;
    Filter filter;
    private static final int VIEW_TYPE_COUNT = 3;
    private static final int VIEW_TYPE_News= 0;
    private static final int VIEW_TYPE_Adds= 1;
    private static final int VIEW_TYPE_New= 2;
    public CustomAdapter(Context context, ArrayList<News> array ) {
        super(context, 0 , array);
        this.context = context;
        this.NewsArrayList = array;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return NewsArrayList.size();
    }
    @Override
    public News getItem(int position) {
        return NewsArrayList.get(position);
    }
    @Override
    public int getItemViewType(int position) {
        if(NewsArrayList.get(position).getTime()=="adds")
            return VIEW_TYPE_Adds;
        else if (NewsArrayList.get(position).getIs_seen()==0)
            return VIEW_TYPE_New;
        else
            return VIEW_TYPE_News;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }
    private class ViewHolder {
        private AdView mAdView ;
        private TextView titleapp,titlenews,time;
        private ImageView image;
        private TextView titleapp1,titlenews1,time1;
        private ImageView image1;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder ;
        int type = getItemViewType(position);
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            switch (type) {
                case VIEW_TYPE_Adds:
                    convertView = inflater.inflate(R.layout.addlist_item,parent,false);
                    viewHolder.mAdView = (AdView) convertView.findViewById(R.id.adView);
                    break;
                case VIEW_TYPE_News:
                    convertView = inflater.inflate(R.layout.list_item, parent, false);
                    viewHolder.titleapp=(TextView)convertView.findViewById(R.id.newstitle);
                    viewHolder.time=(TextView)convertView.findViewById(R.id.newstime);
                    viewHolder.titlenews=(TextView)convertView.findViewById(R.id.title);
                    viewHolder.image = (ImageView) convertView.findViewById(R.id.imagenews);
                    break;
                case VIEW_TYPE_New:
                    convertView = inflater.inflate(R.layout.list_item2, parent, false);
                    viewHolder.titleapp1=(TextView)convertView.findViewById(R.id.newstitle);
                    viewHolder.time1=(TextView)convertView.findViewById(R.id.newstime);
                    viewHolder.titlenews1=(TextView)convertView.findViewById(R.id.title);
                    viewHolder.image1 = (ImageView) convertView.findViewById(R.id.imagenews);
                    break;
                default:
                    break;
            }
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();
        if (type == VIEW_TYPE_Adds) {
            AdRequest adRequest = new AdRequest.Builder().build();
            viewHolder.mAdView.loadAd(adRequest);

        }
        else if (type == VIEW_TYPE_News)
        {
            viewHolder.titlenews.setText(NewsArrayList.get(position).getTitle());
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
                viewHolder.time.setText(getDurationBreakdown(dif));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            String image_name="";
            if (!(NewsArrayList.get(position).get_image().contains("Misrna")))
            {
                Picasso.with(context).load(NewsArrayList.get(position).get_image()).into(viewHolder.image);
            }
            String font = "DroidKufi-Regular.ttf";
            Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
            viewHolder.titlenews.setTypeface(tf);
            viewHolder.titleapp.setTypeface(tf);
            viewHolder.time.setTypeface(tf);
        }
        else
        {
            viewHolder.titlenews1.setText(NewsArrayList.get(position).getTitle());
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
                viewHolder.time1.setText(getDurationBreakdown(dif));
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }
            //////////////////////////////////////////////////////
            String image_name="";
            if (!(NewsArrayList.get(position).get_image().contains("Misrna") && (NewsArrayList.get(position).get_image().length()>5)))
            {
                //Picasso.with(context).load(NewsArrayList.get(position).get_image()).into(viewHolder.image1);
            }
            String font = "DroidKufi-Bold.ttf";
            Typeface tf = Typeface.createFromAsset(context.getAssets(), font);
            viewHolder.titlenews1.setTypeface(tf);
            viewHolder.titleapp1.setTypeface(tf);
            viewHolder.time1.setTypeface(tf);
        }
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
    @Override
    public Filter getFilter() {
        if (filter == null)
            filter = new AppFilter<News>(NewsArrayList);
        return filter;
    }
    private class AppFilter<T> extends Filter {
        private ArrayList<T> sourceObjects;
        public AppFilter(ArrayList<T> recipes) {
            sourceObjects = new ArrayList<T>();
            synchronized (this) {
                sourceObjects.addAll(recipes);
            }
        }
        @Override
        protected FilterResults performFiltering(CharSequence chars) {
            String filterSeq = chars.toString().toLowerCase();
            FilterResults result = new FilterResults();
            if (filterSeq != null && filterSeq.length() > 0) {
                ArrayList<T> filter = new ArrayList<T>();
                for (T object : sourceObjects) {
                    // the filtering itself:
                    if (object.toString().contains(filterSeq))
                        filter.add(object);
                }
                result.count = filter.size();
                result.values = filter;
            } else {
                // add all objects
                synchronized (this) {
                    result.values = sourceObjects;
                    result.count = sourceObjects.size();
                }
            }
            return result;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // NOTE: this function is *always* called from the UI thread.
            ArrayList<T> filtered = (ArrayList<T>) results.values;
            notifyDataSetChanged();
            clear();
            for (int i = 0, l = filtered.size(); i < l; i++)
                add((News) filtered.get(i));
            notifyDataSetInvalidated();
        }
    }
}
