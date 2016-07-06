package com.example.andrearodriguez.photofeed.login;

import com.example.andrearodriguez.photofeed.domine.FirebaseAPI;
import com.example.andrearodriguez.photofeed.domine.FirebaseActionListenerCallback;
import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.login.events.LoginEvent;
import com.firebase.client.FirebaseError;

/**
 * Created by andrearodriguez on 6/11/16.
 */
public class LoginRepositoryImpl implements LoginRepository {

    private EventBus eventBus;
    private FirebaseAPI firebaseAPI;

    public LoginRepositoryImpl(EventBus eventBus, FirebaseAPI firebaseAPI) {
        this.eventBus = eventBus;
        this.firebaseAPI = firebaseAPI;
    }

    @Override
    public void signUp(final String email, final String password) {
        firebaseAPI.signup(email, password, new FirebaseActionListenerCallback() {
            @Override
            public void onSuccess() {
                postEvent(LoginEvent.onSignUpSuccess);
                SignIn(email, password);
            }

            @Override
            public void onError(FirebaseError error) {
                postEvent(LoginEvent.onSignUpError, error.getMessage(), null);
            }
        });
    }

    @Override
    public void SignIn(String email, String password) {
        if(email != null && password!=null){
            firebaseAPI.login(email, password, new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = firebaseAPI.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    postEvent(LoginEvent.onSignInError, error.getMessage(), null);
                }
            });
        }else{
            firebaseAPI.checkForSession(new FirebaseActionListenerCallback() {
                @Override
                public void onSuccess() {
                    String email = firebaseAPI.getAuthEmail();
                    postEvent(LoginEvent.onSignInSuccess, email);
                }

                @Override
                public void onError(FirebaseError error) {
                    postEvent(LoginEvent.onFailedRecoverSession);
                }
            });
        }
    }

    private void postEvent(int type, String errorMessage, String currentUserEmail){
        LoginEvent loginEvent = new LoginEvent();
        loginEvent.setEventType(type);
        loginEvent.getCurrenUserEmail();
        eventBus.post(loginEvent);
    }

    private void postEvent(int type){
        postEvent(type, null, null);
    }

    private void postEvent(int type, String currentUserEmail){
        postEvent(type, null, currentUserEmail);
    }
}
