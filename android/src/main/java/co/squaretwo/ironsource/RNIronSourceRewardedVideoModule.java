package co.squaretwo.ironsource;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.support.annotation.Nullable;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.supersonic.mediationsdk.logger.SupersonicError;
import com.supersonic.mediationsdk.model.Placement;
import com.supersonic.mediationsdk.sdk.RewardedVideoListener;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.supersonic.mediationsdk.sdk.Supersonic;
import com.supersonic.mediationsdk.sdk.SupersonicFactory;


public class RNIronSourceRewardedVideoModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RewardedVideo";
    private Intent mRewardedVideoIntent;
    private final ReactApplicationContext reactContext;
    private Supersonic mMediationAgent;

    /*
        RewardedVideoListener will emit the following React Native callbacks
            rewardedVideoInitialized
            rewardedVideoInitializationFailed
            rewardedVideoAvailable
            rewardedVideoUnavailable
            rewardedVideoDidStart
            rewardedVideoClosedByUser
            rewardedVideoClosedByError
            rewardedVideoAdStarted
            rewardedVideoAdEnded
            rewardedVideoAdRewarded
    */
    RewardedVideoListener mRewardedVideoListener = new RewardedVideoListener() {
        //Invoked when initialization of RewardedVideo has finished successfully.
        @Override
        public void onRewardedVideoInitSuccess() {
            Log.d(TAG, "onRewardedVideoInitSuccess() called!");
            sendEvent("rewardedVideoInitialized", null);
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
                sendEvent("rewardedVideoInitializationFailed", null);
            }
        }

        //Invoked when RewardedVideo call to show a rewarded video has failed
        //SupersonicError contains the reason for the failure.
        @Override
        public void onRewardedVideoShowFail(SupersonicError se) {
            Log.d(TAG, "onRewardedVideoShowFail() called!");
            sendEvent("rewardedVideoClosedByError", null);

        }

        //Invoked when the RewardedVideo ad view has opened.
        //Your Activity will lose focus. Please avoid performing heavy
        //tasks till the video ad will be closed.
        @Override
        public void onRewardedVideoAdOpened() {
            Log.d(TAG, "onRewardedVideoAdOpened() called!");
            sendEvent("rewardedVideoDidStart", null);
        }

        //Invoked when the RewardedVideo ad view is about to be closed.
        //Your activity will now regain its focus.
        @Override
        public void onRewardedVideoAdClosed() {
            Log.d(TAG, "onRewardedVideoAdClosed() called!");
            sendEvent("rewardedVideoClosedByUser", null);
        }

        //Invoked when there is a change in the ad availability status.
        //@param - available - value will change to true when rewarded videos are available.
        //You can then show the video by calling showRewardedVideo().
        //Value will change to false when no videos are available.
        @Override
        public void onVideoAvailabilityChanged(boolean available) {
            //Change the in-app 'Traffic Driver' state according to availability.
            Log.d(TAG, "onVideoAvailabilityChanged() called!");
            if (available) {
              sendEvent("rewardedVideoAvailable", null);
            } else {
              sendEvent("rewardedVideoUnavailable", null);
            }
        }

        //Invoked when the video ad starts playing.
        @Override
        public void onVideoStart() {
            Log.d(TAG, "onVideoStart() called!");
            sendEvent("rewardedVideoAdStarted", null);
        }

        //Invoked when the video ad finishes playing.
        @Override
        public void onVideoEnd() {
            Log.d(TAG, "onVideoEnd() called!");
            sendEvent("rewardedVideoAdEnded", null);
        }

        //Invoked when the user completed the video and should be rewarded.
        //If using server-to-server callbacks you may ignore this events and wait for
        //the callback from the Supersonic server.
        //@param - placement - the Placement the user completed a video from.
        @Override
        public void onRewardedVideoAdRewarded(Placement placement) {
            //TODO - here you can reward the user according to the given amount.
            String rewardName = placement.getRewardName();
            int rewardAmount = placement.getRewardAmount();
        }

    };

    public RNIronSourceRewardedVideoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNIronSourceRewardedVideo";
    }

    @ReactMethod
    public void initializeRewardedVideo(String appKey, String userId) {
        mMediationAgent = SupersonicFactory.getInstance();
        mMediationAgent.setRewardedVideoListener(mRewardedVideoListener);
        mMediationAgent.initRewardedVideo(getCurrentActivity(), appKey, userId);
    }

    @ReactMethod
    public void showRewardedVideo() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "showRewardedVideo() called!!");
                boolean available = mMediationAgent.isRewardedVideoAvailable();
                if (available) {
                    mMediationAgent.showRewardedVideo();
                }
            }
        });
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
