package com.koronakiller.stayqurantine.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

public class User implements Parcelable {
    private static User userInstance;
    private String id;
    private String name;
    private String password;
    private String phone;
    private String email;
    private int score;

    private User() {
    }

    private User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public static User getUser(String phone, String password) {
        if (userInstance == null) {
            userInstance = new User();
            userInstance.password = password;
            userInstance.phone = phone;
        }
        userInstance.password = password;
        userInstance.phone = phone;
        return userInstance;
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

    public static User getUserFromJson(String jsonData) {
        Gson gson = new Gson();
        return (User) gson.fromJson(jsonData, User.class);
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
        dest.writeInt(this.score);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.password = in.readString();
        this.phone = in.readString();
        this.email = in.readString();
        this.score = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

       // Added


        Context context;

        public void removeUser()
        {
            sharedPreferences.edit().clear().commit();
        }

        public String GetName() {
            name = sharedPreferences.getString("userdata","");
            return name;
        }

        public void setName(String name) {
            this.name = name;
            sharedPreferences.edit().putString("userdata",name).commit();

        }

        SharedPreferences sharedPreferences;

        public User(Context context)
        {
            this.context=context;
            sharedPreferences =context.getSharedPreferences("userdata",context.MODE_PRIVATE);

        }

    }

