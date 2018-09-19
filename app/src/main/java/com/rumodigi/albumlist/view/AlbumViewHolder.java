package com.rumodigi.albumlist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.rumodigi.albumlist.R;

public class AlbumViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    private TextView albumTitle;

    public AlbumViewHolder(View view){
        super(view);
        this.view = view;
        albumTitle = view.findViewById(R.id.title);
    }

    public TextView getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(TextView albumTitle) {
        this.albumTitle = albumTitle;
    }
}
