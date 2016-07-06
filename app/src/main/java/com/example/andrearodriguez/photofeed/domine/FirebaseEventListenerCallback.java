package com.example.andrearodriguez.photofeed.domine;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

/**
 * Created by andrearodriguez on 7/5/16.
 */
public interface FirebaseEventListenerCallback {
    void onChilAdded(DataSnapshot snapshot);
    void onChilRemoved(DataSnapshot snapshot);
    void onCancelled(FirebaseError error);
}
