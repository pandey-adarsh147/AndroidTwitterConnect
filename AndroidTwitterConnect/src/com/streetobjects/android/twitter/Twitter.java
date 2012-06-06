package com.streetobjects.android.twitter;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

public class Twitter {
	final String TAG = getClass().getName();
	
	private String mApiKey;
	private String mSecretKey;
	
	private OAuthConsumer consumer; 
    private OAuthProvider provider;
    
    private Activity mContext;
    private TwitterAuthListener mAuthListener;
	
	public Twitter(Activity context, String apiKey, String secretKey) {
		
		if(apiKey == null || secretKey == null) {
			throw new IllegalArgumentException("API key and Secret key never be null");
		}
		
		mApiKey = apiKey;
		mSecretKey = secretKey;
		mContext = context;
		
		try {
    		this.consumer = new CommonsHttpOAuthConsumer(mApiKey, mSecretKey);
    	    this.provider = new CommonsHttpOAuthProvider(IConstants.REQUEST_URL, IConstants.ACCESS_URL, IConstants.AUTHORIZE_URL);
    	} catch (Exception e) {
    		Log.e(TAG, "Error creating consumer / provider",e);
		}

	}
	
	public void authenticate() {
		Log.i(TAG, "Starting task to retrieve request token.");
		new OAuthRequestTokenTask(mContext,consumer,provider, tokenListener).execute();
	}
	
	private TokenListener tokenListener = new TokenListener() {
		
		@Override
		public void onReceive(Uri uri) {
			if (uri != null && uri.getScheme().equals(IConstants.OAUTH_CALLBACK_SCHEME)) {
				Log.i(TAG, "Callback received : " + uri);
				Log.i(TAG, "Retrieving Access Token");
				new RetrieveAccessTokenTask(mContext, consumer, provider).execute(uri);
			}
		}
	};
	
	public class RetrieveAccessTokenTask extends AsyncTask<Uri, Void, Void> {

		private Context	context;
		private OAuthProvider provider;
		private OAuthConsumer consumer;
		
		public RetrieveAccessTokenTask(Context context, OAuthConsumer consumer,OAuthProvider provider) {
			this.context = context;
			this.consumer = consumer;
			this.provider = provider;
		}


		/**
		 * Retrieve the oauth_verifier, and store the oauth and oauth_token_secret 
		 * for future API calls.
		 */
		@Override
		protected Void doInBackground(Uri...params) {
			final Uri uri = params[0];
			final String oauth_verifier = uri.getQueryParameter(OAuth.OAUTH_VERIFIER);

			try {
				provider.retrieveAccessToken(consumer, oauth_verifier);

				OAuthDTO authDTO = new OAuthDTO();
				authDTO.setToken(consumer.getToken());
				authDTO.setTokenSecret(consumer.getTokenSecret());
				
				SessionStore.saveTwitterOAuth(authDTO, mContext);
				
				String token = authDTO.getToken();
				String secret = authDTO.getTokenSecret();
				
				consumer.setTokenWithSecret(token, secret);
				
				Intent data = new Intent();
//				setResult(IConstants.RESULT_OK, data);
//				finish();
				
				if(mAuthListener != null) {
					mAuthListener.onAuthenticated();
				}
				
				Log.i(TAG, "OAuth - Access Token Retrieved");
				
			} catch (Exception e) {
				Log.e(TAG, "OAuth - Access Token Retrieval Error", e);
			}

			return null;
		}
	}	

	public void setAuthListener(TwitterAuthListener authListener) {
		mAuthListener = authListener;
	}
	
	interface TokenListener {
		void onReceive(Uri uri);
	}
	
	public interface TwitterAuthListener {
		void onAuthenticated();
	}
	
	public boolean isAuthenticated() {
		return TwitterUtils.isAuthenticated(mContext, mApiKey, mSecretKey);
	}
	
	public void tweet(String mssg) throws Exception {
		TwitterUtils.sendTweet(mContext, mApiKey, mSecretKey, mssg);
	}
}
