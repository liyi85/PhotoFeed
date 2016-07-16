package com.example.andrearodriguez.photofeed.photolist.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.example.andrearodriguez.photofeed.PhotoFeedApp;
import com.example.andrearodriguez.photofeed.R;
import com.example.andrearodriguez.photofeed.entities.Photo;
import com.example.andrearodriguez.photofeed.photolist.PhotoListPresenter;
import com.example.andrearodriguez.photofeed.photolist.ui.adapter.OnItemClickListernY;
import com.example.andrearodriguez.photofeed.photolist.ui.adapter.PhotoListAdapter;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoListFragment extends Fragment implements PhotoListView, OnItemClickListernY {


    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.progresBarPL)
    ProgressBar progresBarPL;
    @Bind(R.id.container)
    FrameLayout container;

    @Inject
    PhotoListAdapter adapter;
    @Inject
    PhotoListPresenter presenter;

    public PhotoListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupInjection();
        presenter.onCreate();
    }

    @Override
    public void onDestroy() {
        presenter.unsubscribe();
        presenter.onDestroy();
        super.onDestroy();
    }

    private void setupInjection() {
        PhotoFeedApp app = (PhotoFeedApp) getActivity().getApplication();
        app.getPhotoListComponet(this, this, this).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photo_list, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView();
        presenter.subscribe();
        return view;
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    @Override
    public void showList() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progresBarPL.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progresBarPL.setVisibility(View.GONE);
    }

    @Override
    public void addPhoto(Photo photo) {
        adapter.addPhoto(photo);
    }

    @Override
    public void removePhoto(Photo photo) {
        adapter.removePhoto(photo);
    }

    @Override
    public void onPhotosError(String error) {
        Snackbar.make(container, error, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onPlaceClick(Photo photo) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:" + photo.getLatitud() +"," + photo.getLongitud()));
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onShareclick(Photo photo, ImageView img) {
        Bitmap bitmap = ((GlideBitmapDrawable)img.getDrawable()).getBitmap();
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null, null);
        Uri imageUri =  Uri.parse(path);

        share.putExtra(Intent.EXTRA_STREAM, imageUri);
        startActivity(Intent.createChooser(share, getString(R.string.photolist_message_share)));
    }

    @Override
    public void onDeleteClick(Photo photo) {
        presenter.removePhoto(photo);
    }
}
