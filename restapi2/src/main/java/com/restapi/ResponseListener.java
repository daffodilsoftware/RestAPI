package com.restapi;

import com.android.volley.Response;

/**
 * Created by user on 12/8/2015.
 */
public interface ResponseListener<T> extends Response.ErrorListener{
    void onSuccessResponse(T responseData);
}
