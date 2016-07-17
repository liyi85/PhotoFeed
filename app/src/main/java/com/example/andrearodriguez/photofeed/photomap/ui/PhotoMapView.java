package com.example.andrearodriguez.photofeed.photomap.ui;

import com.example.andrearodriguez.photofeed.entities.Photo;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public interface PhotoMapView {

    void addPhoto(Photo photo);
    void removePhoto(Photo photo);
    void onPhotosError(String error);
}
