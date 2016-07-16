package com.example.andrearodriguez.photofeed.main;

import android.location.Location;

/**
 * Created by andrearodriguez on 7/7/16.
 */
public class UploadInteractorImp implements UploadInteractor{
    MainRepository repository;

    public UploadInteractorImp(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void excecute(Location location, String path) {
        repository.uploadPhoto(location, path);
    }
}
