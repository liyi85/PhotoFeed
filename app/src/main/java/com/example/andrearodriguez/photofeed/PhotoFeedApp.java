package com.example.andrearodriguez.photofeed;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.andrearodriguez.photofeed.domine.di.DomainModule;
import com.example.andrearodriguez.photofeed.libs.base.di.LibsModule;
import com.example.andrearodriguez.photofeed.login.di.DaggerLoginComponent;
import com.example.andrearodriguez.photofeed.login.di.LoginComponent;
import com.example.andrearodriguez.photofeed.login.di.LoginModule;
import com.example.andrearodriguez.photofeed.login.ui.LoginView;
import com.example.andrearodriguez.photofeed.main.di.DaggerMainComponent;
import com.example.andrearodriguez.photofeed.main.di.MainComponent;
import com.example.andrearodriguez.photofeed.main.di.MainModule;
import com.example.andrearodriguez.photofeed.main.ui.MainView;
import com.example.andrearodriguez.photofeed.photolist.di.DaggerPhotoListComponet;
import com.example.andrearodriguez.photofeed.photolist.di.PhotoListComponet;
import com.example.andrearodriguez.photofeed.photolist.di.PhotoListModule;
import com.example.andrearodriguez.photofeed.photolist.ui.PhotoListFragment;
import com.example.andrearodriguez.photofeed.photolist.ui.PhotoListView;
import com.example.andrearodriguez.photofeed.photolist.ui.adapter.OnItemClickListernY;
import com.example.andrearodriguez.photofeed.photomap.di.DaggerPhotoMapComponet;
import com.example.andrearodriguez.photofeed.photomap.di.PhotoMapComponet;
import com.example.andrearodriguez.photofeed.photomap.di.PhotoMapModule;
import com.example.andrearodriguez.photofeed.photomap.ui.PhotoMapFragment;
import com.example.andrearodriguez.photofeed.photomap.ui.PhotoMapView;
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
    public MainComponent getMainComponent(MainView view, FragmentManager manager, Fragment[] fragments, String[] titles){
        return DaggerMainComponent
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(null))
                .mainModule(new MainModule(view, titles, fragments, manager))
                .build();
    }
    public PhotoListComponet getPhotoListComponet(PhotoListFragment fragment, PhotoListView view, OnItemClickListernY onItemClickListener) {

        return DaggerPhotoListComponet
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(fragment))
                .photoListModule(new PhotoListModule(view, onItemClickListener))
                .build();

    }

    public PhotoMapComponet getMapComponent(PhotoMapFragment photoMapFragment, PhotoMapView view) {
        return DaggerPhotoMapComponet
                .builder()
                .photoFeedAppModule(photoFeedAppModule)
                .domainModule(domainModule)
                .libsModule(new LibsModule(photoMapFragment))
                .photoMapModule(new PhotoMapModule(view))
                .build();
    }
}
