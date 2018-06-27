package com.y2m.masrnanewstwo;

/**
 * Created by user on 3/28/2016.
 */
public class News {
    private String title;
    private String id;
    private String image;
    private String body;
    private String link;
    private String video;
    private String type;
    private String time;
    private int is_seen;
    public News (String id, String title, String body, String link, String image, String video, String type, String time, int is_seen)
    {
        this.title=title;
        this.id=id;
        this.image=image;
        this.link=link;
        this.body=body;
        this.video=video;
        this.type=type;
        this.time=time;
        this.is_seen=is_seen;
    }
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle()    {
        return title;
    }
    public String getId(){
        return id;
    }
    public String get_image(){
        return image;
    }
    public String getLink()    {
        return link;
    }
    public String getBody(){
        return body;
    }

    public int getIs_seen() {
        return is_seen;
    }

    public void setIs_seen(int is_seen) {
        this.is_seen = is_seen;
    }

    public String toString()
    {
        return title;
    }


}
