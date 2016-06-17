package com.xogrp.tkgz.listener;

import com.xogrp.tkgz.fragment.AbstractTKGZFragment;
import com.xogrp.tkgz.fragment.EventDetailFragment;
import com.xogrp.tkgz.fragment.GetActivityTypeFragment;
import com.xogrp.tkgz.fragment.GetCommonAddressFragment;
import com.xogrp.tkgz.fragment.GetInitiatorFragment;
import com.xogrp.tkgz.fragment.MyAccountFragment;
import com.xogrp.tkgz.model.ActivityType;
import com.xogrp.tkgz.model.MessageForUser;
import com.xogrp.tkgz.model.MessageProfile;
import com.xogrp.tkgz.model.UserProfile;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by dgao on 7/28/2015.
 */
public interface OnScreenNavigationListener {

    void goBackFromCurrentPage();

    void logOutFromCurrentPage();

    void changeLanguageFromCurrentPage();

    void navigateToLoginPage();

    void navigateToAdminFirPage();

    void navigateToIntegralRankPage();

    void navigateToMyIntegralPage();

    void navigateToUserHomePage();

    void navigateToMasterHomePage();

    void navigateToAddAddressPage();

    void navigateToAddActTypePage();

    void navigateToActivityManagePage();

    void navigateToActivityTypePage();

    void navigateToAddressListPage();

    void returnToAddressListPage();

    void navigateToTeamIntegralPage();

    void navigateToTeamInquireResultPage();

    void navigateToSearchIntegralPage();

    void navigateToTeamsPersonalSearchResultsPage();

    void navigateToIntegralExchangePage();

    void navigateToChangePasswordPage();

    void navigateToFeedbackPage();

    void navigateToAboutTheKnotPage();

    void navigateToPersonnelManagePage();

    void navigateToSectionPersonnelPage(String actionBarName);

    void navigateToPersonnelInformationPage(UserProfile user);

    void navigateToNewsListPage();

    void navigateToNewsDetailsPage(MessageForUser message);

    void navigateToEventDetailsPage(EventDetailFragment.OnUpdateEventStatusListener onUpdateEventStatusListener, String eventId, boolean has_menu);

    void navigateToMyEventsPage();

    void navigateToMyProductsPage();

    void navigateToActivityType();

    void navigateToMessageManagerPage();

    void navigateToInquireManagerPage();

    void navigateToMessageEditPage(AbstractTKGZFragment fragment);

    void navigateToPersonnelInformationEditPage(AbstractTKGZFragment fragment);

    void navigateToShowProductDetailPage(String id);

    void navigateToEventTypePage();

    void navigateToCreateNewEventPage();

    void navigateToActivityTrainingPage(String title, boolean hasMenu);

    void navigateToMessageDetailsPage(MessageProfile message, int position);

    void navigateToSearchEmployeePage();

    void navigateToSearchIntegralByAdminPage();

    void navigateToSearchMemberIntegralResultPage();

    void navigateToSearchTeamMemberIntegralPage();

    void navigateToPhotoWallPage();

    void navigateToSelfIntroductionPage(UserProfile employeeInformation);

    void navigateToMyAccountPage(UserProfile userProfile, MyAccountFragment.OnRefreshMyAvatarListener onRefreshMyAvatarListener);

    void navigateToShowEventsPage();

    void navigateToMonthStarsPage();

    void navigateToNewComerPage();

    void navigateToRetrievePasswordPage();

    void navigateToSplashPage();

    void navigateToEditActivityTypePage(List<ActivityType> activityTypes);

    void navigateToCommonAddressPage();

    void navigateToAdminPage();
    void navigateToCreateActivityPage2();
    void navigateToGetCommonAddressPage(GetCommonAddressFragment.GetAddressListener getAddressListener);
    void navigateToGetActivityTypePage(GetActivityTypeFragment.GetActivityTypeListener getActivityTypeListener);
    void navigateToGetInitiator(GetInitiatorFragment.InitiatorListener initiatorListener);


    void navigateToCreateActivityPageTwo(JSONObject jsonObject);

    void navigateToAddressSearchPage();
}
