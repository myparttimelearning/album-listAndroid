package com.rumodigi.albumlist.network;

import com.rumodigi.albumlist.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetData {

    @GET("/albums")
    Call<List<Album>> getAllAlbums();
}
