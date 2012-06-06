package com.streetobjects.android.twitter;

public interface IConstants {

	String	OAUTH_CALLBACK_SCHEME	= "x-oauthflow-chargepoint";
	String	OAUTH_CALLBACK_HOST		= "callback";
	String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;

	String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
	String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
	String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";
	
	String TWITTER_API_KEY = "gorpmqzplPfVqfBfzBYKHA";
	String TWITTER_SECRET_KEY = "vq3Ej8P1UyDvB1ovG6X96GnOiVrExNv4XKTfUblwUWU";
	
//	String REQUEST_URL = "https://api.linkedin.com/uas/oauth/requestToken";
//	String ACCESS_URL = "https://api.linkedin.com/uas/oauth/accessToken";
//	String AUTHORIZE_URL = "https://www.linkedin.com/uas/oauth/authorize";
//	
//	String TWITTER_API_KEY = "6v56e67jrlsj";
//	String TWITTER_SECRET_KEY = "7Xl5ceyg8SUyqN9U";
	
	
	int RESULT_OK = 1;
	int RESULT_CANCEL = 2;
}
