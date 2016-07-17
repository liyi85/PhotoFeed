package com.example.andrearodriguez.photofeed.photomap;

import com.example.andrearodriguez.photofeed.domine.FirebaseAPI;
import com.example.andrearodriguez.photofeed.domine.FirebaseActionListenerCallback;
import com.example.andrearodriguez.photofeed.domine.FirebaseEventListenerCallback;
import com.example.andrearodriguez.photofeed.entities.Photo;
import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.photomap.events.PhotoMapEvent;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public class PhotoMapRepositoryImp implements PhotoMapRepository {

    private EventBus eventBus;
    private FirebaseAPI firebase;

    public PhotoMapRepositoryImp(EventBus eventBus, FirebaseAPI firebase) {
        this.eventBus = eventBus;
        this.firebase = firebase;
    }

    @Override
    public void subscribe() {
        firebase.checkForData(new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(FirebaseError error) {
                if (error != null) {
                    post(PhotoMapEvent.READ_EVENT, error.getMessage());
                } else {
                    post(PhotoMapEvent.READ_EVENT, "");
                }
            }
        });
        firebase.subscribe(new FirebaseEventListenerCallback() {
            @Override
            public void onChilAdded(DataSnapshot snapshot) {

                Photo photo = snapshot.getValue(Photo.class);
                String email = firebase.getAuthEmail();
                photo.setId(snapshot.getKey());

                boolean publishedByMy = (photo.getEmail()).equals(email);
                photo.setPublishedByMe(publishedByMy);
                post(PhotoMapEvent.READ_EVENT, photo);
            }

            @Override
            public void onChilRemoved(DataSnapshot snapshot) {
                Photo photo = snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());

                post(PhotoMapEvent.DELETE_EVENT, photo);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                post(PhotoMapEvent.READ_EVENT, error.getMessage());
            }
        });
    }

    @Override
    public void unsubscribe() {
        firebase.unsubscribe();
    }


    private void post(int type, Photo photo) {
        post(type, photo, null);
    }

    private void post(int type, String error) {
        post(type, null, error);
    }

    private void post(int type, Photo photo, String error) {
        PhotoMapEvent event = new PhotoMapEvent();
        event.setType(type);
        event.setError(error);
        event.setPhoto(photo);
        eventBus.post(event);
    }
}
