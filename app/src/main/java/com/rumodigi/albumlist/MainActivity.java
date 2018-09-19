package com.rumodigi.albumlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rumodigi.albumlist.adapter.AlbumAdapter;
import com.rumodigi.albumlist.model.Album;
import com.rumodigi.albumlist.network.CheckConnectivity;
import com.rumodigi.albumlist.network.GetDataInterface;
import com.rumodigi.albumlist.network.HttpClient;

import java.util.ArrayList;
import java.util.Collections;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private static final String INSTANCE_STATE_LIST = "INSTANCE_STATE_LIST";
    private final String TAG = this.getClass().getName();

    ProgressDialog progressDialog;
    Button loadAlbums;
    AlbumAdapter albumAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager linearLayoutManager;
    ArrayList<Album> albums;
    CheckConnectivity checkConnectivity = new CheckConnectivity();

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        Log.d(TAG, "onSaveInstanceState");
        outState.putParcelableArrayList(INSTANCE_STATE_LIST, albums);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState (Bundle savedInstanceState) {
        Log.d(TAG, "onRestoredInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null){
            albums = savedInstanceState.getParcelableArrayList(INSTANCE_STATE_LIST);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Albums, please wait");
        loadAlbums = findViewById(R.id.load_albums);
        loadAlbums.setOnClickListener(view -> {
            if (checkForInternet()) {
                loadAlbums();
            } else {
                showToast();
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume");
        if(albums != null){
            sortListAndRender();
        } else {
            loadAlbums.setVisibility(View.VISIBLE);
        }
    }

    private boolean checkForInternet(){
        return checkConnectivity.isThereInternetConnection(this);
    }

    private void showToast(){
        Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
    }

    private void loadAlbums() {
        progressDialog.show();
        GetDataInterface getDataInterface = HttpClient.getClientInstance().create(GetDataInterface.class);
        Call<ArrayList<Album>> call = getDataInterface.getAllAlbums();

        call.enqueue(new Callback<ArrayList<Album>>() {
            @Override
            public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                loadAlbums.setVisibility(View.GONE);
                progressDialog.dismiss();
                albums = response.body();
                sortListAndRender();
            }
            @Override
            public void onFailure(Call<ArrayList<Album>> call, Throwable t) {
                progressDialog.dismiss();
                loadAlbums.setVisibility(View.VISIBLE);
            }
        });
    }

    private void sortListAndRender(){
        Collections.sort(albums, (album1, album2) -> {
            if (album1.equals(album2)) {
                return 0;
            }
            return album1.getTitle().compareTo(album2.getTitle());
        });
        recyclerView = findViewById(R.id.recycler_view);
        albumAdapter = new AlbumAdapter(this, albums);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(albumAdapter);
    }
}
