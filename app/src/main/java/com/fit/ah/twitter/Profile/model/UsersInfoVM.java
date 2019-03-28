package com.fit.ah.twitter.Profile.model;

import java.io.Serializable;
import java.util.Date;

public class UsersInfoVM {
    public int userID;
    public int followersCount;
    public int followingCount;
    public String username;
    public String nickname;
    public String birthDate;
    public String location;
    public String profileImg;
    public String headerImg;

    public UsersInfoVM(){

    }

    public UsersInfoVM(UsersInfoVM userInfo) {
        userID = userInfo.userID;
        followersCount = userInfo.followersCount;
        followingCount = userInfo.followingCount;
        username = userInfo.username;
        nickname = userInfo.nickname;
        birthDate = userInfo.birthDate;
        location = userInfo.location;
        profileImg = userInfo.profileImg;
    }
}
