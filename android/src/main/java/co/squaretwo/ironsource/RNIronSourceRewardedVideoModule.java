package co.squaretwo.ironsource;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.support.annotation.Nullable;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;

public class RNIronSourceRewardedVideoModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNIronSourceRewardedVideo";
    private final ReactApplicationContext reactContext;

    public RNIronSourceRewardedVideoModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void initializeRewardedVideo(Promise promise) {
        IronSource.setRewardedVideoListener(new RewardedVideoListener() {
            @Override
            public void onRewardedVideoAdOpened() {
                Log.d(TAG, "onRewardedVideoAdOpened() called!");
                // @Deprecated "ironSourceRewardedVideoDidStart"
                sendEvent("ironSourceRewardedVideoDidStart", null);
                sendEvent("ironSourceRewardedVideoDidOpen", null);
            }
            @Override
            public void onRewardedVideoAdClosed() {
                Log.d(TAG, "onRewardedVideoAdClosed() called!");
                sendEvent("ironSourceRewardedVideoClosedByUser", null);
            }
            @Override
            public void onRewardedVideoAvailabilityChanged(boolean available) {
                Log.d(TAG, "onRewardedVideoAvailabilityChanged() called!");
                if (available) {
                    Log.d(TAG, "ironSourceRewardedVideoAvailable!" );
                    sendEvent("ironSourceRewardedVideoAvailable", null);
                } else {
                    Log.d(TAG, "ironSourceRewardedVideoUnavailable!" );
                    sendEvent("ironSourceRewardedVideoUnavailable", null);
                }
            }
            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {
                //TODO - here you can reward the user according to the given amount.
                String rewardName = placement.getRewardName();
                int rewardAmount = placement.getRewardAmount();
                Log.d(TAG, "onRewardedVideoAdRewarded() called! " + rewardName + " " + rewardAmount);
                sendEvent("ironSourceRewardedVideoAdRewarded", null);
            }
            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError se) {
                Log.d(TAG, "onRewardedVideoAdShowFailed() called!");
                sendEvent("ironSourceRewardedVideoClosedByError", null);
            }

            @Override
            public void onRewardedVideoAdClicked(Placement placement) {
                Log.d(TAG, "onRewardedVideoAdClicked() called!");
            }
            /*
             * Note: the events below are not available for
             * all supported Rewarded Video Ad Networks.
             * Check which events are available per Ad Network
             * you choose to include in your build.
             * We recommend only using events which register to
             * ALL Ad Networks you include in your build.
             */
            /*
             * Available for: AdColony, Vungle, AppLovin, UnityAds
             * Invoked when the video ad starts playing.
             */
            @Override
            public void onRewardedVideoAdStarted() {
                Log.d(TAG, "onRewardedVideoAdStarted() called!");
                sendEvent("ironSourceRewardedVideoAdStarted", null);
            }
            /*
             * Available for: AdColony, Vungle, AppLovin, UnityAds
             * Invoked when the video ad finishes playing.
             */
            @Override
            public void onRewardedVideoAdEnded() {
                Log.d(TAG, "onRewardedVideoAdEnded() called!");
                sendEvent("ironSourceRewardedVideoAdEnded", null);
            }
        });
        promise.resolve(null);
    }

    @ReactMethod
    public void showRewardedVideo() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "showRewardedVideo() called!!");
                boolean available = IronSource.isRewardedVideoAvailable();
                if (available) {
                    Log.d(TAG, "isRewardedVideoAvailable() = true");
                    IronSource.showRewardedVideo();
                } else {
                    Log.d(TAG, "isRewardedVideoAvailable() = false");
                }
            }
        });
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
