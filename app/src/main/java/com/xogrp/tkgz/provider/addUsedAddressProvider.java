package com.xogrp.tkgz.provider;

import com.xogrp.tkgz.TKGZConfiguration;
import com.xogrp.tkgz.model.UserProfile;
import com.xogrp.tkgz.spi.AddUsedAddressApiCallback;
import com.xogrp.xoapp.spi.RESTfulApiClient;

/**
 * Created by hliao on 5/5/2016.
 */
public abstract class addUsedAddressProvider extends AbstractTKGZRESTLoader {
    private static final String URL_ADDUSEDADDRESS="http://%s/v1/frequent_addresses";
    public addUsedAddressProvider(RESTfulApiClient.RESTfulApiCallback resTfulApiCallback,AbstractTKGZRESTLoader.OnRESTLoaderListener onRESTLoaderListener){
        super(resTfulApiCallback,onRESTLoaderListener);
    }


    public  static addUsedAddressProvider getAddUsedAddressProvider(UserProfile userProfile,AddUsedAddressApiCallback.OnAddAddressApiListener onaddAddressApiListener,OnRESTLoaderListener onRESTLoaderListener,String address) {
        return new addUsedAddressProvider(new AddUsedAddressApiCallback(userProfile, onaddAddressApiListener,address), onRESTLoaderListener) {

            @Override
            protected String getUrl() {
                return String.format(URL_ADDUSEDADDRESS, TKGZConfiguration.getInstance().getWebServiceHost());
            }

            @Override
            protected RESTfulApiClient getRESTfulApiClient() {
                return RESTfulApiClient.getRESTfulPostClient(TKGZConfiguration.getInstance());
            }
        };
}
        }
