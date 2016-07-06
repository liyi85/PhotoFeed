package com.example.andrearodriguez.photofeed;

import android.app.Application;

import com.example.andrearodriguez.photofeed.domine.di.DomainModule;
import com.example.andrearodriguez.photofeed.libs.base.di.LibsModule;
import com.example.andrearodriguez.photofeed.login.di.DaggerLoginComponent;
import com.example.andrearodriguez.photofeed.login.di.LoginComponent;
import com.example.andrearodriguez.photofeed.login.di.LoginModule;
import com.example.andrearodriguez.photofeed.login.ui.LoginView;
import com.firebase.client.Firebase;

/**
 * Created by andrearodriguez on 7/5/16.
 */
public class PhotoFeedApp extends Application {

    private final static String EMAIL_KEY = "email";
    private final static String SHARED_PREFERENCES_NAME = "UsersPrefs";
    private final static String FIREBASE_URL = "https://photoedxandrea2.firebaseio.com/";

    private DomainModule domainModule;
    private PhotoFeedAppModule photoFeedAppModule;

    @Override
    public void onCreate() {
        super.onCreate();
        initFirebase();
        initModules();
    }

    private void initModules() {
        photoFeedAppModule = new PhotoFeedAppModule(this);
        domainModule = new DomainModule(FIREBASE_URL);
    }

    private void initFirebase() {
        Firebase.setAndroidContext(this);
    }

    public String getEmailKey() {
        return EMAIL_KEY;
    }

    public String getSharedPreferencesName() {
        return SHARED_PREFERENCES_NAME;
    }

    public LoginComponent getLoginComponent(LoginView view){
        return DaggerLoginComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .loginModule(new LoginModule(view))
                .build();
    }
}
