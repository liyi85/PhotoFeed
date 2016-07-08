package com.example.andrearodriguez.photofeed.login.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.andrearodriguez.photofeed.main.ui.MainActivity;
import com.example.andrearodriguez.photofeed.PhotoFeedApp;
import com.example.andrearodriguez.photofeed.R;
import com.example.andrearodriguez.photofeed.login.LogingPresenter;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @Bind(R.id.txtEmail)
    EditText txtEmail;
    @Bind(R.id.txtPassword)
    EditText txtPassword;
    @Bind(R.id.btnSignin)
    Button btnSignin;
    @Bind(R.id.btnSignInUp)
    Button btnSignInUp;
    @Bind(R.id.progressBar)
    ProgressBar progressBar;
    @Bind(R.id.layoutMainContainer)
    RelativeLayout container;

    private PhotoFeedApp app;
    @Inject
    LogingPresenter logingPresenter;
    @Inject
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        app = (PhotoFeedApp)getApplication();
        setupInjection();
        logingPresenter.onCreate();
        logingPresenter.validateLogin(null, null);
    }

    @Override
    protected void onDestroy() {
        logingPresenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void enableInputs() {
        setInputs(true);

    }

    @Override
    public void disableInputs() {

        setInputs(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);

    }

    @OnClick(R.id.btnSignInUp)
    @Override
    public void handleSignUp() {
        logingPresenter.registerNewUser(txtEmail.getText().toString(),
                txtPassword.getText().toString());

        //startActivity(new Intent(this, ActivitySignUp.class));
    }

    @OnClick(R.id.btnSignin)
    @Override
    public void handleSignIn() {
        logingPresenter.validateLogin(txtEmail.getText().toString(),
                txtPassword.getText().toString());

    }

    @Override
    public void navigationToMainScreen() {
        startActivity(new Intent(this, MainActivity.class));

    }

    @Override
    public void loginError(String error) {
        txtPassword.setText("");
        String msgError = String.format(getString(R.string.loging_error_messagge_signin, error));
        txtPassword.setError(msgError);

    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(container, R.string.loging_notice_signin, Snackbar.LENGTH_SHORT).show();

    }


    @Override
    public void newUserError(String error) {
        txtPassword.setText("");
        String msgError = String.format(getString(R.string.loging_error_messagge_signup, error));
        txtPassword.setError(msgError);
    }

    private void setInputs (boolean enabled){
        txtEmail.setEnabled(enabled);
        txtPassword.setEnabled(enabled);
        btnSignin.setEnabled(enabled);
        btnSignInUp.setEnabled(enabled);
    }

    @Override
    public void setUserEmail(String email) {
        if(email != null){
            sharedPreferences.edit().putString(app.getEmailKey(), email).commit();
        }
    }
    private void setupInjection() {
        app.getLoginComponent(this).inject(this);
    }

}
