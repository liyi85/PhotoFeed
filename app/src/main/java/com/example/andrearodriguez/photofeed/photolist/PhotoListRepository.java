package com.example.andrearodriguez.photofeed.photolist;

import com.example.andrearodriguez.photofeed.entities.Photo;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public interface PhotoListRepository {
    void subscribe();
    void unsubscribe();

    void removePhoto(Photo photo);
}
