package com.example.andrearodriguez.photofeed.main.ui;

/**
 * Created by andrearodriguez on 7/7/16.
 */
public interface MainView {
    void onUploadInit();
    void onUploadComplete();
    void onUploadError(String error);
}
