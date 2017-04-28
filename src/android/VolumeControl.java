/*
 * Cordova/Phonegap VolumeControl Plugin for Android
 * Cordova >= 3.0.0
 * Author: Manuel Simpson
 * Email: manusimpson[at]gmail[dot]com
 * Date: 12/28/2012
 * 
 * At 04/28/2017 working in app compiled with Cordova 6.5.0  
 */

package com.develcode.plugins.volumeControl;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.media.*;

public class VolumeControl extends CordovaPlugin {

	public static final String SET = "setVolume";
	public static final String GET = "getVolume";
	public static final String MUT = "toggleMuteVolume";
	private static final String TAG = "VolumeControl";

	private Context context;
	private AudioManager manager;

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		boolean actionState = true;
		context = cordova.getActivity().getApplicationContext();
		manager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		if (SET.equals(action)) {
			try {
				//Get the volume value to set
				int volume = getVolumeToSet(args.getInt(0));
				boolean play_sound;

				if(args.length() > 1 && !args.isNull(1)) {
					play_sound = args.getBoolean(1);
				} else {
					play_sound = true;
				}

				//Set the volume
				manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, (play_sound ? AudioManager.FLAG_PLAY_SOUND : 0));
				callbackContext.success();
			} catch (Exception e) {
				LOG.d(TAG, "Error setting volume " + e);
				actionState = false;
			}
		} else if(GET.equals(action)) {
			try {
				//Get current system volume
				int currVol = getCurrentVolume();
				String strVol= String.valueOf(currVol);
				callbackContext.success(strVol);
			} catch (Exception e) {
				LOG.d(TAG, "Error setting volume " + e);
				actionState = false;
			}
		} else if(MUT.equals(action)){
			try{
				//Mute or Unmute volume
				int volume = getCurrentVolume();
				if(volume > 1){
					// Mute: Set volume to 0
					volume = 0;
				} else {
					// Unmute: Set volume to previous value
					volume = getVolumeToSet(args.getInt(0));
				}
				manager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_PLAY_SOUND);
				callbackContext.success(volume);

			} catch (Exception e) {
				LOG.d(TAG, "Error setting mute/unmute " + e);
				actionState = false;
			}
		} else {
			actionState = false;
		}
		return actionState;
	}

	private int getVolumeToSet(int percent) {
		try {
			int volLevel;
			int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			volLevel = Math.round((percent * maxVolume) / 100);

			return volLevel;
		} catch (Exception e){
			LOG.d(TAG, "Error getting VolumeToSet: " + e);
			return 1;
		}
	}

	private int getCurrentVolume() {
		try {
			int volLevel;
			int maxVolume = manager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			int currSystemVol = manager.getStreamVolume(AudioManager.STREAM_MUSIC);
			volLevel = Math.round((currSystemVol * 100) / maxVolume);

			return volLevel;
		} catch (Exception e) {
			LOG.d(TAG, "Error getting CurrentVolume: " + e);
			return 1;
		}
	}
}
