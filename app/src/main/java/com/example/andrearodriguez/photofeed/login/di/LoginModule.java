package com.example.andrearodriguez.photofeed.login.di;

import com.example.andrearodriguez.photofeed.domine.FirebaseAPI;
import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.login.LoginInteractor;
import com.example.andrearodriguez.photofeed.login.LoginInteractorImpl;
import com.example.andrearodriguez.photofeed.login.LoginPresenterImpl;
import com.example.andrearodriguez.photofeed.login.LoginRepository;
import com.example.andrearodriguez.photofeed.login.LoginRepositoryImpl;
import com.example.andrearodriguez.photofeed.login.LogingPresenter;
import com.example.andrearodriguez.photofeed.login.ui.LoginView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andrearodriguez on 7/5/16.
 */

@Module
public class LoginModule {

    LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    @Singleton
    LoginView providesLoginView (){
        return this.view;
    }
    @Provides
    @Singleton
    LogingPresenter providesLogingPresenter (EventBus eventBus, LoginView loginView, LoginInteractor loginInteractor){
        return new LoginPresenterImpl(eventBus, loginView, loginInteractor);
    }
    @Provides
    @Singleton
    LoginInteractor providesLoginInteractor (LoginRepository repository){
        return new LoginInteractorImpl(repository);
    }
    @Provides
    @Singleton
    LoginRepository providesLoginRepository (EventBus eventBus, FirebaseAPI firebaseAPI){
        return new LoginRepositoryImpl(eventBus, firebaseAPI);
    }
}
