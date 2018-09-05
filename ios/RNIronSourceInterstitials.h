#if __has_include(<React/RCTEventEmitter.h>)
#import <React/RCTEventEmitter.h>
#else
#import "RCTEventEmitter.h"
#endif

#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif

#import "IronSource/IronSource.h"

@interface RNIronSourceInterstitials : RCTEventEmitter <RCTBridgeModule, ISInterstitialDelegate>

@end
