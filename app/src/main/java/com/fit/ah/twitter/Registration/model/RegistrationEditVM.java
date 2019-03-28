package com.fit.ah.twitter.Registration.model;

public class RegistrationEditVM {
    public boolean username;
    public boolean nickname;
    public boolean birthDate;
    public boolean email;
    public boolean countryID;
    public boolean password;
    public boolean confirmPass;

    public RegistrationEditVM(){
        username = true;
        nickname = true;
        birthDate = true;
        email = true;
        countryID = true;
        password = true;
        confirmPass = true;
    }

    public boolean IsValid(){
        if(!username)
            return false;
        if(!nickname)
            return false;
        if(!birthDate)
            return false;
        if(!email)
            return false;
        if(!countryID)
            return false;
        if(!password)
            return false;
        if(!confirmPass)
            return false;
        return true;
    }
}
