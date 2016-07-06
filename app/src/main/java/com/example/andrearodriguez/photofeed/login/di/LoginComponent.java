package com.example.andrearodriguez.photofeed.login.di;

import com.example.andrearodriguez.photofeed.PhotoFeedAppModule;
import com.example.andrearodriguez.photofeed.domine.di.DomainModule;
import com.example.andrearodriguez.photofeed.libs.base.di.LibsModule;
import com.example.andrearodriguez.photofeed.login.ui.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by andrearodriguez on 7/5/16.
 */

@Singleton
@Component(modules = {LoginModule.class, DomainModule.class, LibsModule.class, PhotoFeedAppModule.class})
public interface LoginComponent {
    void inject(LoginActivity activity);
}
