package com.restapi;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONObject;


/**
 * Created by user on 12/8/2015.
 */
public abstract class RequestCallback<T> implements ResponseListener<T> {

    Gson gson = new Gson();

    @Override
    public void onSuccessResponse(T responseData) {
        onRestResponse(null, responseData);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String message = null;
        if (error instanceof NetworkError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ServerError) {
            message = "The server could not be found. Please try again after some time!!";
        } else if (error instanceof AuthFailureError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof ParseError) {
            message = "Parsing error! Please try again after some time!!";
        } else if (error instanceof NoConnectionError) {
            message = "Cannot connect to Internet...Please check your connection!";
        } else if (error instanceof TimeoutError) {
            message = "Connection TimeOut! Please check your internet connection.";
        }else{

            if (error.networkResponse != null && error.networkResponse.data != null) {
                try {
                    if (error.networkResponse.headers != null) {
                        message = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers));
                        JSONObject jsonObject=new JSONObject(message);
                        message = jsonObject.getString("detail");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        onErrorResponse(error, message);
    }


    public abstract void onRestResponse(Exception e, T result);

    public abstract void onErrorResponse(Exception e, String errorJson);


}
