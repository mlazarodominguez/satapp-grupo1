package com.example.satapp.common;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public static MyApp instance;

    public static MyApp getInstance(){
        return instance;
    }

    public static Context getContext(){
        return instance;
    }

    public void onCreate(){
        instance= this;
        super.onCreate();
    }

}
