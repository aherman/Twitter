package com.fit.ah.twitter.Profile.model;

public class EditValidationModel {
    public boolean newPass;
    public boolean oldPass;
    public boolean confirmPass;
    public boolean username;
    public boolean nickname;

    public EditValidationModel(){
        newPass = true;
        oldPass = true;
        confirmPass = true;
        username = true;
        nickname = true;
    }

    public boolean isValid(){
        if(!newPass)
            return false;
        if(!oldPass)
            return false;
        if(!confirmPass)
            return false;
        if(!nickname)
            return false;
        if(!username)
            return false;
        return true;
    }

}

