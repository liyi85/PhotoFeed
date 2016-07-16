package com.example.andrearodriguez.photofeed.main.di;

import com.example.andrearodriguez.photofeed.PhotoFeedAppModule;
import com.example.andrearodriguez.photofeed.domine.di.DomainModule;
import com.example.andrearodriguez.photofeed.libs.base.di.LibsModule;
import com.example.andrearodriguez.photofeed.main.ui.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andrearodriguez on 7/9/16.
 */

@Singleton
@Component (modules = {MainModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
