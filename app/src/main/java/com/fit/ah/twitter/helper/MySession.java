package com.fit.ah.twitter.helper;

import com.fit.ah.twitter.Login.model.AuthProvjeraVM;
import java.util.Map;

public class MySession {
    public static AuthProvjeraVM logiraniKorisnik;
    public static Map<String, String> authToken;

    public static AuthProvjeraVM getLogiraniKorisnik() {
        return logiraniKorisnik;
    }

    public static void setLogiraniKorisnik(AuthProvjeraVM logiraniKorisnik) {
        MySession.logiraniKorisnik = logiraniKorisnik;
    }

    public static Map<String, String> getAuthToken() {
        return authToken;
    }

    public static void setAuthToken(Map<String, String> authToken) {
        MySession.authToken = authToken;
    }
}
