package com.y2m.masrnanewstwo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener{
    public static FragmentManager mfragmentManager;
    String s_title ,id,s_link,s_image,s_body,s_video,s_time,share="";
    private static final int RECOVERY_REQUEST = 1;
    Context mycontext;
    private ShareActionProvider mShareActionProvider;
    private TextView titel,content,link;
    private View video_view;
    private ImageView image;
    private Button yotube;
    private YouTubePlayerView youTubeView;
    String YOUTUBE_API_KEY ="AIzaSyDBF2JoELAb21Pa6sC5PCga2X96yoIxSQg";
    CallbackManager callbackManager;
    NewsDBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        FacebookSdk.sdkInitialize(getApplicationContext());
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2664238069150772/6101650848");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mycontext=this;
        String font = "ae_AlMohanad.ttf";
        Typeface tf = Typeface.createFromAsset(mycontext.getAssets(), font);
        id= getIntent().getExtras().getString("id");
        s_title= getIntent().getExtras().getString("title");
        s_video = getIntent().getExtras().getString("video");
        s_time = getIntent().getExtras().getString("time");
        s_image = getIntent().getExtras().getString("image");
        s_link = getIntent().getExtras().getString("link");
        s_body = getIntent().getExtras().getString("body");
        setTitle(s_title);
        titel=(TextView)this.findViewById(R.id.name);
        content=(TextView)this.findViewById(R.id.content);
        link=(TextView)this.findViewById(R.id.news_link);
        image = (ImageView) this.findViewById(R.id.image);
        video_view = (View) this.findViewById(R.id.video_view);
        yotube = (Button) this.findViewById(R.id.button_yoyube);
        if(s_title.equals(""))
            titel.setVisibility(View.GONE);
        else {
            titel.setVisibility(View.VISIBLE);
            titel.setText(s_title);
        }
        if(s_body.equals(""))
            content.setVisibility(View.GONE);
        else {
            content.setVisibility(View.VISIBLE);
            content.setText(s_body);
        }
        if(s_link.length()>5) {
            link.setVisibility(View.VISIBLE);
            link.setTypeface(tf);
            link.setText(s_link);
            link.setClickable(true);
            link.setMovementMethod(LinkMovementMethod.getInstance());
        }
        else
            link.setVisibility(View.GONE);
        String image_name="";
        if (!((s_image.contains("ytimg"))||(s_image.contains("Misrna"))))
        {
            Picasso.with(this).load(s_image).into(image);
            /*
            image.setVisibility(View.VISIBLE);
            String []imagenames=s_image.split("/");
            image_name=imagenames[(imagenames.length)-1];
            Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/Akhbar_Masrna" + "/" +image_name));
            Picasso.with(this).load(uri).into(image);
            */
        }
        else
        {
            image.setVisibility(View.GONE);
        }
        String packageName = "com.google.android.youtube";
        boolean isYoutubeInstalled = isAppInstalled(packageName);
        if (isYoutubeInstalled && !s_video.equals("")) {
            video_view.setVisibility(View.VISIBLE);
            mfragmentManager =  getSupportFragmentManager();
            YouTubePlayerSupportFragment fragment = (YouTubePlayerSupportFragment) mfragmentManager.findFragmentById(R.id.view_player);
            fragment.initialize(YOUTUBE_API_KEY, this);
        }
        else if (!s_video.equals(""))
        {
            yotube.setVisibility(View.VISIBLE);
        }
        callbackManager = CallbackManager.Factory.create();
        titel.setText(s_title);
        content.setText(s_body);
        titel.setTypeface(tf);
        content.setTypeface(tf);
        db=new NewsDBHandler(getApplicationContext());
        if(!db.CheckIsSeenOrNot(Integer.valueOf(id))){
            News seenItem=db.getNews(Integer.valueOf(id));
            seenItem.setIs_seen(1);
            int result =db.updateNews(seenItem);
            Log.d("add seen", result+"");
        }
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            Log.d("jfd",get_youtube_id(s_video) );
            player.cueVideo(get_youtube_id(s_video)); // Plays https://www.youtube.com/watch?v=fhWaJi1Hsfo
        }
    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog((Activity)this, RECOVERY_REQUEST).show();
        } else {
            String error = String.format("error", errorReason.toString());
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }
    private String get_youtube_id(String link)
    {
        String result="";
        char[] c = link.toCharArray();
        int start =0;
        for (int i=0;i<c.length;i++)
        {
            if (c[i]=='v')
            {
                start=i+2;
                break;
            }
        }
        for(int i=start;i<c.length;i++)
        {
            result=link.substring(start,c.length);
        }
        Log.d("jh", result);
        return result;
    }
    protected boolean isAppInstalled(String packageName) {
        Intent mIntent = this.getPackageManager().getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("TAG", "share1");
        if (id == R.id.menu_item_share_fb) {
            ShareDialog shareDialog = new ShareDialog((Activity)mycontext);
            if (ShareDialog.canShow(ShareLinkContent.class)) {
                ShareLinkContent linkContent = new ShareLinkContent.Builder()
                        .setContentTitle(s_title)
                        .setContentUrl(Uri.parse("https://fb.me/1387683631245445"))
                        .setContentDescription(s_title + "\r\n" + s_body + "\r\n" + s_link)
                        .setImageUrl(Uri.parse("http://y2m.esy.es/news/images/" + s_image))
                        .build();
                shareDialog.show(linkContent);
            }
            else
            {
                Log.d("TAG","cant show");
            }
            return true;
        }
        else if (id == R.id.menu_item_share) {
            startActivity(createShareForecastIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public Intent createShareForecastIntent() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                s_title + "\r\n" + s_body + "\r\n" + s_link+ "\r\n" + "https://fb.me/1387683631245445");
        return shareIntent;
    }
}