package com.rumodigi.albumlist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rumodigi.albumlist.R;
import com.rumodigi.albumlist.model.Album;
import com.rumodigi.albumlist.view.AlbumViewHolder;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumViewHolder> {

    private List<Album> albumList;
    private Context context;

    AlbumAdapter(Context context, List<Album> albumList){
        this.context = context;
        this.albumList = albumList;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_album_row, null);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder albumViewHolder, int position){
        albumViewHolder.getAlbumTitle().setText(albumList.get(position).getTitle());
    }

    @Override
    public int getItemCount(){
        return albumList.size();
    }
}
