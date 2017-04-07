package com.restapi;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by user on 12/8/2015.
 */
public class VolleyManager {
    private static VolleyManager mInstance;
    private RequestQueue mRequestQueue;
    private Context ctx;

    public VolleyManager(Context context){
        ctx=context;
        mRequestQueue=getRequestQueue();

    }
    public static synchronized VolleyManager getInstance(Context context){
        if(mInstance==null){
            mInstance=new VolleyManager(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue==null){

            mRequestQueue= Volley.newRequestQueue(ctx);
        }
        return mRequestQueue;
    }
    public <T> Request<T> addToRequestQueue(Request<T> req)
    {
        return getRequestQueue().add(req);
    }

    }
