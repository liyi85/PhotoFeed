package com.example.andrearodriguez.photofeed.domine;

import com.firebase.client.FirebaseError;

/**
 * Created by andrearodriguez on 7/5/16.
 */
public interface FirebaseActionListenerCallback {
    void onSuccess();
    void onError(FirebaseError error);
}
