package com.fit.ah.twitter.People;

import android.media.Image;

/**
 * Created by AH on 07-Sep-18.
 */

public class PeopleData {

    Image imgProfile;
    String username;
    String nickname;
    String bio;

    public PeopleData(String username, String nickname, String bio) {
        this.username = username;
        this.nickname = nickname;
        this.bio = bio;
    }

    public Image getImgProfile() {
        return imgProfile;
    }

    public String getBio(){
        return bio;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }
}
