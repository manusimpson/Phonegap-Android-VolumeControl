/*
 * Cordova/Phonegap VolumeControl Plugin for Android
 * Cordova >= 3.0.0
 * Email: manusimpson[at]gmail[dot]com
 * Author: Manuel Simpson
 * Date: 12/28/2012
 * 
 * At 04/28/2017 working in app compiled with Cordova 6.5.0
 */

var exec = require('cordova/exec');

var VolumeControl = {
  setVolume: function(vol, successCallback, failureCallback, playSound){
		return exec(
			successCallback,
			failureCallback,
			'VolumeControl',
			'setVolume',
			[vol, playSound]
		);
	},
	getVolume: function(successCallback,failureCallback){
		return exec(
			successCallback,
			failureCallback,
			'VolumeControl',
			'getVolume',
			[]);
	},
	toggleMuteVolume: function(vol, successCallback, failureCallback){
		return exec(
			successCallback,
			failureCallback,
			'VolumeControl',
			'toggleMuteVolume',
			[vol]);
	}
};

module.exports = VolumeControl;
