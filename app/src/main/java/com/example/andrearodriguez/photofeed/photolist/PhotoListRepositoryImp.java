package com.example.andrearodriguez.photofeed.photolist;

import com.example.andrearodriguez.photofeed.domine.FirebaseAPI;
import com.example.andrearodriguez.photofeed.domine.FirebaseActionListenerCallback;
import com.example.andrearodriguez.photofeed.domine.FirebaseEventListenerCallback;
import com.example.andrearodriguez.photofeed.entities.Photo;
import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.photolist.events.PhotoListEvent;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public class PhotoListRepositoryImp implements PhotoListRepository {

    private EventBus eventBus;
    private FirebaseAPI firebase;

    public PhotoListRepositoryImp(EventBus eventBus, FirebaseAPI firebase) {
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
                    post(PhotoListEvent.READ_EVENT, error.getMessage());
                } else {
                    post(PhotoListEvent.READ_EVENT, "");
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
                post(PhotoListEvent.READ_EVENT, photo);
            }

            @Override
            public void onChilRemoved(DataSnapshot snapshot) {
                Photo photo = snapshot.getValue(Photo.class);
                photo.setId(snapshot.getKey());

                post(PhotoListEvent.DELETE_EVENT, photo);
            }

            @Override
            public void onCancelled(FirebaseError error) {
                post(PhotoListEvent.READ_EVENT, error.getMessage());
            }
        });
    }

    @Override
    public void unsubscribe() {
        firebase.unsubscribe();
    }

    @Override
    public void removePhoto(final Photo photo) {
        firebase.remove(photo, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                post(PhotoListEvent.DELETE_EVENT, photo);
            }

            @Override
            public void onError(FirebaseError error) {

            }
        });
    }

    private void post(int type, Photo photo) {
        post(type, photo, null);
    }

    private void post(int type, String error) {
        post(type, null, error);
    }

    private void post(int type, Photo photo, String error) {
        PhotoListEvent event = new PhotoListEvent();
        event.setType(type);
        event.setError(error);
        event.setPhoto(photo);
        eventBus.post(event);
    }
}
