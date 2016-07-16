package com.example.andrearodriguez.photofeed.photolist;

import com.example.andrearodriguez.photofeed.entities.Photo;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public class PhotoListInteractorImp implements PhotoListInteractor{
    PhotoListRepository repository;

    public PhotoListInteractorImp(PhotoListRepository repository) {
        this.repository = repository;
    }

    @Override
    public void subscribe() {
        repository.subscribe();
    }

    @Override
    public void unsubscribe() {
        repository.unsubscribe();
    }

    @Override
    public void removePhoto(Photo photo) {
        repository.removePhoto(photo);
    }
}
