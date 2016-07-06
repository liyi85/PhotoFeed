package com.example.andrearodriguez.photofeed.login;

import android.util.Log;

import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.login.events.LoginEvent;
import com.example.andrearodriguez.photofeed.login.ui.LoginView;

import org.greenrobot.eventbus.Subscribe;

/**
 * Created by andrearodriguez on 6/9/16.
 */
public class LoginPresenterImpl implements LogingPresenter{

    private EventBus eventBus;
    private LoginView loginView;
    private LoginInteractor loginInteractor;


    public LoginPresenterImpl(EventBus eventBus, LoginView loginView, LoginInteractor loginInteractor) {
        this.eventBus = eventBus;
        this.loginView = loginView;
        this.loginInteractor = loginInteractor;
    }


    @Override
    public void onCreate() {
        eventBus.register(this);
    }

    @Override
    public void onDestroy() {
        eventBus.unregister(this);
        loginView = null;
    }



    @Override
    public void validateLogin(String email, String password) {
        if(loginView != null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {

        if(loginView != null){
            loginView.disableInputs();
            loginView.showProgress();
        }
        loginInteractor.doSignUp(email, password);
    }

    @Subscribe
    @Override
    public void onEventMainThread(LoginEvent event) {
        Log.e("LoginPresenterImpl","onEventMainThread");
        switch (event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSucces(event.getCurrenUserEmail());
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSucces();
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedRecoverSession:
                onFailedRecoverSession();
                break;
        }
    }

    private void onFailedRecoverSession() {
        if(loginView != null){
            loginView.hideProgress();
            loginView.enableInputs();
        }
    }

    private void onSignInSucces(String email){
        if(loginView != null){
            loginView.setUserEmail(email);
            loginView.navigationToMainScreen();

        }

    }
    private void onSignUpSucces(){
        if(loginView != null){
            loginView.newUserSuccess();

        }

    }
    private void onSignInError(String error){
        if(loginView != null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.loginError(error);
        }

    }
    private void onSignUpError(String error){
        if(loginView != null){
            loginView.hideProgress();
            loginView.enableInputs();
            loginView.newUserError(error);
        }

    }
}
