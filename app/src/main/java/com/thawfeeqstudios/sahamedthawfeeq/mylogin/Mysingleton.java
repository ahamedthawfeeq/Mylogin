package com.thawfeeqstudios.sahamedthawfeeq.mylogin;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingleton {
    private static  Mysingleton mysingleton;
    private static Context ctx;
    private RequestQueue requestQueue;
    private Mysingleton(Context ctx){
        this.ctx = ctx;
        requestQueue= getRequestQueue();
    }
    private RequestQueue getRequestQueue(){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return  requestQueue;
    }
    public static  synchronized Mysingleton getmysingleton(Context context){
        if(mysingleton == null){
            mysingleton = new Mysingleton(context);
        }
        return mysingleton;
    }
    public <T>void addToRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
