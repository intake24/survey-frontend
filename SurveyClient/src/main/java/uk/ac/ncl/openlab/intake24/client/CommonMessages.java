/*
This file is part of Intake24.

Copyright 2015, 2016, 2017 Newcastle University.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

This file is based on Intake24 v1.0.

© Crown copyright, 2012, 2013, 2014

Licensed under the Open Government Licence 3.0: 

http://www.nationalarchives.gov.uk/doc/open-government-licence/
*/

package uk.ac.ncl.openlab.intake24.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;

public interface CommonMessages extends Messages {

  CommonMessages INSTANCE = GWT.create(CommonMessages.class);

  public static class Util {
    private static CommonMessages instance = null;

    public static CommonMessages getInstance() {
      if (instance == null)
        instance = GWT.create(CommonMessages.class);
      return instance;
    }
  }

  public String backLink();

  public String helpButtonLabel();

  public String serverErrorTitle();

  public String serverErrorText(String supportEmail);

  public String forbiddenErrorTitle();

  public String forbiddenErrorText(String supportEmail);

  public String surveyPendingTitle();

  public String surveyPendingText(String supportEmail);

  public String surveyFinishedTitle();

  public String surveyFinishedText(String supportEmail);

  public String surveySuspendedTitle();

  public String surveySuspendedReason(String reason);

  public String surveySuspendedText(String supportEmail);

  public String loginForm_welcome();

  public String loginForm_userNameLabel();

  public String loginForm_logInToContinue();

  public String loginForm_logInSeparator();

  public String loginForm_passwordLabel();

  public String loginForm_sessionExpired();

  public String loginForm_passwordNotRecognised(String supportEmail);

  public String loginForm_serviceException(String supportEmail);

  public String loginForm_logInButtonLabel();

  public String loginForm_watchVideo();

  public String logoutPage_title();

  public String logoutPage_close();

  public String logoutPage_closeWithHelp(String supportEmail);

  String maximumDailySubmissionsReachedTitle();

  String maximumDailySubmissionsReachedText();

  String maximumTotalSubmissionsReachedTitle();

  String maximumTotalSubmissionsReachedText();

  public String authToken_invalid(String supportEmail);

  public String callbackRequestForm_promptText();

  public String callbackRequestForm_nameLabel();

  public String callbackRequestForm_phoneNumberLabel();

  public String callbackRequestForm_requestCallbackButtonLabel();

  public String callbackRequestForm_tooSoon();

  public String callbackRequestForm_success();

  public String callbackRequestForm_hideButtonLabel();

  public String callbackRequestForm_fieldsEmpty();

  public String callbackRequestForm_disabledForDemo(String supportEmail);

  public String callbackRequestForm_watchWalkthrough();

  public String genUserWelcome();

  public String genUserForbidden(String supportEmail);

  public String genUserSaveInfo();

  public String genUserSurveyLink();

  public String genUserOneSitting();

  public String genUserContinue();

  public String walkthroughYouTubeUrl();

  public String walkthroughYouTubeEmbedUrl();

  public String privacyLinkLabel();

  public String tocLinkLabel();

}
