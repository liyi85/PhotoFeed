package com.example.andrearodriguez.photofeed.photomap;

import com.example.andrearodriguez.photofeed.photomap.events.PhotoMapEvent;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public interface PhotoMapPresenter {
    void onCreate();
    void onDestroy();

    void subscribe();
    void unsubscribe();

    void onEventMainThread(PhotoMapEvent event);
}
