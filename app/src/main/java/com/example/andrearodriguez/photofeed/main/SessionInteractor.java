package com.example.andrearodriguez.photofeed.main;

import android.location.Location;

/**
 * Created by andrearodriguez on 7/7/16.
 */
public interface SessionInteractor {
    void logout();
    void uploadPhoto(Location location, String path);
}
