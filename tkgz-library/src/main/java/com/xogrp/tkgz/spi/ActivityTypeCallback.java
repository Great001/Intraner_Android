package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.ActivityType;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;





/**
 * Created by jdeng on 4/28/2016.
 */
public class ActivityTypeCallback extends RESTfulApiClient.RESTfulApiCallback {
    private OnActivityTypeListner onActivityTypeListner;
    private List<ActivityType> ActivityTypelist;

    public ActivityTypeCallback( MemberProfile memberProfile,OnActivityTypeListner mOnActivityTypeListner) {
        super(memberProfile);
        this.onActivityTypeListner = mOnActivityTypeListner;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        String TypeString=readResponseBodyAsString(inStream);
        JSONObject jsonObject=new JSONObject(TypeString);
        if (statusCode==200){
            JSONArray jsonArray=jsonObject.getJSONArray("event_type_list");
            int size=jsonArray.length();
            ActivityTypelist=new ArrayList<>();
            for (int i=0;i<size;++i){
                ActivityTypelist.add(new ActivityType(jsonArray.getJSONObject(i).getInt("type_id"),jsonArray.getJSONObject(i).getString("type_name")));
            }
            onActivityTypeListner.GetActivityType((ArrayList<ActivityType>) ActivityTypelist);
        }else if(statusCode==400){
            onActivityTypeListner.Error(jsonObject.getString("message"));
        }

    }

    public interface OnActivityTypeListner{
        void GetActivityType(ArrayList<ActivityType> list);
        void Error(String message);
    }

}
