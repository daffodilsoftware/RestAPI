package com;

import android.app.Application;

/**
 * Created by shantanu jain on 7/4/17.
 */

public class SampleApplication extends Application{
    private  static RestClient _mRestClient;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

    }

    void init(){
        if(_mRestClient==null)
            _mRestClient = new RestClient(getApplicationContext());
    }

    public static RestClient getRestClient(){
        return _mRestClient;
    }
}
