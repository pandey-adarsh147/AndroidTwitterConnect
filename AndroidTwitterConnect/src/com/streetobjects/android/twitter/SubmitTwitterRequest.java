package com.streetobjects.android.twitter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SubmitTwitterRequest extends Activity {
	private static final String TAG = "Twitter - Submission";
	private WebView mWebView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
//		setContentView(R.layout.twitter_screen);
		
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		
//		mWebView = (WebView) findViewById(R.id.web_engine);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebChromeClient(new WebChromeClient() {
			
			public void onProgressChanged(final WebView view, final int progress) {
				/*if(progress == 100) {
					mProgressIndicator.setVisibility(View.GONE);
				} else {
					mProgressIndicator.setVisibility(View.VISIBLE);
				}
				mProgressIndicator.setProgress(progress);*/
			}

			
		});
		
		mWebView.setWebViewClient(new ClbSignupWebViewClient());
		
		mWebView.loadUrl(url);
	}
	
	private class ClbSignupWebViewClient extends WebViewClient {
		@Override
		public void onReceivedSslError(WebView view,
				SslErrorHandler handler, SslError error) {
			
			handler.proceed();
		}
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Log.d(TAG, "server url: "+url);
			
			if(url.startsWith(IConstants.OAUTH_CALLBACK_URL)) {
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//				intent.setData(data);
				setResult(IConstants.RESULT_OK, intent);
				finish();
			}

			return false;
		}
		
		@Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Log.d(TAG, "Page loading URL: " + url);
            
            super.onPageStarted(view, url, favicon);
        }
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			Log.d(TAG, "Page finish URL: "+url);
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent data = new Intent();
		setResult(IConstants.RESULT_CANCEL, data);
		finish();
	}
}
