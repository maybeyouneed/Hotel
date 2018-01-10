package com.example.hotel.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hotel.interfaces.HttpCallBackListener;
import com.example.hotel.data.CommonResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpTask extends AsyncTask<String, Integer, Integer> {

    private Context mContext;
    private static final int SUCCESS = 0;
    private static final int FAILED = 1;
    private static final int ERROR = 2;
    private HttpCallBackListener listener;
    private CommonResponse commonResponse;

    public HttpTask(Context context, HttpCallBackListener listener) {
        mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(String... params) {
        Log.d("AsyncTask", params[0]);
        Log.d("AsyncTask", params[1]);
        Log.d("AsyncTask", params[2]);
        Response response;
        okhttp3.Call call = null;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        if(params[1] != null && params[2] != null){
            RequestBody body = new FormBody.Builder()
                    .add("username", params[1])
                    .add("password", params[2])
                    .build();
            Request request = new Request.Builder()
                    .url(params[0])
                    .post(body)
                    .build();
            call = client.newCall(request);
        }
        try{
            response = call.execute();
            String res = response.body().string();
            response.body().close();
            commonResponse = Utility.handleLoginResponse(res);
            if (commonResponse.getResult()){
                return SUCCESS;
            }else {
                Log.d("失败时返回的message", commonResponse.getMessage());
                return FAILED;
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return ERROR;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case SUCCESS:
                listener.onSuccess(commonResponse);
                break;
            case FAILED:
                listener.onFailed();
                break;
            case ERROR:
                listener.onError();
            default:
                break;
        }
    }
}
