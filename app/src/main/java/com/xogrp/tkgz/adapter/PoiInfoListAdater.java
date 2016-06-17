package com.xogrp.tkgz.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.xogrp.tkgz.R;
import java.util.List;

/**
 * Created by hliao on 5/31/2016.
 */
public class PoiInfoListAdater extends BaseAdapter {

    private Context mContext;
    private List<PoiInfo> mListPoiInfo;
    private Cursor mCursor;

    public PoiInfoListAdater(Context context){
        this.mContext=context;
    }

    public void setListPoiInfo(List<PoiInfo> list){
        mListPoiInfo =list;
    }

    public void setListHistoryPoiInfo(Cursor cursor) {
        this.mCursor =cursor;
    }

    public Cursor getCursor(){
     return mCursor;
    }

    @Override
   public int getCount() {
        return mListPoiInfo ==null? mCursor.getCount(): mListPoiInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return mListPoiInfo !=null? mListPoiInfo.get(position): mCursor.getPosition();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            MyViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(mContext);
                convertView = inflater.inflate(R.layout.poiresult_layout, null);
                holder = new MyViewHolder();
                holder.mKeyAddr = (TextView) convertView.findViewById(R.id.tv_PoiResult_addrName);
                holder.mDetailAddr = (TextView) convertView.findViewById(R.id.tv_PoiResult_addrDetail);
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder) convertView.getTag();
            }

        if (mListPoiInfo !=null) {
            holder.mKeyAddr.setText(mListPoiInfo.get(position).name);
            holder.mDetailAddr.setText(mListPoiInfo.get(position).address);
        }
        else if(mCursor.moveToPosition(position)) {
            holder.mKeyAddr.setText(mCursor.getString(mCursor.getColumnIndex("addrName")));
            holder.mDetailAddr.setText(mCursor.getString(mCursor.getColumnIndex("addrDetail")));
        }



            return convertView;

    }

    class MyViewHolder{
        TextView mKeyAddr;
        TextView mDetailAddr;
    }

}
