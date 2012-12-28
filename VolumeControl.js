/*
 * Phonegap VolumeControl Plugin for Android
 * Cordova 2.2.0
 * Author: Manuel Simpson
 * Date: 12/28/2012
 */
function VolumeControl(){}

var VolumeControl = {
  setVolume: function(vol, successCallback, failureCallback){
		return cordova.exec(
			successCallback,
			failureCallback,
			'VolumeControl',
			'setVolume',
			[vol]
		);
	},
	getVolume: function(successCallback,failureCallback){
		return cordova.exec(
			successCallback,
			failureCallback,
			'VolumeControl',
			'getVolume',
			[]);
	}
};
