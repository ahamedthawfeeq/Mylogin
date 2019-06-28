package com.thawfeeqstudios.sahamedthawfeeq.mylogin;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class conchecker {
    private Context context;
    conchecker(Context ctx){
        this.context = ctx;
    }
    public boolean isconnected(){
        ConnectivityManager connectivityManager= (ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null){
                return (networkInfo.getState()==NetworkInfo.State.CONNECTED);
            }
        }
        return false;
    }
}
