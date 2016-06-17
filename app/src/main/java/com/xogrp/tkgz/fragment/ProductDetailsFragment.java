package com.xogrp.tkgz.fragment;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xogrp.tkgz.R;
import com.xogrp.tkgz.TKGZApplication;
import com.xogrp.tkgz.Widget.TKGZCustomDialog;
import com.xogrp.tkgz.listener.OnRefreshUIListener;
import com.xogrp.tkgz.model.ProductProfile;
import com.xogrp.tkgz.provider.ExchangeProductProvider;
import com.xogrp.tkgz.provider.ProductDetailProvider;
import com.xogrp.tkgz.spi.ExchangeProductApiCallBack;
import com.xogrp.tkgz.spi.ProductDetailApiCallBack;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by ayu on 9/29/2015 0029.
 */
public class ProductDetailsFragment extends AbstractTKGZFragment implements ViewPager.OnPageChangeListener {
    private Button mBtnExchange;
    private TextView mTvName;
    private TextView mTvIntegral;
    private TextView mTvExpiryDate;
    private TextView mTvExchangedCount;
    private TextView mTvDescription;
    private TextView mTvRemaining;
    private TextView mTvTxtRemaining;
    private ViewPager mVpShowPic;
    private LinearLayout mLlDotLayout;
    private static final String BUNDLE_KEY_ID = "id";
    private boolean mIsTouch = false;
    private int mPagerCount;
    private int mCurrentItem;
    private List<ImageView> mImageList;
    private ProductProfile mProduct;
    private ScheduledExecutorService mScheduledExecutorService;
    private OnRefreshUIListener mOnRefreshUIListener;
    private WeakHandler mWeakHandler = new WeakHandler(this);
    private WeakRunnable mWeakRunnable = new WeakRunnable(this);

