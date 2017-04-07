package com.restapi;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.JsonSyntaxException;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by daffolap-164 on 25/4/16.
 */
public class MultipartRequest extends Request<String> {
    private MultipartEntity entity = new MultipartEntity();
    private static final String FILE_PART_NAME = "image";
    private static final String OLD_IMAGE_URL = "oldImageUrl";
    private static final String FILE_IMAGE_TYPE = "imageType";

    private final Map<String, String> headers;
    private final Response.Listener<String> mListener;
    private File mFilePart;

    public MultipartRequest(String url, Response.ErrorListener errorListener, Response.Listener<String> listener, File file, String imageType , String oldImageUrl) {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        headers = new HashMap<>();
        buildMultipartEntity(oldImageUrl,imageType);
    }

    private void buildMultipartEntity(String oldImageUrl, String imageType) {
        try {
            entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
            entity.addPart(FILE_IMAGE_TYPE, new StringBody(imageType));
            entity.addPart(OLD_IMAGE_URL,new StringBody(oldImageUrl));
        } catch (Exception e) {
            VolleyLog.e("UnsupportedEncodingException");
        }
    }

    @Override
    public String getBodyContentType() {
        return entity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        Log.e("TAG","Getting request body");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            entity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(
                    json,
                    HttpHeaderParser.parseCacheHeaders(response)
            );
        } catch (UnsupportedEncodingException e) {
            ParseError parseError = new ParseError(response);
            parseError.initCause(e);
            VolleyLog.e("Error Unsupported encoding", e);
            return Response.error(parseError);
        } catch (JsonSyntaxException e) {
            ParseError parseError = new ParseError(response);
            parseError.initCause(e);
            VolleyLog.e("Error Parsing JSON", e);
            Log.e("error", Log.getStackTraceString(e));
            return Response.error(parseError);
        }
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    public void putHeaders(String key, String value) {
        if (headers != null) {
            headers.put(key, value);
        }
    }

}
