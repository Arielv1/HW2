package com.example.hw2;

import android.app.Application;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        MyToaster.initHelper(this);
        MySP.initHelper(this);

    }

}
