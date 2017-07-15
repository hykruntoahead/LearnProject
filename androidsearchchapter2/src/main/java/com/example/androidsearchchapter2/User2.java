package com.example.androidsearchchapter2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by heyukun on 2017/7/15.
 */

/**
 * Serializable 是java中的序列化接口，其使用起来简单但是开销很大，序列化和反序列化过程需要大量i/o操作
 * Parcelable 是android的序列化方式 更适用于android平台 效率很高
 */

public class User2 implements Parcelable {
    public int userId;
    public String userName;
    public boolean isMale;

    public User2(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    protected User2(Parcel in) {
        userId = in.readInt();
        userName = in.readString();
        isMale = in.readByte() != 0;
    }

    public static final Creator<User2> CREATOR = new Creator<User2>() {
        @Override
        public User2 createFromParcel(Parcel in) {
            return new User2(in);
        }

        @Override
        public User2[] newArray(int size) {
            return new User2[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeByte((byte) (isMale ? 1 : 0));
    }
}
