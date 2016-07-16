package com.example.andrearodriguez.photofeed.main;

/**
 * Created by andrearodriguez on 7/9/16.
 */
public class SessionInteractorImp implements SessionInteractor {
    MainRepository repository;

    public SessionInteractorImp(MainRepository repository) {
        this.repository = repository;
    }

    @Override
    public void logout() {
        repository.logout();
    }
}
