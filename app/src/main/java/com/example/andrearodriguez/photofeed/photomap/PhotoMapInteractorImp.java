package com.example.andrearodriguez.photofeed.photomap;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public class PhotoMapInteractorImp implements PhotoMapInteractor{
    PhotoMapRepository repository;

    public PhotoMapInteractorImp(PhotoMapRepository repository) {
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

}
