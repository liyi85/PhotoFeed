package com.example.andrearodriguez.photofeed.main;

import android.location.Location;

import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.main.events.MainEvent;
import com.example.andrearodriguez.photofeed.main.ui.MainView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by andrearodriguez on 7/9/16.
 */
public class MainPresenterImp implements MainPresenter {

    private MainView view;
    private EventBus eventBus;
    private UploadInteractor uploadInteractor;
    private SessionInteractor sessionInteractor;

    public MainPresenterImp(MainView view, EventBus eventBus, UploadInteractor uploadInteractor, SessionInteractor sessionInteractor) {
        this.view = view;
        this.eventBus = eventBus;
        this.uploadInteractor = uploadInteractor;
        this.sessionInteractor = sessionInteractor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        view = null;
        eventBus.unregister(this);
    }

    @Override
    public void logout() {
        sessionInteractor.logout();

    }

    @Override
    public void uploadPhoto(Location location, String path) {
        uploadInteractor.excecute(location, path);
    }

    @Override
    @Subscribe
    public void onEventMainThread(MainEvent event) {

        if(this.view != null){
            switch (event.getType()){
                case MainEvent.UPLOAD_INIT:
                    view.onUploadInit();
                    break;
                case MainEvent.UPLOAD_COMPLETE:
                    view.onUploadComplete();
                    break;
                case MainEvent.UPLOAD_ERROR:
                    view.onUploadError(event.getError());
                    break;
            }
        }
    }
}
