
#if __has_include(<React/RCTBridgeModule.h>)
#import <React/RCTBridgeModule.h>
#else
#import "RCTBridgeModule.h"
#endif

#if __has_include(<React/RCTEventEmitter.h>)
#import <React/RCTEventEmitter.h>
#else
#import "RCTEventEmitter"
#endif

#import "IronSource/IronSource.h"
#import "React/RCTConvert.h"

@interface RNIronSourceBanner : RCTEventEmitter <RCTBridgeModule, ISBannerDelegate>

@property (nonatomic, assign)   ISBannerView*   bannerView;

@end

