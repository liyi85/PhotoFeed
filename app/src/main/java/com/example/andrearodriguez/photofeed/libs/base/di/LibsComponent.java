package com.example.andrearodriguez.photofeed.libs.base.di;

import com.example.andrearodriguez.photofeed.PhotoFeedAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andrearodriguez on 7/5/16.
 */

@Singleton
@Component (modules = {LibsModule.class, PhotoFeedAppModule.class})
public interface LibsComponent {
}
