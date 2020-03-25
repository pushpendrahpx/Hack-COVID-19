package com.koronakiller.stayqurantine.utils;

import android.os.Parcel;
import android.os.Parcelable;

import com.koronakiller.stayqurantine.models.User;

import java.util.HashMap;
import java.util.Map;

public class RequestPackage implements Parcelable {

    public static final Creator<RequestPackage> CREATOR = new Creator<RequestPackage>() {
        @Override
        public RequestPackage createFromParcel(Parcel source) {
            return new RequestPackage(source);
        }

        @Override
        public RequestPackage[] newArray(int size) {
            return new RequestPackage[size];
        }
    };
    private String endPoint;
    private String method = "GET";
    private Map<String, User> params = new HashMap<>();

    public RequestPackage() {
    }

    protected RequestPackage(Parcel in) {
        this.endPoint = in.readString();
        this.method = in.readString();
        int paramsSize = in.readInt();
        this.params = new HashMap<String, User>(paramsSize);
        for (int i = 0; i < paramsSize; i++) {
            String key = in.readString();
            User value = in.readParcelable(User.class.getClassLoader());
            this.params.put(key, value);
        }
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, User> getParams() {
        return params;
    }

    public void setParams(String key, User value) {
        params.put(key, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.endPoint);
        dest.writeString(this.method);
        dest.writeInt(this.params.size());
        for (Map.Entry<String, User> entry : this.params.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), flags);
        }
    }
}
