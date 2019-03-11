package co.squaretwo.ironsource;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.react.ReactActivity;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

public class RNIronSourceBannerModule extends ReactContextBaseJavaModule implements BannerListener, LifecycleEventListener {

    public RNIronSourceBannerModule(ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @Override
    public String getName() {
        return "RNIronSourceBanner";
    }

    private IronSourceBannerLayout bannerLayout;

    @ReactMethod
    public void initializeBanner() {
    }

    private ISBannerSize getBannerSize(String sizeDescription) {
        ISBannerSize bannerSize = ISBannerSize.BANNER;
        if (sizeDescription.equals("LARGE")) {
            bannerSize = ISBannerSize.LARGE;
        } else if (sizeDescription.equals("RECTANGLE")) {
            bannerSize = ISBannerSize.RECTANGLE;
        } else if (sizeDescription.equals("SMART")) {
            bannerSize = ISBannerSize.SMART;
        }
        return bannerSize;
    }

    @ReactMethod
    public void loadBanner(final String sizeDescription) {
        final Activity activity = getReactApplicationContext().getCurrentActivity();
        if (activity == null) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final FrameLayout rootView = activity.findViewById(android.R.id.content);

                final ISBannerSize bannerSize = RNIronSourceBannerModule.this.getBannerSize(sizeDescription);

                if (bannerLayout != null) {
                    rootView.removeView(bannerLayout);
                    RNIronSourceBannerModule.this.destroyBanner();
                }

                bannerLayout = IronSource.createBanner(activity, bannerSize);
                IronSource.loadBanner(bannerLayout);

                final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                );
                bannerLayout.setLayoutParams(layoutParams);
                bannerLayout.setVisibility(View.INVISIBLE);
                layoutParams.gravity = Gravity.BOTTOM;
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

    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        getReactApplicationContext().getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }

    @Override
    public void onHostResume() {
        IronSource.onResume(getReactApplicationContext().getCurrentActivity());
    }

    @Override
    public void onHostPause() {
        IronSource.onPause(getReactApplicationContext().getCurrentActivity());
    }

    @Override
    public void onHostDestroy() {
        this.destroyBanner();
    }
}
