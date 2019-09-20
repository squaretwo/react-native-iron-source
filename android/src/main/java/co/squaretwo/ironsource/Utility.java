package co.squaretwo.ironsource;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.ironsource.mediationsdk.logger.IronSourceError;

import javax.annotation.Nullable;

final class Utility {
    static WritableMap formatIronSourceError(IronSourceError error, @Nullable String message) {
        WritableMap map = Arguments.createMap();
        map.putString(String.valueOf(error.getErrorCode()), message == null ? error.getErrorMessage() : message);
        return map;
    }
    static WritableMap formatIronSourceError(IronSourceError error) {
        return Utility.formatIronSourceError(error, null);
    }
}
