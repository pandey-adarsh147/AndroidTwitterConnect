package com.streetobjects.android.twitter;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;

import com.streetobjects.android.twitter.Twitter.TokenListener;


/**
 * An asynchronous task that communicates with Twitter to 
 * retrieve a request token.
 * (OAuthGetRequestToken)
 * 
 * After receiving the request token from Twitter, 
 * pop a browser to the user to authorize the Request Token.
 * (OAuthAuthorizeToken)
 * 
 */
public class OAuthRequestTokenTask extends AsyncTask<Void, Void, String> implements OnCancelListener{

	final String TAG = getClass().getName();
	private Activity context;
	private OAuthProvider provider;
	private OAuthConsumer consumer;
	private TokenListener tokenListener;

	private ProgressDialog mSpinner;
	
	/**
	 * 
	 * We pass the OAuth consumer and provider.
	 * 
	 * @param 	context
	 * 			Required to be able to start the intent to launch the browser.
	 * @param 	provider
	 * 			The OAuthProvider object
	 * @param 	consumer
	 * 			The OAuthConsumer object
	 * @param tokenListener 
	 */
	public OAuthRequestTokenTask(Activity context,OAuthConsumer consumer,OAuthProvider provider, TokenListener tokenListener) {
		this.context = context;
		this.consumer = consumer;
		this.provider = provider;
		this.tokenListener = tokenListener;
	}

	/**
	 * 
	 * Retrieve the OAuth Request Token and present a browser to the user to authorize the token.
	 * 
	 */
	@Override
	protected String doInBackground(Void... params) {
		String url = null ;
		try {
			Log.i(TAG, "Retrieving request token from Google servers");
			url = provider.retrieveRequestToken(consumer, IConstants.OAUTH_CALLBACK_URL);
			Log.i(TAG, "Popping a browser with the authorize URL : " + url);
			/*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
			context.startActivity(intent);*/
			
			/*Intent intent = new Intent(this.context, SubmitTwitterRequest.class);
			intent.putExtra("url", url);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
			
			context.startActivityForResult(intent, 1);
			*/
		} catch (Exception e) {
			Log.e(TAG, "Error during OAUth retrieve request token", e);
		}

		return url;
	}
	
	@Override
	protected void onPreExecute() {
		mSpinner = new ProgressDialog(context);
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");
        mSpinner.setCancelable(true);
        mSpinner.setOnCancelListener(this);
        
        mSpinner.show();
	}
	
	@Override
	protected void onPostExecute(String result) {
		mSpinner.dismiss();
		
		if(result != null) {
			mTwitterDialog = new TwitterDialog(context, result, tokenListener);
			mTwitterDialog.show();
		}
	}

	private TwitterDialog mTwitterDialog;
	
	@Override
	public void onCancel(DialogInterface dialog) {
		
	}

}