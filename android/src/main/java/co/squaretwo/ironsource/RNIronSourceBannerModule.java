package co.squaretwo.ironsource;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;

import java.util.HashMap;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class RNIronSourceBannerModule extends ReactContextBaseJavaModule implements BannerListener, LifecycleEventListener {

    public RNIronSourceBannerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    static HashMap<ISBannerSize, HashMap> sizeMap = createSizeMap();

    static HashMap<String, ISBannerSize> sizeDescriptionMap = createSizeDescriptionMap();

    static HashMap<String, Integer> positionsMap = createPositionsMap();

    @Override
    public String getName() {
        return "RNIronSourceBanner";
    }

    private IronSourceBannerLayout bannerLayout;

    private Activity activity;

    private WritableMap bannerSize;

    private Promise loadPromise;

    @ReactMethod
    public void initializeBanner() {
        activity = getReactApplicationContext().getCurrentActivity();
    }

    private static HashMap<ISBannerSize, HashMap> createSizeMap() {
        final HashMap<ISBannerSize, HashMap> sizeMap = new HashMap<>();
        final HashMap<String, Integer> normalSize = new HashMap<>();
        final HashMap<String, Integer> largeSize = new HashMap<>();
        final HashMap<String, Integer> rectangleSize = new HashMap<>();
        final HashMap<String, Integer> smartSize = new HashMap<>();

        normalSize.put("width", 320);
        normalSize.put("height", 50);
        sizeMap.put(ISBannerSize.BANNER, normalSize);

        largeSize.put("width", 320);
        largeSize.put("height", 90);
        sizeMap.put(ISBannerSize.LARGE, largeSize);

        rectangleSize.put("width", 300);
        rectangleSize.put("height", 250);
        sizeMap.put(ISBannerSize.RECTANGLE, rectangleSize);

        smartSize.put("width", 320);
        smartSize.put("height", 50);
        sizeMap.put(ISBannerSize.SMART, smartSize);

        smartSize.put("altWidth", 320);
        smartSize.put("altHeight", 50);
        sizeMap.put(ISBannerSize.SMART, smartSize);

        return sizeMap;
    }

    private static HashMap<String, Integer> createPositionsMap() {
        final HashMap<String, Integer> positions = new HashMap<>();
        positions.put("top", Gravity.TOP);
        positions.put("bottom", Gravity.BOTTOM);
        return positions;
    }

    private static HashMap<String, ISBannerSize> createSizeDescriptionMap() {
        final HashMap<String, ISBannerSize> map = new HashMap<>();
        map.put("BANNER", ISBannerSize.BANNER);
        map.put("LARGE", ISBannerSize.LARGE);
        map.put("RECTANGLE", ISBannerSize.RECTANGLE);
        map.put("SMART", ISBannerSize.SMART);
        return map;
    }

    private HashMap getBannerSize(ISBannerSize bannerSizeDescription) {
        if (bannerSizeDescription.equals(ISBannerSize.SMART)) {
            final DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
            if (displayMetrics.widthPixels / displayMetrics.density > 720) {
                final HashMap<String, Integer> altSize = new HashMap<>();
                final HashMap<String, Integer> smartSize = sizeMap.get(ISBannerSize.SMART);
                altSize.put("width", smartSize.get("altWidth"));
                altSize.put("height", smartSize.get("altHeight"));
                return altSize;
            }
            return sizeMap.get(ISBannerSize.SMART);
        }
        return sizeMap.get(bannerSizeDescription);
    }

    private ISBannerSize getBannerSizeDescription(String sizeDescriptionString) {
        ISBannerSize bannerSize = sizeDescriptionMap.get(sizeDescriptionString);
        if (bannerSize == null) {
            bannerSize = ISBannerSize.BANNER;
        }
        return bannerSize;
    }

    @ReactMethod
    public void loadBanner(final String sizeDescriptionString, final ReadableMap options, final Promise promise) {
        loadPromise = promise;
        final String position = options.getString("position");
        final boolean scaleToFitWidth = options.getBoolean("scaleToFitWidth");
        if (activity == null) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final FrameLayout rootView = activity.findViewById(android.R.id.content);
                final ISBannerSize bannerSizeDescription = RNIronSourceBannerModule.this.getBannerSizeDescription(sizeDescriptionString);

                if (bannerLayout != null) {
                    RNIronSourceBannerModule.this.destroyBanner();
                    rootView.removeView(bannerLayout);
                }

                bannerLayout = IronSource.createBanner(activity, bannerSizeDescription);
                bannerLayout.setBannerListener(RNIronSourceBannerModule.this);
                IronSource.loadBanner(bannerLayout);

                final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                bannerLayout.setLayoutParams(layoutParams);
                bannerLayout.setVisibility(View.INVISIBLE);

                final HashMap bannerSizeMap = getBannerSize(bannerSizeDescription);
                int bannerWidth = (int) bannerSizeMap.get("width");
                int bannerHeight = (int) bannerSizeMap.get("height");

                if (scaleToFitWidth) {
                    final float displayDensity = activity.getResources().getDisplayMetrics().density;
                    final double rootViewWidth = rootView.getWidth() / displayDensity;
                    float scale = (float) rootViewWidth / (float) bannerWidth;
                    float translateY = (float) bannerHeight * (1 - scale) * displayDensity / 2;

                    if (position.equals("top")) {
                        translateY = -translateY;
                    }

                    bannerLayout.setScaleX(scale);
                    bannerLayout.setScaleY(scale);
                    bannerLayout.setTranslationY(translateY);

                    bannerSize = Arguments.createMap();
                    bannerSize.putDouble("width", bannerWidth * scale);
                    bannerSize.putDouble("height", bannerHeight * scale);
                } else {
                    bannerSize = Arguments.createMap();
                    bannerSize.putDouble("width", bannerWidth);
                    bannerSize.putDouble("height", bannerHeight);
                }

                layoutParams.gravity = positionsMap.get(position);
                rootView.addView(bannerLayout);
            }
        });
    }

    @ReactMethod
    public void destroyBanner() {
        if (bannerLayout != null) {
            IronSource.destroyBanner(bannerLayout);
        }
    }

    @ReactMethod
    public void showBanner() {
        if (bannerLayout != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bannerLayout.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @ReactMethod
    public void hideBanner() {
        if (bannerLayout != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    bannerLayout.setVisibility(View.INVISIBLE);
                }
            });
        }
    }

    @Override
    public void onBannerAdLoaded() {
        sendEvent("ironSourceBannerDidLoad", null);
        loadPromise.resolve(bannerSize);
    }

    @Override
    public void onBannerAdLoadFailed(IronSourceError ironSourceError) {
        sendEvent("ironSourceBannerDidFailToLoadWithError", null);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bannerLayout.removeAllViews();
            }
        });
        loadPromise.reject("E_LOAD", "Failed to load banner");
    }

    @Override
    public void onBannerAdClicked() {
        sendEvent("ironSourceDidClickBanner", null);
    }

    @Override
    public void onBannerAdScreenPresented() {
        sendEvent("ironSourceBannerWillPresentScreen", null);
    }

    @Override
    public void onBannerAdScreenDismissed() {
        sendEvent("ironSourceBannerDidDismissScreen", null);
    }

    @Override
    public void onBannerAdLeftApplication() {
        sendEvent("ironSourceBannerWillLeaveApplication", null);
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    @Override
    public void onHostResume() {
        IronSource.onResume(activity);
    }

    @Override
    public void onHostPause() {
        IronSource.onPause(activity);
    }

    @Override
    public void onHostDestroy() {
        this.destroyBanner();
    }
}
