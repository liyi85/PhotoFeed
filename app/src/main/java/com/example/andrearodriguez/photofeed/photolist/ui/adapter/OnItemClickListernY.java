package com.example.andrearodriguez.photofeed.photolist.ui.adapter;

import android.widget.ImageView;

import com.example.andrearodriguez.photofeed.entities.Photo;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public interface OnItemClickListernY {
    void onPlaceClick(Photo photo);
    void onShareclick(Photo photo, ImageView img);
    void onDeleteClick(Photo photo);
}
