package com.jiuan.oa.android.app.andoncontact.im;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by ZhangKong on 2015/8/3.
 */
public class MyRequest extends Request<JSONObject> {

    private  Response.Listener<JSONObject> mListener;

    protected static final String PROTOCOL_CHARSET = "utf-8";

    public MyRequest(int method, String url, Response.Listener<JSONObject> listener,
                     Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }
    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject jsonObject) {
        mListener.onResponse(jsonObject);
    }
}
