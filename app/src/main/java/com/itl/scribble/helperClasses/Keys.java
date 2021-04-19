package com.itl.scribble.helperClasses;

public class Keys {
    public static final String HEROKU_URL = "https://scribblebygulshan.herokuapp.com/";
    public static final String LOCALHOST_URL = "http://192.168.43.252:8000/";
    public static final String SOCKET_URL = LOCALHOST_URL;

    public static final String NOTE_OF_DAY = "profiles/createnote/";

    public static final String URL_REGISTRATION = "profiles/registration/";
    public static final String URL_LOGIN = "profiles/login/";
    public static final String URL_LOGOUT = "rest-auth/registration/";
    public static final String URL_NOTELIST = "profiles/notelist/";

    public static final String LOGGED_IN = "isLoggedIn";


    private static final int TIMEOUT = 60000;

    public static final String PREFERENCE_FILE_NAME = "user_profiles";
    public static final String PREFERENCE_AUTH_KEY = "AuthCode";


    public static int getTIMEOUT() {
        return TIMEOUT;
    }


    public static final String TAG_LOGIN = "LoginTag";
    public static final String TAG_GET_NOTE_FOR_A_DAY = "get_note_for_day";
    public static final String TAG_PROFILES_NOTEFORDAY = "noteforday";
    public static final String TAG_POST_NOTE_FOR_A_DAY = "post_note_for_day";
    public static final String TagForAPI_POST_NOTE_FOR_DAY = "postnotefordaytagforAPI";
    public static final String TAG_GET_NOTE_LIST = "getNoteList";
    public static final String TagForAPI_GET_NOTE_LIST = "profiles/notelist";
    public static final String TAG_POST_REGISTER="postRegister";
    public static final String TagForAPIRegister="profiles/register";

    public static final String happy="Happy";
    public static final String surprise="Surprise";
    public static final String sad="Sad";
    public static final String fear="Fear";
    public static final String angry="Angry";

    public static final String polarity="polarity";
    public static final String subjectivity="subjectivity";

    public static final String YOUTUBE_API_KEY = "AIzaSyAaoSKUCCiJpbl8ZjuzRMr_fxmLO_QWVPo";



}
