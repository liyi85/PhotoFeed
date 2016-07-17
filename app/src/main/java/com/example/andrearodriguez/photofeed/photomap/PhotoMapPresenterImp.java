package com.example.andrearodriguez.photofeed.photomap;

import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.photomap.events.PhotoMapEvent;
import com.example.andrearodriguez.photofeed.photomap.ui.PhotoMapView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public class PhotoMapPresenterImp implements PhotoMapPresenter {

    EventBus eventBus;
    PhotoMapView view;
    PhotoMapInteractor interactor;

    private final static String EMPTY_LIST = "Listado vacío";

    public PhotoMapPresenterImp(EventBus eventBus, PhotoMapView view, PhotoMapInteractor interactor) {
        this.eventBus = eventBus;
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        this.view = null;
        eventBus.unregister(this);
    }

    @Override
    public void subscribe() {
        if (view != null){

        }
        interactor.subscribe();
    }

    @Override
    public void unsubscribe() {
        interactor.unsubscribe();
    }


    @Override
    @Subscribe
    public void onEventMainThread(PhotoMapEvent event) {
        if (this.view != null) {
            if (event.getError() != null){
                view.onPhotosError(event.getError());
            } else {
                if (event.getType() == PhotoMapEvent.READ_EVENT) {
                    view.addPhoto(event.getPhoto());
                } else if (event.getType() == PhotoMapEvent.DELETE_EVENT) {
                    view.removePhoto(event.getPhoto());
                }
            }
        }

    }
}
