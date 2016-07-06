package com.example.andrearodriguez.photofeed.login.events;

/**
 * Created by andrearodriguez on 7/5/16.
 */
public class LoginEvent {
    public final static int onSignInError = 0;
    public final static int onSignUpError = 1;
    public final static int onSignInSuccess = 2;
    public final static int onSignUpSuccess = 3;
    public final static int onFailedRecoverSession = 4;

    private int eventType;
    private String errorMessage;
    private String currenUserEmail;

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {

        this.eventType = eventType;
    }
    public String getErrorMessage() {

        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {

        this.errorMessage = errorMessage;
    }
    public String getCurrenUserEmail() {
        return currenUserEmail;
    }
    public void setCurrenUserEmail(String currenUserEmail) {
        this.currenUserEmail = currenUserEmail;
    }
}
