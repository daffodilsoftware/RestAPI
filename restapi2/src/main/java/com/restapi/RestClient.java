package com.restapi;

import android.content.Context;


/**
 * Created by user on 12/8/2015.
 */
public class RestClient {

    public VolleyManager getmVolleyManager() {
        return mVolleyManager;
    }


    VolleyManager mVolleyManager;

    public RestClient(Context appContext) {
        mVolleyManager = new VolleyManager(appContext);
    }

   /* *//**
     * Login Request
     *
     * @param listener
     * @param request
     * @return
     *//*
    public RequestHandler _mLoginRequestMethod(final RequestCallback<LoginResponseModel> listener, JSONObject request) {

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, ApiConfig.SIGNIN_API, LoginResponseModel.class, request, listener);
        gsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                2,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));
        gsonRequest.setShouldCache(false);
        gsonRequest.putHeaders(ApiConfig.api_key_string, ApiConfig.api_key);
        return new RequestHandler(mVolleyManager.addToRequestQueue(gsonRequest));
    }*/


   /* public RequestHandler _mUploadProfileImageRequestMethod(final RequestCallback<ProfileImageUploadResponse> listener, File file, String imageType, String authToken, String oldImageUrl) {


        MultipartRequest multipartRequest = new MultipartRequest(ApiConfig.PROFILE_IMAGE_UPLOAD_API, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
            }
        }, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ProfileImageUploadResponse object = gson.fromJson(response, ProfileImageUploadResponse.class);
                listener.onSuccessResponse(object);
            }
        },file,imageType,oldImageUrl);
                multipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                        8000,
                        2,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                ));
        multipartRequest.setShouldCache(false);
        multipartRequest.putHeaders(ApiConfig.api_key_string, ApiConfig.api_key);
        multipartRequest.putHeaders(ApiConfig.auth_token_string,authToken);
        return new RequestHandler(mVolleyManager.addToRequestQueue(multipartRequest));
    }*/


}