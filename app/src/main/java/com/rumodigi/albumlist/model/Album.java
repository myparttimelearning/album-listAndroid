package com.rumodigi.albumlist.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Album implements Comparable<Album>, Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
    }

    // Parcelling part
    public Album(Parcel in){
        this.title = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        public Album[] newArray(int size) {
            return new Album[size];
        }
    };
}
