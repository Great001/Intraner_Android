package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.RankingListApiCallBack;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by ayu on 12/1/2015 0001.
 */
public abstract class RankingListProvider extends AbstractTKGZRESTLoader {

    private static final String URL_GET_RANKING_LIST = "http://%s/v1/integrals/?rankType=%s&type=rank";

    protected RankingListProvider(RESTfulApiClient.RESTfulApiCallback restfulApiCallback, OnRESTLoaderListener onRESTLoaderListener) {
        super(restfulApiCallback, onRESTLoaderListener);
    }

    public static RankingListProvider getRankingListProvider(OnRESTLoaderListener onRESTLoaderListener, UserProfile userProfile, RankingListApiCallBack.OnRankingListApiListener onRankingListApiListener, final String eventType) {

        return new RankingListProvider(new RankingListApiCallBack(userProfile, onRankingListApiListener), onRESTLoaderListener) {

            @Override
            protected String getUrl() {
                return String.format(URL_GET_RANKING_LIST, TKGZConfiguration.getInstance().getWebServiceHost(), eventType);
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulGetClient(TKGZConfiguration.getInstance());
            }
        };
    }
}
