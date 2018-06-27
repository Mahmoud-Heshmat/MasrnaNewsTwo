package com.y2m.masrnanewstwo;

import java.io.Serializable;

/**
 * Created by Mohamed Antar on 2/2/2017.
 */
public class DayWeatherData implements Serializable
{
    private String day_title;
    private String day_icon_url;
    private String day_fcttext;
    private String day_fcttext_metric;
    private String night_title;
    private String night_icon_url;
    private String night_fcttext;
    private String night_fcttext_metric;
    private String date; // Friday,1 April
    private String highfahrenheit;
    private String highcelsius;
    private String lowfahrenheit;
    private String lowcelsius;
    private String conditions;
    private String icon_url;
    public DayWeatherData(String day_title,
                          String day_icon_url,
                          String day_fcttext,
                          String day_fcttext_metric,
                          String night_title,
                          String night_icon_url,
                          String night_fcttext,
                          String night_fcttext_metric,
                          String date,
                          String highfahrenheit,
                          String highcelsius,
                          String lowfahrenheit,
                          String lowcelsius,
                          String conditions,
                          String icon_url)
    {
        this.day_title=day_title;
        this.day_icon_url=day_icon_url;
        this.day_fcttext=day_fcttext;
        this.day_fcttext_metric=day_fcttext_metric;
        this.night_title=night_title;
        this.night_icon_url=night_icon_url;
        this.night_fcttext=night_fcttext;
        this.night_fcttext_metric=night_fcttext_metric;
        this.date=date;
        this.highfahrenheit=highfahrenheit;
        this.highcelsius=highcelsius;
        this.lowfahrenheit=lowfahrenheit;
        this.lowcelsius=lowcelsius;
        this.conditions=conditions;
        this.icon_url=icon_url;
    }

    public String getDay_fcttext() {
        return day_fcttext;
    }

    public String getDay_title() {
        return day_title;
    }

    public void setDay_title(String day_title) {
        this.day_title = day_title;
    }

    public String getDay_icon_url() {
        return day_icon_url;
    }

    public void setDay_icon_url(String day_icon_url) {
        this.day_icon_url = day_icon_url;
    }

    public void setDay_fcttext(String day_fcttext) {
        this.day_fcttext = day_fcttext;
    }

    public String getDay_fcttext_metric() {
        return day_fcttext_metric;
    }

    public void setDay_fcttext_metric(String day_fcttext_metric) {
        this.day_fcttext_metric = day_fcttext_metric;
    }

    public String getNight_title() {
        return night_title;
    }

    public void setNight_title(String night_title) {
        this.night_title = night_title;
    }

    public String getNight_icon_url() {
        return night_icon_url;
    }

    public void setNight_icon_url(String night_icon_url) {
        this.night_icon_url = night_icon_url;
    }

    public String getNight_fcttext() {
        return night_fcttext;
    }

    public void setNight_fcttext(String night_fcttext) {
        this.night_fcttext = night_fcttext;
    }

    public String getNight_fcttext_metric() {
        return night_fcttext_metric;
    }

    public void setNight_fcttext_metric(String night_fcttext_metric) {
        this.night_fcttext_metric = night_fcttext_metric;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHighfahrenheit() {
        return highfahrenheit;
    }

    public void setHighfahrenheit(String highfahrenheit) {
        this.highfahrenheit = highfahrenheit;
    }

    public String getHighcelsius() {
        return highcelsius;
    }

    public void setHighcelsius(String highcelsius) {
        this.highcelsius = highcelsius;
    }

    public String getLowfahrenheit() {
        return lowfahrenheit;
    }

    public void setLowfahrenheit(String lowfahrenheit) {
        this.lowfahrenheit = lowfahrenheit;
    }

    public String getLowcelsius() {
        return lowcelsius;
    }

    public void setLowcelsius(String lowcelsius) {
        this.lowcelsius = lowcelsius;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}
