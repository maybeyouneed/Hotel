package com.example.hotel.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.hotel.Interfaces.HttpCallBackListener;
import com.example.hotel.data.CommonResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *   {"name":"MongoError",
    "message":"failed to connect to server [localhost:27017] on first connect [MongoError: connect ECONNREFUSED 127.0.0.1:27017]","result":false}
 *     {"name":"Login falied","result":false,"message":"Username or password falied"}
 *     {"name":"Login succeed","result":true,"message":"5utvFrjf1QiFNQ8DKPaHRvgCWktFsEbz"}
 */

//@TODO 解析服务器返回的JSON数据



public class HttpAsyncTask extends AsyncTask<String, Integer, Integer> {

    private Context mContext;
    private static final int SUCCESS = 0;
    private static final int FAILED = 1;
    private static final int ERROR = 2;
    private HttpCallBackListener listener;

    public HttpAsyncTask(Context context, HttpCallBackListener listener) {
        mContext = context;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Integer doInBackground(String... params) {
        Log.d("AsyncTask", params[0]);
        Response response;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        RequestBody body = new FormBody.Builder()
                .add("username", params[1])
                .add("password", params[2])
                .build();
        Request request = new Request.Builder()
                .url(params[0])
                .post(body)
                .build();
        okhttp3.Call call = client.newCall(request);
        try{
            response = call.execute();
            String res = response.body().string();
            response.body().close();
            CommonResponse commonResponse = Utility.parseJsonObject(res);
            if (commonResponse.getResult()){
                return SUCCESS;
            }else {
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
                listener.onSuccess();
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
