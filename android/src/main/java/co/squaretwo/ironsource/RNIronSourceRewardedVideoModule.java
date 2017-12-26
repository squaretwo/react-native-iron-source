package co.squaretwo.ironsource;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.support.annotation.Nullable;
import com.facebook.react.bridge.Callback;
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
    private static final String TAG = "RewardedVideo";
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
    public void initializeRewardedVideo() {
        IronSource.setRewardedVideoListener(new RewardedVideoListener() {
            @Override
            public void onRewardedVideoAdOpened() {
                Log.d(TAG, "onRewardedVideoAdOpened() called!");
                sendEvent("ironSourceRewardedVideoDidStart", null);
            }
            @Override
            public void onRewardedVideoAdClosed() {
                Log.d(TAG, "onRewardedVideoAdClosed() called!");
                sendEvent("ironSourceRewardedVideoClosedByUser", null);
            }
            @Override
            public void onRewardedVideoAvailabilityChanged(boolean available) {
                Log.d(TAG, "onVideoAvailabilityChanged() called!");
                if (available) {
                    Log.d(TAG, "ironSourceRewardedVideoAvailable!" );
                    sendEvent("ironSourceRewardedVideoAvailable", null);
                } else {
                    sendEvent("ironSourceRewardedVideoUnavailable", null);
                }
            }
            @Override
            public void onRewardedVideoAdStarted() {
                Log.d(TAG, "onVideoStart() called!");
                sendEvent("ironSourceRewardedVideoAdStarted", null);
            }
            @Override
            public void onRewardedVideoAdEnded() {
                Log.d(TAG, "onVideoEnd() called!");
                sendEvent("ironSourceRewardedVideoAdEnded", null);
            }
            @Override
            public void onRewardedVideoAdRewarded(Placement placement) {
                //TODO - here you can reward the user according to the given amount.
                String rewardName = placement.getRewardName();
                int rewardAmount = placement.getRewardAmount();
            }
            @Override
            public void onRewardedVideoAdShowFailed(IronSourceError se) {
                Log.d(TAG, "onRewardedVideoShowFail() called!");
                sendEvent("ironSourceRewardedVideoClosedByError", null);
            }
        });
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
