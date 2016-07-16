package com.example.andrearodriguez.photofeed.main.di;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.andrearodriguez.photofeed.domine.FirebaseAPI;
import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.libs.base.ImageStorage;
import com.example.andrearodriguez.photofeed.main.MainPresenter;
import com.example.andrearodriguez.photofeed.main.MainPresenterImp;
import com.example.andrearodriguez.photofeed.main.MainRepository;
import com.example.andrearodriguez.photofeed.main.MainRepositoryImp;
import com.example.andrearodriguez.photofeed.main.SessionInteractor;
import com.example.andrearodriguez.photofeed.main.SessionInteractorImp;
import com.example.andrearodriguez.photofeed.main.UploadInteractor;
import com.example.andrearodriguez.photofeed.main.UploadInteractorImp;
import com.example.andrearodriguez.photofeed.main.ui.MainView;
import com.example.andrearodriguez.photofeed.main.ui.adapters.MainSectionPagerAdapter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andrearodriguez on 7/9/16.
 */
@Module
public class MainModule {
    private MainView view;
    private String[] titles;
    private Fragment[] fragments;
    private FragmentManager fragmentManager;

    public MainModule(MainView view, String[] titles, Fragment[] fragments, FragmentManager fragmentManager) {
        this.view = view;
        this.titles = titles;
        this.fragments = fragments;
        this.fragmentManager = fragmentManager;
    }

    @Provides
    @Singleton
    MainView providesMainView(){
        return this.view;
    }
    @Provides
    @Singleton
    MainPresenter providesMainPresenter(MainView view, EventBus eventBus, UploadInteractor uploadInteractor, SessionInteractor sessionInteractor){
        return new MainPresenterImp(view, eventBus, uploadInteractor, sessionInteractor);
    }
    @Provides
    @Singleton
    UploadInteractor providesUploadInteractor(MainRepository repository){
        return new UploadInteractorImp(repository);
    }
    @Provides
    @Singleton
    SessionInteractor providesSessionInteractor (MainRepository repository){
        return new SessionInteractorImp(repository);
    }
    @Provides
    @Singleton
    MainRepository providesMainRepository(EventBus eventBus, FirebaseAPI firebase, ImageStorage imageStorage){
        return new MainRepositoryImp(eventBus, firebase, imageStorage);
    }
    @Provides
    @Singleton
    MainSectionPagerAdapter providesAdapter (FragmentManager fm, String[] titles, Fragment[] fragments){
        return new MainSectionPagerAdapter(fm, titles, fragments);
    }
    @Provides
    @Singleton
    FragmentManager providesAdapterFragmentManager(){
        return this.fragmentManager;
    }
    @Provides
    @Singleton
    Fragment[] providesFragmentArrayForAdapter(){
        return this.fragments;
    }
    @Provides
    @Singleton
    String[] providesStringArrayForAdapter(){
        return this.titles;
    }
}
