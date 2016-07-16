package com.example.andrearodriguez.photofeed.photolist.ui;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public interface PhotoListView {
    void showList();
    void hideList();
    void showProgress();
    void hideProgress();

    void addPhoto();
    void removePhoto();
    void onPhotosError(String error);
}