    public static ProductDetailsFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString(BUNDLE_KEY_ID, id);
        ProductDetailsFragment fragment = new ProductDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onTKGZAttach(Activity activity) {
        super.onTKGZAttach(activity);
        if (activity instanceof OnRefreshUIListener) {
            mOnRefreshUIListener = (OnRefreshUIListener) activity;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_show_product_detail;
    }

    private static class WeakHandler extends Handler {
        private final WeakReference<ProductDetailsFragment> mHandler;

        private WeakHandler(ProductDetailsFragment fragment) {
            mHandler = new WeakReference<ProductDetailsFragment>(fragment);
        }
    }

    private static class WeakRunnable implements Runnable {
        private final WeakReference<ProductDetailsFragment> mRunnable;

        private WeakRunnable(ProductDetailsFragment fragment) {
            mRunnable = new WeakReference<ProductDetailsFragment>(fragment);
        }

        @Override
        public void run() {
            mRunnable.get().mVpShowPic.setCurrentItem(++mRunnable.get().mCurrentItem);
        }
    }

    @Override
    protected void onTKGZCreateView(View rootView) {
        mVpShowPic = (ViewPager) rootView.findViewById(R.id.vp_autoScroll);
        mLlDotLayout = (LinearLayout) rootView.findViewById(R.id.ll_dotLayout);
        mLlDotLayout.removeAllViews();
        mTvName = (TextView) rootView.findViewById(R.id.tv_product_name);
        mTvExpiryDate = (TextView) rootView.findViewById(R.id.tv_expiry_date);
        mTvRemaining = (TextView) rootView.findViewById(R.id.tv_remaining);
        mTvTxtRemaining = (TextView) rootView.findViewById(R.id.tv_txt_remaining);
        mTvIntegral = (TextView) rootView.findViewById(R.id.tv_integral);
        mTvExchangedCount = (TextView) rootView.findViewById(R.id.tv_exchanged_count);
        mTvDescription = (TextView) rootView.findViewById(R.id.tv_describe);
        mBtnExchange = (Button) rootView.findViewById(R.id.btn_exchange);
    }

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    mIsTouch = true;
                    stopAutoScroll();
                    break;
                case MotionEvent.ACTION_UP:
                    mIsTouch = false;
                    startAutoScroll();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private void getProductDetail() {
        String id = getArguments().getString(BUNDLE_KEY_ID);
        showSpinner();
        initLoader(ProductDetailProvider.getProductDetailProvider(id, this, TKGZApplication.getInstance().getUserProfile(), new ProductDetailApiCallBack.OnProductDetailApiListener() {
            @Override
            public void showProductDetail(ProductProfile productProfile) {
                mProduct = productProfile;
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            refreshUI();
                        }
                    });
                }
            }
        }));
    }

    @Override
    protected void onTKGZActivityCreated() {
        mImageList = new ArrayList<>();
        getProductDetail();
    }

    private void refreshUI() {
        mTvName.setText(mProduct.getName());
        mTvIntegral.setText(String.valueOf(mProduct.getIntegral()));
        mTvExpiryDate.setText(mProduct.getExpiryDate());
        if (mProduct.isTangible()) {
            mTvTxtRemaining.setVisibility(View.VISIBLE);
            mTvRemaining.setText(String.valueOf(mProduct.getQuantity()));
        }
        mTvDescription.setText(mProduct.getDescription());
        mTvExchangedCount.setText(String.format(getString(R.string.txt_member_count), mProduct.getExchanged()));

        mPagerCount = mProduct.getImages().length;
        if (mPagerCount != 0) {
            String[] imageUrls = mProduct.getImages();
            for (int index = 0; index < mPagerCount; index++) {
                final ImageView imageView = new ImageView(getActivity());
                ImageLoader.getInstance().loadImage(imageUrls[index], getDefaultDisplayOptions(), new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        imageView.setImageResource(R.drawable.test_b);
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                    }
                });
                mImageList.add(imageView);
                View dotView = new View(getActivity());
                dotView.setEnabled(index == 0);
                LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(20, 20);
                dotView.setBackgroundResource(R.drawable.dot_style_selector);
                dotParams.leftMargin = 5;
                dotParams.rightMargin = 5;
                mLlDotLayout.addView(dotView, dotParams);
            }
            mVpShowPic.setAdapter(mAutoScrollPagerAdapter);
            mCurrentItem = mPagerCount * 100;
            mVpShowPic.setCurrentItem(mCurrentItem);
            mVpShowPic.addOnPageChangeListener(this);
            mVpShowPic.setOnTouchListener(onTouchListener);
            if (!mIsTouch) {
                startAutoScroll();
            }
        } else {
            mVpShowPic.setBackgroundResource(R.drawable.test_b);
        }

        if (mProduct.getIntegral() > TKGZApplication.getInstance().getUserProfile().getIntegralForYear()) {
            mBtnExchange.setBackgroundResource(R.drawable.btn_gray_style);
        } else {
            mBtnExchange.setBackgroundResource(R.drawable.btn_green_style);
            mBtnExchange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCancelButtonDialog(R.string.dialog_title_reminder, R.string.dialog_content_ensure_exchange,
                            mProduct.getName(), "", R.string.dialog_ok_button, R.string.dialog_cancel_button, new TKGZCustomDialog.OnDialogActionCallback() {
                                @Override
                                public void onConfirmSelected() {
                                    onCallExchange(mProduct.getId());
                                    getProductDetail();
                                }
                            }, null);
                }
            });
        }
    }

    private void startAutoScroll() {
        mScheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        mScheduledExecutorService.scheduleAtFixedRate(new AutoScrollTask(), 3, 4, TimeUnit.SECONDS);
    }

    private void stopAutoScroll() {
        if (mScheduledExecutorService != null) {
            mScheduledExecutorService.shutdown();
        }
    }

    public void onCallExchange(String productId) {
        initLoader(ExchangeProductProvider.getExchangeProductProvider(this, TKGZApplication.getInstance().getUserProfile(), productId, new ExchangeProductApiCallBack.OnExchangeProductApiListener() {
            @Override
            public void onExchangeProduct(final boolean result, final int integral) {
                final Activity activity = getActivity();
                if (activity != null) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (result) {
                                showDialog(R.string.dialog_title_reminder, R.string.dialog_content_exchange_success);
                                TKGZApplication.getInstance().getUserProfile().setIntegralForYear(integral);
                                mOnRefreshUIListener.callRefreshIntegral();
                            } else {
                                showDialog(R.string.dialog_title_reminder, R.string.dialog_content_exchange_failure);
                            }
                        }
                    });
                }

            }
        }));
    }

    private class AutoScrollTask implements Runnable {
        @Override
        public void run() {
            synchronized (mVpShowPic) {
                mWeakHandler.post(mWeakRunnable);
            }
        }
    }

    @Override
    public String getTransactionTag() {
        return "my_goods_detail_fragment";
    }

    @Override
    public boolean isHideActionBar() {
        return false;
    }

    @Override
    public String getActionBarTitle() {
        return getString(R.string.txt_my_goods_detail);
    }


    @Override
    protected void onXODestroyView() {
        stopAutoScroll();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentItem = position;
        position %= mPagerCount;
        for (int i = 0; i < mPagerCount; i++) {
            mLlDotLayout.getChildAt(i).setEnabled(false);
        }
        mLlDotLayout.getChildAt(position).setEnabled(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    PagerAdapter mAutoScrollPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int currentPosition = position % mPagerCount;
            ImageView imageView = mImageList.get(currentPosition);

            ViewParent parent = imageView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(imageView);
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    };
}
