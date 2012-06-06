package com.streetobjects.android.twitter;

public interface IConstants {

	String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow-chargepoint";
	String	OAUTH_CALLBACK_HOST		= "callback";
	String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

	String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
	String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
	String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";
	
	String TWITTER_API_KEY = "--API KEY HERE--";
	String TWITTER_SECRET_KEY = "--API SECRET HERE--";
	
	
	
	int RESULT_OK = 1;
	int RESULT_CANCEL = 2;
}
