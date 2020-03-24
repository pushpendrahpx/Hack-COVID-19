package com.koronakiller.geolocation.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class User implements Parcelable {
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
    private static User userInstance;
    private String id;
    private String userName;
    private String password;
    private String phoneNo;
    private String emailId;

    private User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.userName = in.readString();
        this.password = in.readString();
        this.phoneNo = in.readString();
        this.emailId = in.readString();
    }

    public static User getUser(String userName, String password, String phoneNo, String emailId) {
        if (userInstance == null) {
            userInstance = new User();
            userInstance.userName = userName;
            userInstance.password = password;
            userInstance.phoneNo = phoneNo;
            userInstance.emailId = emailId;
        }
        return userInstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getEmailId() {
        return emailId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userName);
        dest.writeString(this.password);
        dest.writeString(this.phoneNo);
        dest.writeString(this.emailId);
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(userInstance);
//        String jsonString;
//        jsonString = " \"name\":\"" + userName + "\", " +
//                " \"password\":\"" + password + "\"," +
//                "\"phone\": \" " + phoneNo + "\"," +
//                "\"email\":\"" + emailId + "\",";
//        return jsonString;
    }
}
