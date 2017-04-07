package com.restapi;

import com.android.volley.Request;

/**
 * Created by user on 12/8/2015.
 */
public class RequestHandler {
    final private Request mRequest;

    public RequestHandler(Request request) {
        mRequest = request;
    }

    public void cancel() {
        mRequest.cancel();
    }

}


