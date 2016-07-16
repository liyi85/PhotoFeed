package com.example.andrearodriguez.photofeed.main;

import android.location.Location;

/**
 * Created by andrearodriguez on 7/9/16.
 */
public interface MainRepository {
    void logout();
    void uploadPhoto(Location location, String path);
}
