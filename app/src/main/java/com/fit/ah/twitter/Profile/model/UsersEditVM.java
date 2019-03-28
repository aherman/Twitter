package com.fit.ah.twitter.Profile.model;

public class UsersEditVM {
    public String nickname;
    public String imageProfile;
    public String imageHeader;

    public UsersEditVM(UsersEditVM result) {
        nickname = result.nickname;
        imageProfile = result.imageProfile;
        imageHeader = result.imageHeader;
    }
}
