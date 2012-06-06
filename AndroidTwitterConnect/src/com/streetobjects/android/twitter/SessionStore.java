/*
 * Copyright 2010 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.streetobjects.android.twitter;

import oauth.signpost.OAuth;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionStore {
	private static final String KEY_TWITTER = "twitter-key-";
    
    public static boolean saveTwitterOAuth(OAuthDTO consumer, Activity context) {
    	String preferenceKey = KEY_TWITTER;
    	Editor editor =
            context.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE).edit();
    	editor.putString(OAuth.OAUTH_TOKEN, consumer.getToken());
    	editor.putString(OAuth.OAUTH_TOKEN_SECRET, consumer.getTokenSecret());
		
        
        return editor.commit();
    }
    
    public static void clearTwitterCredentials(Activity context) {
    	String preferenceKey = KEY_TWITTER;
    	Editor editor =
                context.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE).edit();
    	editor.remove(OAuth.OAUTH_TOKEN);
		editor.remove(OAuth.OAUTH_TOKEN_SECRET);
		editor.commit();
	}
    
    public static boolean restoreTwitterOAuth(OAuthDTO authDTO, Activity context) {
    	
    	String preferenceKey = KEY_TWITTER;
    	SharedPreferences preferences =
            context.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
    	
    	authDTO.setToken(preferences.getString(OAuth.OAUTH_TOKEN, ""));
    	authDTO.setTokenSecret(preferences.getString(OAuth.OAUTH_TOKEN_SECRET, ""));
    	return true;
    }
}
