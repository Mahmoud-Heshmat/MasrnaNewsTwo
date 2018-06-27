package com.y2m.masrnanewstwo;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by user on 3/28/2016.
 */
public class WeatherAdapter extends ArrayAdapter<DayWeatherData> {
    private final Context context;
    private final ArrayList<DayWeatherData> WeatherArrayList;
    final SharedPreferences prefs ;

    LayoutInflater inflater;
    Filter filter;
    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_TODAY= 0;
    private static final int VIEW_TYPE_NEXTDAY= 1;
    public WeatherAdapter(Context context, ArrayList<DayWeatherData> array ) {
        super(context, 0 , array);
        this.context = context;
        this.WeatherArrayList = array;
        inflater = LayoutInflater.from(context);
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }
    @Override
    public int getCount() {
        return WeatherArrayList.size();
    }
    @Override
    public DayWeatherData getItem(int position) {
        return WeatherArrayList.get(position);
    }
    @Override
    public int getItemViewType(int position) {
        if (position==0)
            return VIEW_TYPE_TODAY;
        else
            return VIEW_TYPE_NEXTDAY;
    }
    @Override
    public long getItemId(int position) {
        Log.d("Adaptor", "/////////////////////////////////////////////position"+ position);

        return position;
    }
    @Override
    public int getViewTypeCount() {
        Log.d("Adaptor", "/////////////////////////////////////////////VIEW_TYPE_COUNT"+ VIEW_TYPE_COUNT);
        return VIEW_TYPE_COUNT;

    }
    private class ViewHolder {
        private ImageView iconView;
        private TextView dateView;
        private TextView descriptionView;
        private TextView highTempView;
        private TextView lowTempView;
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
                case VIEW_TYPE_TODAY:
                    convertView = inflater.inflate(R.layout.today_item_layout,parent,false);
                    break;
                case VIEW_TYPE_NEXTDAY:
                    convertView = inflater.inflate(R.layout.nextday_item, parent, false);
                    break;
                default:
                    break;
            }
            viewHolder.iconView=(ImageView)convertView.findViewById(R.id.icon);
            viewHolder.dateView=(TextView)convertView.findViewById(R.id.date);
            viewHolder.descriptionView=(TextView)convertView.findViewById(R.id.conditions);
            viewHolder.highTempView=(TextView)convertView.findViewById(R.id.high);
            viewHolder.lowTempView=(TextView)convertView.findViewById(R.id.low);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        Log.d("Adaptor", "/////////////////////////////////////////////position"+ WeatherArrayList.get(position).getDay_title());
        Picasso.with(context).load(WeatherArrayList.get(position).getIcon_url()).into(viewHolder.iconView);
        viewHolder.dateView.setText(WeatherArrayList.get(position).getDate());
        viewHolder.descriptionView.setText(WeatherArrayList.get(position).getConditions());
        String unit = prefs.getString(context.getString(R.string.pref_selection_key),context.getString(R.string.pref_selection_default));
        if (unit.equals(context .getString(R.string.pref_selection_celsius)))
        {
            viewHolder.highTempView.setText(WeatherArrayList.get(position).getHighcelsius() + " C ");
            viewHolder.lowTempView.setText(WeatherArrayList.get(position).getLowcelsius() + " C ");
        }
        else
        {
            viewHolder.highTempView.setText(WeatherArrayList.get(position).getHighfahrenheit() + " F ");
            viewHolder.lowTempView.setText(WeatherArrayList.get(position).getLowfahrenheit() + " F ");
        }
        return convertView;
    }
}
