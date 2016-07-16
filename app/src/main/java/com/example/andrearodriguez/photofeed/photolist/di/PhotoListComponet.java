package com.example.andrearodriguez.photofeed.photolist.di;

import com.example.andrearodriguez.photofeed.PhotoFeedAppModule;
import com.example.andrearodriguez.photofeed.domine.di.DomainModule;
import com.example.andrearodriguez.photofeed.libs.base.di.LibsModule;
import com.example.andrearodriguez.photofeed.photolist.ui.PhotoListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andrearodriguez on 7/15/16.
 */

@Singleton
@Component(modules = {PhotoListModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface PhotoListComponet {
    void inject(PhotoListFragment fragment);
}
