package com.example.andrearodriguez.photofeed.photolist.di;

import com.example.andrearodriguez.photofeed.domine.FirebaseAPI;
import com.example.andrearodriguez.photofeed.domine.Util;
import com.example.andrearodriguez.photofeed.entities.Photo;
import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.libs.base.ImageLoader;
import com.example.andrearodriguez.photofeed.photolist.PhotoListInteractor;
import com.example.andrearodriguez.photofeed.photolist.PhotoListInteractorImp;
import com.example.andrearodriguez.photofeed.photolist.PhotoListPresenter;
import com.example.andrearodriguez.photofeed.photolist.PhotoListPresenterImp;
import com.example.andrearodriguez.photofeed.photolist.PhotoListRepository;
import com.example.andrearodriguez.photofeed.photolist.PhotoListRepositoryImp;
import com.example.andrearodriguez.photofeed.photolist.ui.PhotoListView;
import com.example.andrearodriguez.photofeed.photolist.ui.adapter.OnItemClickListernY;
import com.example.andrearodriguez.photofeed.photolist.ui.adapter.PhotoListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andrearodriguez on 7/15/16.
 */

@Module
public class PhotoListModule {
    PhotoListView view;
    OnItemClickListernY onItemClickListener;

    public PhotoListModule(PhotoListView view, OnItemClickListernY onItemClickListener) {
        this.view = view;
        this.onItemClickListener = onItemClickListener;
    }
    @Provides
    @Singleton
    PhotoListView providesPhotoContentView() {
        return this.view;
    }

    @Provides @Singleton
    PhotoListPresenter providesPhotoListPresenter(EventBus eventBus, PhotoListView view, PhotoListInteractor listInteractor) {
        return new PhotoListPresenterImp(eventBus, view, listInteractor);
    }

    @Provides @Singleton
    PhotoListInteractor providesPhotoListInteractor(PhotoListRepository repository) {
        return new PhotoListInteractorImp(repository);
    }

    @Provides @Singleton
    PhotoListRepository providesPhotoListRepository(EventBus eventBus, FirebaseAPI firebase) {
        return new PhotoListRepositoryImp(eventBus, firebase);
    }

    @Provides @Singleton
    PhotoListAdapter providesPhotosAdapter(Util utils, List<Photo> photoList, ImageLoader imageLoader, OnItemClickListernY onItemClickListener) {
        return new PhotoListAdapter(utils, photoList, imageLoader, onItemClickListener);
    }

    @Provides @Singleton
    OnItemClickListernY providesOnItemClickListener() {
        return this.onItemClickListener;
    }

    @Provides @Singleton
    List<Photo> providesPhotosList() {
        return new ArrayList<Photo>();
    }
}
