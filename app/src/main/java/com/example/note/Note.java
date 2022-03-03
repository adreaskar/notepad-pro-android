package com.example.note;

import android.os.Parcel;
import android.os.Parcelable;

public class Note implements Parcelable {

    private String title,body,favourite,visibility,user,date;

    public Note(String title, String body, String favourite, String visibility, String user, String date) {
        this.title = title;
        this.body = body;
        this.favourite = favourite;
        this.visibility = visibility;
        this.user = user;
        this.date = date;
    }

    protected Note(Parcel in) {
        title = in.readString();
        body = in.readString();
        favourite = in.readString();
        visibility = in.readString();
        user = in.readString();
        date = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFavourite() {
        return favourite;
    }

    public void setFavourite(String favourite) {
        this.favourite = favourite;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(favourite);
        parcel.writeString(visibility);
        parcel.writeString(user);
        parcel.writeString(date);
    }
}
