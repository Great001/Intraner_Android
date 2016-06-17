package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.View.AlphabetScrollBar;
import com.xogrp.tkgz.Widget.CirclePageIndicator;
import com.xogrp.tkgz.Widget.PhotoWallViewPagerAdapter;
import com.xogrp.tkgz.model.EmployeeProfile;
import com.xogrp.tkgz.provider.PhotoWallProvider;
import com.xogrp.tkgz.spi.PhotoWallApiCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wlao on 12/23/2015.
 */
public class PhotoWallFragment extends AbstractTKGZFragment {
    private static final int PHOTO_COUNT_PER_PAGE = 12;
    private AlphabetScrollBar mVAlphabet;
    private ViewPager mVpPhotoWall;
    private CirclePageIndicator mVCirclePageIndicator;
    private List<EmployeeProfile> mPhotoWallResponseList;
    private PhotoWallViewPagerAdapter mPhotoAdapter;
    private List<ArrayList<EmployeeProfile>> mPhotoWallProfileTotalList;
    private int mPosition;
    private ArrayList<AbstractTKGZFragment> mResultList;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_photo_wall_layout;
    }

    @Override
    protected void onTKGZAttach(Activity activity) {
        mResultList = new ArrayList<AbstractTKGZFragment>();
        mPhotoAdapter = new PhotoWallViewPagerAdapter(getChildFragmentManager(), mResultList);
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mVAlphabet = (AlphabetScrollBar) rootView.findViewById(R.id.v_alphabet);
        mVpPhotoWall = (ViewPager) rootView.findViewById(R.id.vp_photo_wall);
        mVpPhotoWall.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mPosition = position;
                refreshAlphabet(position);
            }
        });

        mVAlphabet.setOnTouchBarListener(new AlphabetScrollBar.OnAlphabetScrollBarTouchListener() {
            @Override
            public void onTouch(String alphabet) {
                refreshPhotoPage(alphabet);
                refreshAlphabet(mPosition);
            }
        });
        mVCirclePageIndicator = (CirclePageIndicator) rootView.findViewById(R.id.view_pager_photo_wall);
    }

    @Override
    protected void onTKGZActivityCreated() {
        mPhotoWallProfileTotalList = new ArrayList<>();
        mVpPhotoWall.setAdapter(mPhotoAdapter);

        showSpinner();
        initLoader(PhotoWallProvider.getPhotoWallProvider(TKGZApplication.getInstance().getUserProfile(), this, new PhotoWallApiCallBack.OnPhotoWallApiCallBackListener() {
            @Override
            public void OnSuccess(List<EmployeeProfile> list) {
                mPhotoWallResponseList = list;
                int total = list.size();
                int viewPageCount = total % PHOTO_COUNT_PER_PAGE == 0 ? total / PHOTO_COUNT_PER_PAGE : total / PHOTO_COUNT_PER_PAGE + 1;

                for (int k = 0; k < viewPageCount; k++) {
                    mPhotoWallProfileTotalList.add(new ArrayList<EmployeeProfile>());
                }
                int pageNum = 0;
                int size = total + 1;
                for (int j = 1; j < size; j++) {
                    mPhotoWallProfileTotalList.get(pageNum).add(list.get(j - 1));
                    if (j % (PHOTO_COUNT_PER_PAGE) == 0 && pageNum < viewPageCount - 1) {
                        pageNum++;
                    }
                }
                for (int i = 0; i < viewPageCount; i++) {
                    mResultList.add(PhotoWallPageFragment.newInstance(mPhotoWallProfileTotalList.get(i)));
                }

                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPhotoAdapter.notifyDataSetChanged();
                            if (mPhotoAdapter.getIsAfterNotifyDataSetChanged()) {
                                mVCirclePageIndicator.setViewPager(mVpPhotoWall);
                                refreshAlphabet(0);
                                mPhotoAdapter.setAbleToDoNotifyDataSetChanged(false);
                            }
                        }
                    });
                }
            }

            @Override
            public void OnFailed() {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, getString(R.string.get_photowall_data_faild), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        }));

    }

    @Override
    public String getTransactionTag() {
        return "photo_wall_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.actionbar_title_photo_wall_page);
    }

    public void refreshPhotoPage(String curPosIdx) {
        if (mPhotoWallResponseList != null) {
            int listSize = mPhotoWallResponseList.size();
            for (int i = 0; i < listSize; i++) {
                if (curPosIdx.equalsIgnoreCase(mPhotoWallResponseList.get(i).getMemberName().substring(0, 1))) {
                    mVpPhotoWall.setCurrentItem((i / PHOTO_COUNT_PER_PAGE));
                    break;
                }
            }
        }
    }

    public void refreshAlphabet(int position) {
        List<String> alphabetList = new ArrayList<>();
        if (mPhotoWallProfileTotalList.size() > 0) {
            int employeeSum = mPhotoWallProfileTotalList.get(position).size();
            for (int i = 0; i < employeeSum; i++) {
                alphabetList.add(mPhotoWallProfileTotalList.get(position).get(i).getMemberName().substring(0, 1));
            }
            mVAlphabet.setAlphabetFocus(alphabetList);
        }
    }

}
