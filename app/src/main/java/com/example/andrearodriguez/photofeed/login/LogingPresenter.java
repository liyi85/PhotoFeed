package com.example.andrearodriguez.photofeed.login;


import com.example.andrearodriguez.photofeed.login.events.LoginEvent;

/**
 * Created by andrearodriguez on 6/9/16.
 */
public interface LogingPresenter {
    void onCreate();
    void onDestroy();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);
    void onEventMainThread(LoginEvent event);
}
