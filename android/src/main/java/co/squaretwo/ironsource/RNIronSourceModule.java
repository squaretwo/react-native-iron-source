package co.squaretwo.ironsource;

import android.os.Handler;
import android.os.Looper;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.ironsource.mediationsdk.IronSource;

public class RNIronSourceModule extends ReactContextBaseJavaModule {
    private static final String TAG = "RNIronSource";

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
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                IronSource.setUserId(userId);
                IronSource.init(reactContext.getCurrentActivity(), appId);
            }
        });
    }
}
