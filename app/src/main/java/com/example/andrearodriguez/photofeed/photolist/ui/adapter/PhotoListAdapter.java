package com.example.andrearodriguez.photofeed.photolist.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andrearodriguez.photofeed.R;
import com.example.andrearodriguez.photofeed.domine.Util;
import com.example.andrearodriguez.photofeed.entities.Photo;
import com.example.andrearodriguez.photofeed.libs.base.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by andrearodriguez on 7/15/16.
 */
public class PhotoListAdapter extends RecyclerView.Adapter<PhotoListAdapter.ViewHolder> {


    private Util utils;
    private List<Photo> photoList;
    private ImageLoader imageLoader;
    private OnItemClickListernY onItemClickListernY;

    public PhotoListAdapter(Util utils, List<Photo> photoList, ImageLoader imageLoader, OnItemClickListernY onItemClickListernY) {
        this.utils = utils;
        this.photoList = photoList;
        this.imageLoader = imageLoader;
        this.onItemClickListernY = onItemClickListernY;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_photos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Photo currentPhoto = photoList.get(position);
        holder.setOnItemClickListener(currentPhoto, onItemClickListernY);

        imageLoader.load(holder.imgMain, currentPhoto.getUrl());
        imageLoader.load(holder.imgAvatar, utils.getAvatarUrl(currentPhoto.getEmail()));
        holder.txtUser.setText(currentPhoto.getEmail());
        double lat = currentPhoto.getLatitud();
        double lng = currentPhoto.getLongitud();

        if (lat != 0.0 && lng != 0.0) {
            holder.txtPlace.setText(utils.getFromLocation(lat, lng));
            holder.txtPlace.setVisibility(View.VISIBLE);
        } else {
            holder.txtPlace.setVisibility(View.GONE);
        }

        if (currentPhoto.isPublishedByMe()){
            holder.imgDelete.setVisibility(View.VISIBLE);
        } else {
            holder.imgDelete.setVisibility(View.GONE);
        }

    }
    public void addPhoto(Photo photo) {
        photoList.add(0, photo);
        notifyDataSetChanged();
    }

    public void removePhoto(Photo photo) {
        photoList.remove(photo);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imgAvatar)
        CircleImageView imgAvatar;
        @Bind(R.id.txtUser)
        TextView txtUser;
        @Bind(R.id.layoutHeader)
        LinearLayout layoutHeader;
        @Bind(R.id.imgMain)
        ImageView imgMain;
        @Bind(R.id.txtPlace)
        TextView txtPlace;
        @Bind(R.id.imgShare)
        ImageButton imgShare;
        @Bind(R.id.imgDelete)
        ImageButton imgDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setOnItemClickListener(final Photo photo, final OnItemClickListernY listernY){
            txtPlace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listernY.onPlaceClick(photo);
                }
            });

            imgShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listernY.onShareclick(photo, imgMain);
                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listernY.onDeleteClick(photo);
                }
            });
        }
    }
}
