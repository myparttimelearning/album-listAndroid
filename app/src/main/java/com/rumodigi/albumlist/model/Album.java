package com.rumodigi.albumlist.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Album implements Comparable<Album> {

    @SerializedName("title")
    private String title;

    public Album(String title){
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object object){
        if(this == object) {
            return true;
        }
        if(!(object instanceof Album)){
            return false;
        }
        Album albumToCompare = (Album)object;
        return this.title.equals(albumToCompare.title);
    }

    @Override
    public int hashCode(){
        return title.hashCode();
    }

    @Override
    public int compareTo(@NonNull Album albumToCompare) {
        return this.title.compareTo(albumToCompare.title);
    }
}
