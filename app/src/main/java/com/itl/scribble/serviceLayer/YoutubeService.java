package com.itl.scribble.serviceLayer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import com.itl.scribble.helperClasses.YoutubeNotifObj;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class YoutubeService {

    public static String getVideoURLFromId(String id) {
        return "https://youtu.be/" + id;
    }


    public static Bitmap getBitmapFromUrl(String thumbnailUrl) {
        try {
            URL url = new URL(thumbnailUrl);
            InputStream in;
            if (Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                in = (InputStream) url.openConnection().getInputStream();
            } else {
                in = (InputStream) url.openConnection().getInputStream();
            }
            return BitmapFactory.decodeStream(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static YoutubeNotifObj getNotificationObj(String successResponse) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(successResponse);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if (jsonArray.length() > 0) {
                JSONObject videoData = jsonArray.getJSONObject(0);
                JSONObject videoIdObj = videoData.getJSONObject("id");
                String videoId = videoIdObj.getString("videoId");
                String videoURL = getVideoURLFromId(videoId);
                JSONObject snippetObj = videoData.getJSONObject("snippet");
                String title = snippetObj.getString("title");
                String description = snippetObj.getString("description");
                JSONObject thumbnailObj = snippetObj.getJSONObject("thumbnails");
                String thumbnailUrl = thumbnailObj.getJSONObject("medium")
                        .getString("url");
                Bitmap bitmap = getBitmapFromUrl(thumbnailUrl);
                return new YoutubeNotifObj(title, bitmap, videoURL, description);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}


