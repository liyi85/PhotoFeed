package com.example.andrearodriguez.photofeed.main;

import android.location.Location;

import com.example.andrearodriguez.photofeed.main.events.MainEvent;

/**
 * Created by andrearodriguez on 7/7/16.
 */
public interface MainPresenter {
    void onCreate();
    void onDestroy();

    void logout();
    void uploadPhoto(Location location, String path);

    void onEventMainThread(MainEvent event);
}
