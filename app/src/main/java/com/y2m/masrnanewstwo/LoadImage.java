package com.y2m.masrnanewstwo;
import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LoadImage extends IntentService {
    public LoadImage() {
        super("load_image");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        String images [] = new String[0];
        Bundle extras = intent.getExtras();
        images = extras.getStringArray("images");
        for(int i=0;i<images.length;i++)
        {
            if (images[i]!=null && (images[i].length()>5)) {
                if (intent != null) {
                    try {
                        Bitmap b = Picasso.with(this).load(images[i]).get();
                        File folder = new File(Environment.getExternalStorageDirectory() + "/Akhbar_Masrna");
                        String imagename = "";
                        boolean success = true;
                        if (!folder.exists()) {
                            success = folder.mkdir();
                        }

                        if (images[i].contains("ytimg")) {
                            String[] image = images[i].split("/");
                            imagename = image[(image.length) - 2];
                        } else {
                            String[] image = images[i].split("/");
                            imagename = image[(image.length) - 1];
                        }
                        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/Akhbar_Masrna" + "/" + imagename);
                        Log.d("pecoo", "hellooo");
                        try {
                            file.createNewFile();
                            Log.d("pecoo", "hellooo1");
                            FileOutputStream ostream = new FileOutputStream(file);
                            b.compress(Bitmap.CompressFormat.JPEG, 80, ostream);
                            ostream.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
