package com.example.andrearodriguez.photofeed.domine.di;

import com.example.andrearodriguez.photofeed.PhotoFeedAppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andrearodriguez on 7/5/16.
 */
@Singleton
@Component(modules={DomainModule.class, PhotoFeedAppModule.class})
public interface DomainComponent {
}
