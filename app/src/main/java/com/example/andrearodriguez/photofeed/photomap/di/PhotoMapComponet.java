package com.example.andrearodriguez.photofeed.photomap.di;

import com.example.andrearodriguez.photofeed.PhotoFeedAppModule;
import com.example.andrearodriguez.photofeed.photomap.ui.PhotoMapFragment;
import com.example.andrearodriguez.photofeed.domine.di.DomainModule;
import com.example.andrearodriguez.photofeed.libs.base.di.LibsModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andrearodriguez on 7/15/16.
 */

@Singleton
@Component(modules = {PhotoMapModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface PhotoMapComponet {
    void inject(PhotoMapFragment fragment);
}
