package com.example.andrearodriguez.photofeed.libs.base;

import java.io.File;

/**
 * Created by andrearodriguez on 7/5/16.
 */
public interface ImageStorage {
    String getImageUrl(String id);
    void upload(File file, String id, ImageStorageFinishedListener listener);
}
