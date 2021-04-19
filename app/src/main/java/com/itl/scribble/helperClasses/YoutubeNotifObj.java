package com.itl.scribble.helperClasses;

import android.graphics.Bitmap;

public class YoutubeNotifObj {
    String title;
    Bitmap bitmap;
    String videoUrl;
    String description;

    public String getTitle() {
        return title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public YoutubeNotifObj(String title, Bitmap bitmap, String videoUrl, String description) {
        this.title = title;
        this.bitmap = bitmap;
        this.videoUrl = videoUrl;
        this.description = description;
    }
}
