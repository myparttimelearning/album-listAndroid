package com.rumodigi.albumlist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rumodigi.albumlist.adapter.AlbumAdapter;
import com.rumodigi.albumlist.model.Album;
import com.rumodigi.albumlist.network.CheckConnectivity;
import com.rumodigi.albumlist.network.GetDataInterface;
import com.rumodigi.albumlist.network.HttpClient;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    ProgressDialog progressDialog;
    Button loadAlbums;
    CheckConnectivity checkConnectivity = new CheckConnectivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadAlbums = findViewById(R.id.load_albums);
        loadAlbums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkForInternet()){
                    loadAlbums();
                } else {
                    showToast();
                }
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Albums, please wait");
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
        Call<List<Album>> call = getDataInterface.getAllAlbums();
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                loadAlbums.setVisibility(View.GONE);
                progressDialog.dismiss();
                sortListAndRender(response.body());
            }
            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                progressDialog.dismiss();
                loadAlbums.setVisibility(View.VISIBLE);
            }
        });
    }

    private void sortListAndRender(List<Album> albumList){
        Collections.sort(albumList, (album1, album2) -> {
            if (album1.equals(album2)) {
                return 0;
            }
            return album1.getTitle().compareTo(album2.getTitle());
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        AlbumAdapter albumAdapter = new AlbumAdapter(this, albumList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(albumAdapter);
    }
}
