package com.example.andrearodriguez.photofeed.main.ui;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.andrearodriguez.photofeed.PhotoFeedApp;
import com.example.andrearodriguez.photofeed.PhotoMapFragment;
import com.example.andrearodriguez.photofeed.R;
import com.example.andrearodriguez.photofeed.login.ui.LoginActivity;
import com.example.andrearodriguez.photofeed.main.MainPresenter;
import com.example.andrearodriguez.photofeed.main.ui.adapters.MainSectionPagerAdapter;
import com.example.andrearodriguez.photofeed.photolist.ui.PhotoListFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainView,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.viewPager)
    ViewPager viewPager;

    @Inject
    MainPresenter presenter;
    @Inject
    MainSectionPagerAdapter adapter;
    @Inject
    SharedPreferences sharedPreferences;

    private String photoPath;
    private PhotoFeedApp app;
    private GoogleApiClient apiClient;
    private Location lastKnownLocation;

    private boolean resolvingError = false;
    private static final int REQUEST_PICTURE = 1;
    private static final int REQUEST_RESOLVE_ERROR = 0;
    private static final int PERMISSIONS_REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        app = (PhotoFeedApp) getApplication();
        setupInjection();
        setupNavigation();
        setupGoogleApiClient();

        presenter.onCreate();
    }

    private void setupGoogleApiClient() {
        if (apiClient == null) {
            apiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void setupNavigation() {
        //PhotoFeedApp app = (PhotoFeedApp) getApplication();
        String email = sharedPreferences.getString(app.getEmailKey(), getString(R.string.app_name));
        toolbar.setTitle(email);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);
    }

    private void setupInjection() {

        String[] titles = new String[]{ getString(R.string.main_title_list),
                getString(R.string.main_title_maps)};

        Fragment[] fragments = new Fragment[]{ new PhotoListFragment(),
                new PhotoMapFragment()};

        PhotoFeedApp app = (PhotoFeedApp) getApplication();
        app.getMainComponent(this, getSupportFragmentManager(), fragments, titles).inject(this);
//        String[] titles = new String[]{getString(R.string.main_title_list), getString(R.string.main_title_maps)};
//        Fragment[] fragments = new Fragment[]{new PhotoListFragment(), new PhotoMapFragment()};
////
//        app.getMainComponent(this, getSupportFragmentManager(), fragments, titles).inject(this);
//
//        adapter = new MainSectionPagerAdapter(getSupportFragmentManager(), titles, fragments);
//        sharedPreferences = getSharedPreferences(app.getSharedPreferencesName(), MODE_PRIVATE);
//        presenter = new MainPresenter() {
//            @Override
//            public void onCreate() {
//
//            }
//
//            @Override
//            public void onDestroy() {
//
//            }
//
//            @Override
//            public void logout() {
//
//            }
//
//            @Override
//            public void uploadPhoto(Location location, String path) {
//
//            }
//
//            @Override
//            public void onEventMainThread(MainEvent event) {
//
//            }
 //       };
    }

    @Override
    protected void onStop() {
        apiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiClient.connect();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_RESOLVE_ERROR){
            resolvingError = false;
            if(requestCode == RESULT_OK){
                if(!apiClient.isConnecting()&& !apiClient.isConnected()){
                    apiClient.connect();
                }
            }
        }else if (requestCode == REQUEST_PICTURE) {
                if(resultCode == RESULT_OK){
                    boolean fromCamera = (data == null || data.getData() == null );
                    if(fromCamera){
                        addToGallery();
                    }else{
                        photoPath = getRealPathFromURI(data.getData());
                    }
                    presenter.uploadPhoto(lastKnownLocation, photoPath);
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            logout();
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        presenter.logout();
        sharedPreferences.edit().clear().commit();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_LOCATION);
            }

            return;
        }
        if (LocationServices.FusedLocationApi.getLocationAvailability(apiClient).isLocationAvailable()) {
            lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
            Snackbar.make(viewPager, lastKnownLocation.toString(), Snackbar.LENGTH_SHORT).show();
        } else {
            showSnackbar(R.string.main_error_location_noavaliable);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                        return;
                    }
                    if (LocationServices.FusedLocationApi.getLocationAvailability(apiClient).isLocationAvailable()) {
                        lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(apiClient);
                    } else {
                        showSnackbar(R.string.main_error_location_noavaliable);
                    }
                }
                return;
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        apiClient.connect();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if(resolvingError){
            return;
        }else if(connectionResult.hasResolution()){
            resolvingError = true;

            try {
                connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }else{
            resolvingError = true;
            GoogleApiAvailability.getInstance().getErrorDialog(this, connectionResult.getErrorCode(), REQUEST_RESOLVE_ERROR).show();
        }
    }

    @Override
    public void onUploadInit() {
        showSnackbar(R.string.main_notice_upload_init);

    }

    @Override
    public void onUploadComplete() {
        showSnackbar(R.string.main_notice_upload_complete);
    }

    @Override
    public void onUploadError(String error) {
        showSnackbar(error);

    }
    @OnClick(R.id.fab)
    public void takePicture(){
        Intent chooserIntent = null;

        List<Intent> intentList = new ArrayList<>();
        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent cameraInten = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraInten.putExtra("return-data", true);

        File photoFile = getFile();
        if(photoFile != null){
            cameraInten.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

            if(cameraInten.resolveActivity(getPackageManager()) != null){
                intentList = addIntentsToList(intentList, cameraInten);
            }
        }
        if(pickIntent.resolveActivity(getPackageManager()) != null){
            intentList = addIntentsToList(intentList, pickIntent);
        }
        if(intentList.size() > 0){
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                                                getString(R.string.main_message_picture_source));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }
        if(chooserIntent!=null){
            startActivityForResult(chooserIntent, REQUEST_PICTURE);
        }

    }
    private File getFile(){
        File photoFile = null;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            photoFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            Snackbar.make(viewPager, R.string.main_error_dispatch_camera, Snackbar.LENGTH_SHORT).show();
        }
        photoPath = photoFile.getAbsolutePath();
        return photoFile;
    }

    private List<Intent> addIntentsToList(List<Intent> list, Intent intent){
        List<ResolveInfo> resInfo = getPackageManager().queryIntentActivities(intent, 0);
        for(ResolveInfo resolveInfo: resInfo){
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetIntent = new Intent(intent);
            targetIntent.setPackage(packageName);
            list.add(targetIntent);
        }
        return list;
    }

    private void showSnackbar(String msg){
        Snackbar.make(viewPager, msg, Snackbar.LENGTH_SHORT).show();
    }
    private void showSnackbar(int strResult){
        Snackbar.make(viewPager, strResult, Snackbar.LENGTH_SHORT).show();
    }
    private void addToGallery() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(photoPath);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = null;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            if (contentURI.toString().contains("mediaKey")){
                cursor.close();

                try {
                    File file = File.createTempFile("tempImg", ".jpg", getCacheDir());
                    InputStream input = getContentResolver().openInputStream(contentURI);
                    OutputStream output = new FileOutputStream(file);

                    try {
                        byte[] buffer = new byte[4 * 1024];
                        int read;

                        while ((read = input.read(buffer)) != -1) {
                            output.write(buffer, 0, read);
                        }
                        output.flush();
                        result = file.getAbsolutePath();
                    } finally {
                        output.close();
                        input.close();
                    }

                } catch (Exception e) {
                }
            } else {
                cursor.moveToFirst();
                int dataColumn = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(dataColumn);
                cursor.close();
            }

        }
        return result;
    }
}

