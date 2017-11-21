package com.example.hotel.Utils;

import android.text.TextUtils;

import com.example.hotel.data.CommonResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * .
 */

public class Utility {

    public static CommonResponse parseJsonObject(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONObject object = new JSONObject(response);
                CommonResponse commonResponse = new CommonResponse();
                commonResponse.setName(object.getString("name"));
                commonResponse.setResult(object.getBoolean("result"));
                commonResponse.setMessage(object.getString("message"));
                return commonResponse;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return null;
    }

}
