package com.xogrp.tkgz.spi;

import com.xogrp.tkgz.model.Initiator;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.xoapp.model.MemberProfile;
import com.xogrp.xoapp.spi.RESTfulApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jdeng on 5/31/2016.
 */
public class GetAllUsersCallback extends RESTfulApiClient.RESTfulApiCallback {
    private OnGetAllUsersListener onGetAllUsersListener;

    public GetAllUsersCallback(MemberProfile memberProfile, OnGetAllUsersListener onGetAllUsersListener) {
        super(memberProfile);
        this.onGetAllUsersListener = onGetAllUsersListener;
    }

    @Override
    public void readResponseBody(int statusCode, InputStream inStream) throws IOException, JSONException {
        JSONObject jsonObject=new JSONObject(readResponseBodyAsString(inStream));
        if (statusCode==200){
            List<Initiator> initiatorList=new ArrayList<>();
            JSONArray jsonArray=jsonObject.getJSONArray("employee_information_list");
            int size=jsonArray.length();
            for (int i=0;i<size;++i){
                initiatorList.add(Initiator.fromJSON(jsonArray.getJSONObject(i)));
            }
            onGetAllUsersListener.getAllUsersSuccess(initiatorList);
        }else {
            onGetAllUsersListener.getAllUsersError(jsonObject.getString("message").toString());
        }


    }

    public interface OnGetAllUsersListener{
       public void getAllUsersSuccess(List<Initiator> initiatorList);
       public void getAllUsersError(String message);
    }

}
