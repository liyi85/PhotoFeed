package com.example.andrearodriguez.photofeed.photolist.ui;

import com.example.andrearodriguez.photofeed.entities.Photo;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public interface PhotoListView {
    void showList();
    void hideList();
    void showProgress();
    void hideProgress();

    void addPhoto(Photo photo);
    void removePhoto(Photo photo);
    void onPhotosError(String error);
}
