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
    private String name;
    private String password;
    private String phone;
    private String email;

    private User() {
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
    }

    public static User getUser(String userName, String password, String phoneNo, String emailId) {
        if (userInstance == null) {
            userInstance = new User();
            userInstance.name = userName;
            userInstance.password = password;
            userInstance.phone = phoneNo;
            userInstance.email = emailId;
        }
        userInstance.name = userName;
        userInstance.password = password;
        userInstance.phone = phoneNo;
        userInstance.email = emailId;
        return userInstance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNo() {
        return phone;
    }

    public String getEmailId() {
        return email;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.password);
        dest.writeString(this.phone);
        dest.writeString(this.email);
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
