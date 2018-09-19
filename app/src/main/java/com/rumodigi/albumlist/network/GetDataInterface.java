package com.rumodigi.albumlist.network;

import com.rumodigi.albumlist.model.Album;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataInterface {

    @GET("/albums")
    Call<ArrayList<Album>> getAllAlbums();
}
