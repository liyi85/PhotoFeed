package com.example.andrearodriguez.photofeed.libs.base.di;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cloudinary.Cloudinary;
import com.cloudinary.android.Utils;
import com.example.andrearodriguez.photofeed.libs.CloudinaryImageStorage;
import com.example.andrearodriguez.photofeed.libs.GlideImageLoader;
import com.example.andrearodriguez.photofeed.libs.GreenRobotEventBus;
import com.example.andrearodriguez.photofeed.libs.base.EventBus;
import com.example.andrearodriguez.photofeed.libs.base.ImageLoader;
import com.example.andrearodriguez.photofeed.libs.base.ImageStorage;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by andrearodriguez on 6/23/16.
 */
@Module
public class LibsModule {
    private Fragment fragment;


    public LibsModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @Singleton
    EventBus providesEventBus(org.greenrobot.eventbus.EventBus eventBus ){
        return new GreenRobotEventBus(eventBus);
    }

    @Provides
    @Singleton
    org.greenrobot.eventbus.EventBus providesLibraryEventBus(){
        return org.greenrobot.eventbus.EventBus.getDefault();
    }

    @Provides
    @Singleton
    ImageLoader providesImageLoader(RequestManager requestManager){
        return new GlideImageLoader(requestManager);
    }


    @Provides
    @Singleton
    RequestManager providesRequestManager(Fragment fragment){
        return Glide.with(fragment);
    }

    @Provides
    @Singleton
    Fragment providesFragment(){
        return  this.fragment;
    }
    @Provides
    @Singleton
    ImageStorage providesImageStorage(Cloudinary cloudinary){
        return new CloudinaryImageStorage(cloudinary);
    }
    @Provides
    @Singleton
    Cloudinary providesCloudinary (Context context){
        return new Cloudinary(Utils.cloudinaryUrlFromContext(context));
    }
}