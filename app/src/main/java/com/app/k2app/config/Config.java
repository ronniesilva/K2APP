package com.app.k2app.config;

/**
 *
 *  Neste arquivo de Config ficarão configurações FIXAS
 *
 */

public class Config {

    public static final String TAG = "K2Debug";

    //Shared Preferences Global
    public static final String SHARED_PREFS_NAME = "K2SharedPrefs";
    public static final String SHARED_FILTERS_POSTS = "Posts_All_or_Contacts";
    public static final String SHARED_FILTERS_ID_MIN = "Filter_Id_Min";
    public static final String SHARED_FILTERS_ID_MAX = "Filter_Id_Max";
    public static final String SHARED_FILTERS_MAN = "Filter_ToggleButton_Man";
    public static final String SHARED_FILTERS_GIRL = "Filter_ToggleButton_Girl";
    public static final String SHARED_FILTERS_HETERO = "Filter_ToggleButton_Hetero";
    public static final String SHARED_FILTERS_HOMO = "Filter_ToggleButton_Homo";
    public static final String SHARED_FILTERS_BI = "Filter_ToggleButton_Bi";

    /*
     *  DNS do servidor Wildfly
     */

    // OPENSHIFT
    public static final String K2WEBSERVER = "http://ws-k2pio.rhcloud.com:80";

    //MACBOOK
    //public static final String K2WEBSERVER = "http://192.168.0.4:8080";

}
