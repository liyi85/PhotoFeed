package com.example.andrearodriguez.photofeed.main;

import android.location.Location;

import com.example.andrearodriguez.photofeed.domine.FirebaseAPI;
import com.example.andrearodriguez.photofeed.entities.Photo;
import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.libs.base.ImageStorage;
import com.example.andrearodriguez.photofeed.libs.base.ImageStorageFinishedListener;
import com.example.andrearodriguez.photofeed.main.events.MainEvent;

import java.io.File;

/**
 * Created by andrearodriguez on 7/9/16.
 */
public class MainRepositoryImp implements MainRepository{

    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;
    private ImageStorage imageStorage;

    public MainRepositoryImp(EventBus eventBus, FirebaseAPI firebaseAPI, ImageStorage imageStorage) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
        this.imageStorage = imageStorage;
    }

    @Override
    public void logout() {
        firebaseAPI.logout();
    }

    @Override
    public void uploadPhoto(Location location, String path) {
        final String newPhotoId = firebaseAPI.create();
        final Photo photo = new Photo();
        photo.setId(newPhotoId);
        photo.setEmail(firebaseAPI.getAuthEmail());
        if(location != null){
            photo.setLatitud(location.getLatitude());
            photo.setLongitud(location.getLongitude());
        }
        post(MainEvent.UPLOAD_INIT);
        ImageStorageFinishedListener listener = new ImageStorageFinishedListener() {
            @Override
            public void onSuccess() {
                String url = imageStorage.getImageUrl(newPhotoId);
                photo.setUrl(url);
                firebaseAPI.update(photo);
                post(MainEvent.UPLOAD_COMPLETE);
            }

            @Override
            public void onError(String error) {
                post(MainEvent.UPLOAD_ERROR, error);
            }
        };
        imageStorage.upload(new File(path), newPhotoId, listener);
    }

    private void post(int type) {
        post(type, null);
    }
    private  void post(int type, String error){
        MainEvent event = new MainEvent();
        event.setType(type);
        event.setError(error);
        eventBus.post(event);
    }
}
