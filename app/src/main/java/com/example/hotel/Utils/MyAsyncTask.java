package com.example.hotel.Utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 后台发起网络请求
 */

public class MyAsyncTask extends AsyncTask<String, Integer, String> {

    private Context mContext;

    public MyAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        Log.d("AsyncTask", "onPreExecute()");
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("AsyncTask", "doInBackground()");
        Log.d("AsyncTask", params[0]);
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try{
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null){
                response.append(line);
            }
        } catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return response.toString();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPostExecute(String s) {
        Log.d("AsyncTask", "onPostExecute()");
        Toast.makeText(mContext, s ,Toast.LENGTH_SHORT).show();
    }
}
