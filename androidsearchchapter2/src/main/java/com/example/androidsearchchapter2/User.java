package com.example.androidsearchchapter2;

import java.io.Serializable;

/**
 * Created by heyukun on 2017/7/15.
 */

/**
 * Serializable 接口 推荐指定serialVersionUID 虽然不指定系统也会自动生成
 * 但当结构有改变时会出现 无法反序列化 的问题（InvalidClassException）
 */

public class User implements Serializable{
    private static final long serialVersionUID = 519067123721295773L;

    public int userId;
    public String userName;
    public boolean isMale;
    public User(){}

    public User(int userId, String userName, boolean isMale) {
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", isMale=" + isMale +
                '}';
    }
}
