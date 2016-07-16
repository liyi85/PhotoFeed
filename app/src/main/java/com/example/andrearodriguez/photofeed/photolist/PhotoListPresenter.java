package com.example.andrearodriguez.photofeed.photolist;

import com.example.andrearodriguez.photofeed.entities.Photo;
import com.example.andrearodriguez.photofeed.photolist.events.PhotoListEvent;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public interface PhotoListPresenter {
    void onCreate();
    void onDestroy();

    void subscribe();
    void unsubscribe();

    void removePhoto(Photo photo);
    void onEventMainThread (PhotoListEvent event);
}
