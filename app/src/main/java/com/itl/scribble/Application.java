package com.itl.scribble;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class Application extends android.app.Application {
    public static final String CHANNEL_1_ID="channel1";
    public static final String CHANNEL_2_id="channel2";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel1=new NotificationChannel(
                    CHANNEL_1_ID,
                    "Youtube links",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("These are the Recommended videos for you");
            NotificationChannel channel2=new NotificationChannel(
                    CHANNEL_2_id,
                    "channel2",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel2.setDescription("This is channel two");

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
