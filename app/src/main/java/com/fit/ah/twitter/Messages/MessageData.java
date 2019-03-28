package com.fit.ah.twitter.Messages;

import android.media.Image;

import java.util.Date;

/**
 * Created by AH on 05-Sep-18.
 */

public class MessageData {

    Integer conversationID;
    String username;
    String nickname;
    Date time;
    String content;
    String imgProfile;
    Boolean isMineLastMessage;

    public MessageData(Integer conversationID, String username, String nickname, Date time, String content, String imgProfile, Boolean isMineLastMessage) {
        this.conversationID = conversationID;
        this.username = username;
        this.nickname = nickname;
        this.time = time;
        this.content = content;
        this.imgProfile = imgProfile;
        this.isMineLastMessage = isMineLastMessage;
    }


}
