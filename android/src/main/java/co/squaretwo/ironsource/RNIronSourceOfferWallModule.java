package co.squaretwo.ironsource;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.supersonic.mediationsdk.sdk.OfferwallListener;

/**
 * Created by benyee on 11/08/2016.
 */
public class RNIronSourceOfferWallModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNIronSourceOfferWall";
    private static final int OFFER_WALL_REQUEST = 1;

    private ReactApplicationContext mContext;
    private Intent mOfferWallIntent;

    public RNIronSourceOfferWallModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void initializeOfferWall(final String appId, final String securityToken, final String userId) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Settings appId:" + appId);
            }
        });
    }

    @ReactMethod
    public void showOfferWall() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
              Log.d(TAG, "showOfferWall started");
            }
        });
    }
}
