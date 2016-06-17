package com.xogrp.tkgz.fragment;

import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.adapter.PoiInfoListAdater;
import com.xogrp.tkgz.util.AddrSearchDatabaseUtil;

import java.util.List;

/**
 * Created by hliao on 5/30/2016.
 */
public class AddressSearchFragment extends AbstractTKGZFragment implements View.OnClickListener{
    private EditText mEtAddress;
    private ImageView mIvBack,mIvCleanEdit;
    private TextView mTvRecordRemind;
    private ListView mLvSearchResult,mLvSearchHistroy;
    private ScrollView mSvHistoryRecord;
    private PoiSearch mPoiSearch;
    private List<PoiInfo> mListResult;
    private ReturnToCreateActicityPage mIfReturn;
    private Cursor mCursor;
    private PoiInfoListAdater mPoiAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.address_search_layout;
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mEtAddress = (EditText) rootView.findViewById(R.id.et_address);
        mTvRecordRemind = (TextView) rootView.findViewById(R.id.tv_record_remind);
        mIvBack = (ImageView) rootView.findViewById(R.id.iv_back);
        mIvCleanEdit = (ImageView) rootView.findViewById(R.id.iv_edit_clear);
        mLvSearchResult = (ListView) rootView.findViewById(R.id.lv_address_result);
        mLvSearchHistroy = (ListView) rootView.findViewById(R.id.lv_seached_history);
        mSvHistoryRecord=(ScrollView)rootView.findViewById(R.id.sv_address_history);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mPoiSearch=PoiSearch.newInstance();
        mIvBack.setOnClickListener(this);
        mIvCleanEdit.setOnClickListener(this);
        mTvRecordRemind.setOnClickListener(this);
        mLvSearchResult.setOnItemClickListener(mSearchResultListener);
        mLvSearchHistroy.setOnItemClickListener(mSearchHistoryListener);
        mEtAddress.addTextChangedListener(mTwAddrKey);
        mEtAddress.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    mSvHistoryRecord.setVisibility(View.GONE);
                    mLvSearchResult.setVisibility(View.VISIBLE);
                    mIvCleanEdit.setVisibility(View.VISIBLE);
                    mIvCleanEdit.setClickable(true);
                }
            }
        });
        mPoiAdapter=new PoiInfoListAdater(getContext());
        GetDatabaseAndSetView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                hideKeyboard();
                getOnScreenNavigationListener().goBackFromCurrentPage();
                break;
            case R.id.iv_edit_clear:
                mEtAddress.setText("");
                break;
            case R.id.tv_record_remind:
                AddrSearchDatabaseUtil.DeleteTable(getActivity());
                mTvRecordRemind.setTextColor(getResources().getColor(R.color.APP_GRAY));
                mTvRecordRemind.setText(getString(R.string.not_history_record));
                mLvSearchHistroy.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    protected  void GetDatabaseAndSetView(){
        mCursor=AddrSearchDatabaseUtil.getSearchRecord(getActivity());
        mTvRecordRemind.setText(getString(mCursor.getCount() > 0 ? R.string.clean_history_record : R.string.not_history_record));
        mTvRecordRemind.setTextColor(getResources().getColor(mCursor.getCount() > 0 ? R.color.green_app_system : R.color.APP_GRAY));
        if(mCursor.getCount()>0) {
            mPoiAdapter.setListHistoryPoiInfo(mCursor);
            mLvSearchHistroy.setAdapter(mPoiAdapter);
            setListViewHeightBasedOnChildren(mLvSearchHistroy);
        }else{
            mLvSearchResult.setVisibility(View.VISIBLE);
            mLvSearchHistroy.setVisibility(View.GONE);
        }
    }


    ListView.OnItemClickListener mSearchResultListener =new ListView.OnItemClickListener(){
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String addrName=mListResult.get(position).name;
            String addrDetail=mListResult.get(position).address;
            LatLng locat=mListResult.get(position).location;

            AddrSearchDatabaseUtil.insertIntoTable(getActivity(),addrName,addrDetail,locat.latitude,locat.longitude);

            getOnScreenNavigationListener().goBackFromCurrentPage();
            mIfReturn=(ReturnToCreateActicityPage)getActivity().getSupportFragmentManager().findFragmentByTag(CreateActivityPage1Fragment.TRANSACTION_TAG);
            mIfReturn.locatInBaiduMap(locat,addrName,addrDetail);
            mPoiSearch.destroy();
            AddrSearchDatabaseUtil.closeDatabase();
        }
    };

    ListView.OnItemClickListener mSearchHistoryListener =new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCursor.moveToPosition(position);
            String addrName=mCursor.getString(mCursor.getColumnIndex("addrName"));
            String addrDetail= mCursor.getString(mCursor.getColumnIndex("addrDetail"));
            double latitude= mCursor.getDouble(mCursor.getColumnIndex("latitude"));
            double longitude= mCursor.getDouble(mCursor.getColumnIndex("longitude"));
            LatLng locat=new LatLng(latitude,longitude);
            getOnScreenNavigationListener().goBackFromCurrentPage();
            mIfReturn=(ReturnToCreateActicityPage)getActivity().getSupportFragmentManager().findFragmentByTag(CreateActivityPage1Fragment.TRANSACTION_TAG);
            mIfReturn.locatInBaiduMap(locat,addrName,addrDetail);
            mPoiSearch.destroy();
            AddrSearchDatabaseUtil.closeDatabase();
            mCursor.close();
        }
    };

    TextWatcher mTwAddrKey =new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String KeyAddress=s.toString();
            mPoiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
                @Override
                public void onGetPoiResult(PoiResult poiResult) {
                    mListResult=poiResult.getAllPoi();
                    if(mListResult!=null) {
                        mPoiAdapter.setListPoiInfo(mListResult);
                        mLvSearchResult.setAdapter(mPoiAdapter);
                    }
                }
                @Override
                public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

                }
            });
            mPoiSearch.searchInCity(new PoiCitySearchOption()
                    .city(getString(R.string.gz))
                    .keyword(KeyAddress)
                    .pageNum(1));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public String getTransactionTag() {
        return null;
    }

    @Override
    public boolean isHideActionBar() {
        return true;
    }

    @Override
    public String getActionBarTitle() {
        return null;
    }


    @Override
    public boolean isEmptyTopMargin() {
        return true;
    }


    public interface ReturnToCreateActicityPage {
        void locatInBaiduMap(LatLng locat,String addrName,String addrDetail);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;

        Adapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        int count=listAdapter.getCount();
        for (int i = 0; i < count; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
