
package co.squaretwo.ironsource;

import android.util.Log;
import android.os.Handler;
import android.os.Looper;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import com.supersonic.mediationsdk.sdk.RewardedVideoListener;


public class RNIronSourceRewardedVideoModule extends ReactContextBaseJavaModule {
  private static final String TAG = "RNIronSource";
  private RewardedVideoListener mRewardedVideoListener;

  private final ReactApplicationContext reactContext;

  public RNIronSourceRewardedVideoModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.mRewardedVideoListener = new RewardedVideoListener();
  }

  @Override
  public String getName() {
    return "RNIronSourceRewardedVideo";
  }

  //Invoked when initialization of RewardedVideo has finished successfully.
  @Override
  public void onRewardedVideoInitSuccess() {
    Log.d(TAG, "onRewardedVideoInitSuccess() called!");
  }

  //Invoked when RewardedVideo initialization process has failed.
  //SupersonicError contains the reason for the failure.
  @Override
  public void onRewardedVideoInitFail(SupersonicError se) {

   //Retrieve details from a SupersonicError object.
    int errorCode =  se.getErrorCode();
    String errorMessage = se.getErrorMessage();
    if (errorCode == SupersonicError.ERROR_CODE_GENERIC){
       //Write a Handler for specific error's.
       Log.d(TAG, "onRewardedVideoInitFail() called!");
    }
  }

  //Invoked when RewardedVideo call to show a rewarded video has failed
  //SupersonicError contains the reason for the failure.
  @Override
	public void onRewardedVideoShowFail(SupersonicError se) {
    Log.d(TAG, "onRewardedVideoShowFail() called!");
	}
  //Invoked when the RewardedVideo ad view has opened.
  //Your Activity will lose focus. Please avoid performing heavy
  //tasks till the video ad will be closed.
  @Override
  public void onRewardedVideoAdOpened() {
    Log.d(TAG, "onRewardedVideoAdOpened() called!");
  }
  //Invoked when the RewardedVideo ad view is about to be closed.
  //Your activity will now regain its focus.
  @Override
  public void onRewardedVideoAdClosed() {
    Log.d(TAG, "onRewardedVideoAdClosed() called!");
  }
  //Invoked when there is a change in the ad availability status.
  //@param - available - value will change to true when rewarded videos are available.
  //You can then show the video by calling showRewardedVideo().
  //Value will change to false when no videos are available.
  @Override
  public void onVideoAvailabilityChanged(boolean available) {
    //Change the in-app 'Traffic Driver' state according to availability.
    Log.d(TAG, "onVideoAvailabilityChanged() called!");
  }
  //Invoked when the video ad starts playing.
  @Override
  public void onVideoStart() {
    Log.d(TAG, "onVideoStart() called!");

  }
  //Invoked when the video ad finishes playing.
  @Override
  public void onVideoEnd() {
    Log.d(TAG, "onVideoEnd() called!");
  }

  @ReactMethod
  public void showAd() {
      new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override
          public void run() {
            Log.d(TAG, "showAd() called!");
          }
      });
  }
}
