
package co.squaretwo.ironsource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;

public class RNIronSourcePackage implements ReactPackage {
    public RNIronSourcePackage() {
    }
    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
      return Arrays.<NativeModule>asList(
              new RNIronSourceModule(reactContext),
              new RNIronSourceSegmentModule(reactContext),
              new RNIronSourceRewardedVideoModule(reactContext),
              new RNIronSourceOfferwallModule(reactContext),
              new RNIronSourceInterstitialsModule(reactContext),
              new RNIronSourceBannerModule(reactContext)
      );
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
      return Collections.emptyList();
    }
}
