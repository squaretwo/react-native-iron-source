package co.squaretwo.ironsource;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.support.annotation.Nullable;
import com.facebook.react.bridge.Arguments;
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
import com.ironsource.mediationsdk.sdk.InterstitialListener;

public class RNIronSourceInterstitialsModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNIronSourceInterstitials";
    private final ReactApplicationContext reactContext;

    public RNIronSourceInterstitialsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void loadInterstitial() {
        IronSource.setInterstitialListener(new InterstitialListener() {
            /**
             Invoked when Interstitial Ad is ready to be shown after load function was called.
             */
            @Override
            public void onInterstitialAdReady() {
                sendEvent("interstitialDidLoad", null);
            }
            /**
             invoked when there is no Interstitial Ad available after calling load function.
             */
            @Override
            public void onInterstitialAdLoadFailed(IronSourceError error) {
                final WritableMap params = Arguments.createMap();
                params.putString("error_code", error.getErrorMessage());

                sendEvent("interstitialDidFailToLoadWithError", params);
            }
            /**
             Invoked when the Interstitial Ad Unit is opened
             */
            @Override
            public void onInterstitialAdOpened() {
                sendEvent("interstitialDidOpen", null);
            }
            /*
             * Invoked when the ad is closed and the user is about to return to the application.
             */
            @Override
            public void onInterstitialAdClosed() {
                sendEvent("interstitialDidClose", null);
            }
            /*
             * Invoked when the ad was opened and shown successfully.
             */
            @Override
            public void onInterstitialAdShowSucceeded() {
                sendEvent("interstitialDidShow", null);
            }
            /**
             * Invoked when Interstitial ad failed to show.
             // @param error - An object which represents the reason of showInterstitial failure.
             */
            @Override
            public void onInterstitialAdShowFailed(IronSourceError error) {
                final WritableMap params = Arguments.createMap();
                params.putString("error_code", error.getErrorMessage());

                sendEvent("interstitialDidFailToShowWithError", params);
            }
            /*
             * Invoked when the end user clicked on the interstitial ad.
             */
            @Override
            public void onInterstitialAdClicked() {
                sendEvent("didClickInterstitial", null);
            }
        });
        IronSource.loadInterstitial();
    }

    @ReactMethod
    public void showInterstitial(String placementName) {
        if (!IronSource.isInterstitialPlacementCapped(placementName)) {
            IronSource.showInterstitial(placementName);
        }
    }


    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
