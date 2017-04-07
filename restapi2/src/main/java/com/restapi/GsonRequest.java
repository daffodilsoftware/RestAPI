package com.restapi;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 20-May-15.
 *
 */
public class GsonRequest<T, K> extends Request<K> {
    private final Gson gson = new Gson();
    protected static final String PROTOCOL_CHARSET = "utf-8";
    protected static final String PROTOCOL_CONTENT_TYPE = String.format("application/json; charset=%s", PROTOCOL_CHARSET);
    protected final T mRequestBody;
    private final Class<K> clazz;
    private final Map<String, String> headers;
    private final WeakReference<ResponseListener<K>> mResponseListener;

    public GsonRequest(int method, String url, Class<K> clazz, T requestBody,
                       ResponseListener<K> listener) {
        super(method, url, listener);
        this.clazz = clazz;
        headers = new HashMap<>();
        mRequestBody = requestBody;
        mResponseListener = new WeakReference<ResponseListener<K>>(listener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers != null ? headers : super.getHeaders();
    }

    @Override
    protected Response<K> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            K object;
            if (clazz != String.class) {
                object = gson.fromJson(json, clazz);
            } else {
                object = (K) json;
            }
            return Response.success(
                    object,
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
            Log.e("error",  Log.getStackTraceString(e));
            return Response.error(parseError);
        }
    }

    @Override
    protected void deliverResponse(K response) {
        ResponseListener<K> listener = mResponseListener.get();
        if (listener != null) {
            listener.onSuccessResponse(response);
        }
    }

    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (mRequestBody == null) {
            return new byte[0];
        }
        try {
            if (mRequestBody instanceof JSONObject) {
                JSONObject jsonBody = (JSONObject) mRequestBody;
                String body = jsonBody.toString();
                return body.getBytes(PROTOCOL_CHARSET);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    public void putHeaders(String key, String value) {
        if (headers != null) {
            headers.put(key, value);
        }
    }
}
