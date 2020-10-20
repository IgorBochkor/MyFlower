package com.demo.myflower.stetho;
/* Created by Ihor Bochkor on 19.10.2020.
 */

import android.app.Application;

import com.facebook.stetho.Stetho;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
