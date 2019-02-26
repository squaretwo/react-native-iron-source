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
import com.facebook.react.uimanager.ViewManager;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.integration.IntegrationHelper;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.model.Placement;
import com.ironsource.mediationsdk.sdk.RewardedVideoListener;
import com.ironsource.sdk.utils.SDKUtils;

public class RNIronSourceModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNIronSource";
    private static final int OFFER_WALL_REQUEST = 1;

    private ReactApplicationContext reactContext;

    public RNIronSourceModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return TAG;
    }

    @ReactMethod
    public void initializeIronSource(final String appId, final String userId) {
        SDKUtils.setDebugMode(1);
        IntegrationHelper.validateIntegration(reactContext.getCurrentActivity());
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                IronSource.setUserId(userId);
                IronSource.init(reactContext.getCurrentActivity(), appId);
            }
        });
    }
}
