package com.streetobjects.android;

import com.streetobjects.android.twitter.IConstants;
import com.streetobjects.android.twitter.Twitter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TwitterConnect extends Activity {
    /** Called when the activity is first created. */
	private Twitter mTwitter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mTwitter = new Twitter(this, IConstants.TWITTER_API_KEY, IConstants.TWITTER_SECRET_KEY);
        Button button = (Button) findViewById(R.id.connect_twitter);
        button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTwitter.setAuthListener(new Twitter.TwitterAuthListener() {
					
					@Override
					public void onAuthenticated() {
						try {
							mTwitter.tweet("ut patang");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
				mTwitter.authenticate();
			}
		});
    }
}