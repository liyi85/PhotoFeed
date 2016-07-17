package com.example.andrearodriguez.photofeed.photomap.di;

import com.example.andrearodriguez.photofeed.domine.FirebaseAPI;
import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.photomap.PhotoMapInteractor;
import com.example.andrearodriguez.photofeed.photomap.PhotoMapInteractorImp;
import com.example.andrearodriguez.photofeed.photomap.PhotoMapPresenter;
import com.example.andrearodriguez.photofeed.photomap.PhotoMapPresenterImp;
import com.example.andrearodriguez.photofeed.photomap.PhotoMapRepository;
import com.example.andrearodriguez.photofeed.photomap.PhotoMapRepositoryImp;
import com.example.andrearodriguez.photofeed.photomap.ui.PhotoMapView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andrearodriguez on 7/15/16.
 */

@Module
public class PhotoMapModule {
    PhotoMapView view;

    public PhotoMapModule(PhotoMapView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    PhotoMapView providesPhotoMapView() {
        return this.view;
    }

    @Provides @Singleton
    PhotoMapPresenter providesPhotoMapPresenter(EventBus eventBus, PhotoMapView view, PhotoMapInteractor listInteractor) {
        return new PhotoMapPresenterImp(eventBus, view, listInteractor);
    }

    @Provides @Singleton
    PhotoMapInteractor providesPhotoMapInteractor(PhotoMapRepository repository) {
        return new PhotoMapInteractorImp(repository);
    }

    @Provides @Singleton
    PhotoMapRepository providesPhotoMapRepository(EventBus eventBus, FirebaseAPI firebase) {
        return new PhotoMapRepositoryImp(eventBus, firebase);
    }

}
