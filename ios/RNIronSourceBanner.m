//
//  RNIronSourceBanner.m
//  RNIronSource
//
//  Created by Kazlouski Dmitriy on 2/21/19.
//

#import "RNIronSourceBanner.h"
#import "RCTUtils.h"

NSString *const kIronSourceBannerDidLoad = @"ironSourceBannerDidLoad";
NSString *const kIronSourceBannerDidFailToLoadWithError = @"ironSourceBannerDidFailToLoadWithError";
NSString *const kIronSourceBannerDidDismissScreen = @"ironSourceBannerDidDismissScreen";
NSString *const kIronSourceBannerWillLeaveApplication = @"ironSourceBannerWillLeaveApplication";
NSString *const kIronSourceBannerWillPresentScreen = @"ironSourceBannerWillPresentScreen";
NSString *const kIronSourceDidClickBanner = @"ironSourceDidClickBanner";

@implementation RNIronSourceBanner
{
    bool hasListeners;
    RCTPromiseResolveBlock resolveLoadBanner;
    RCTPromiseRejectBlock rejectLoadBanner;
    bool scaleToFitWidth;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

- (NSArray<NSString *> *)supportedEvents {
    return @[kIronSourceBannerDidLoad,
             kIronSourceBannerDidFailToLoadWithError,
             kIronSourceBannerDidDismissScreen,
             kIronSourceBannerWillLeaveApplication,
             kIronSourceBannerWillPresentScreen,
             kIronSourceDidClickBanner,
             ];
}

- (void)startObserving {
    hasListeners = YES;
}

- (void)stopObserving {
    hasListeners = NO;
}

RCT_EXPORT_METHOD(initializeBanner) {
    [IronSource setBannerDelegate:self];
}

RCT_EXPORT_METHOD(loadBanner:(NSString *)bannerSizeDescription
                  options:(NSDictionary *)options
                  resolver:(RCTPromiseResolveBlock)resolve
                  rejector:(RCTPromiseRejectBlock)reject) {
    
    scaleToFitWidth = [RCTConvert BOOL:options[@"scaleToFitWidth"]];
    resolveLoadBanner = resolve;
    rejectLoadBanner = reject;
    if (self.bannerView) {
        [self destroyBanner];
    }
    [IronSource loadBannerWithViewController:RCTPresentedViewController()
                                        size:[[ISBannerSize alloc] initWithDescription:bannerSizeDescription]];
}

RCT_EXPORT_METHOD(showBanner) {
    if (self.bannerView) {
        dispatch_async(dispatch_get_main_queue(), ^{
            self.bannerView.hidden = NO;
        });
    }
}

RCT_EXPORT_METHOD(hideBanner) {
    if (self.bannerView) {
        dispatch_async(dispatch_get_main_queue(), ^{
            self.bannerView.hidden = YES;
        });
    }
}

RCT_EXPORT_METHOD(destroyBanner) {
    if (self.bannerView) {
        [IronSource destroyBanner:self.bannerView];
        self.bannerView = nil;
    }
}

- (CGSize)getBannerSize:(ISBannerView *)bannerView {
    CGSize bannerSize = CGSizeMake(100, 100);
    for (UIView *view in bannerView.subviews){
        bannerSize = view.frame.size;
    }
    return bannerSize;
}


- (CGSize)getScaledBannerSize:(ISBannerView *)bannerView {
    CGSize bannerSize = [self getBannerSize:bannerView];
    CGFloat scale = [self getBannerScale:bannerView];
    return CGSizeMake(bannerSize.width * scale, bannerSize.height * scale);
}

- (CGFloat)getBottomSafeAreaLength {
    UIViewController *viewController = RCTPresentedViewController();
    CGFloat bottomSafeAreaLength = 0;
    if (@available(iOS 11.0, *)) {
        bottomSafeAreaLength = viewController.view.safeAreaInsets.bottom;
    } else {
        bottomSafeAreaLength = viewController.bottomLayoutGuide.length;
    }
    return bottomSafeAreaLength;
}

- (CGFloat)getBannerScale:(ISBannerView *)bannerView {
    CGSize bannerSize = [self getBannerSize:bannerView];
    UIViewController *viewController = RCTPresentedViewController();
    return viewController.view.frame.size.width / bannerSize.width;
}

- (void)bannerDidLoad:(ISBannerView *)bannerView {
    if (hasListeners) {
        [self sendEventWithName:kIronSourceBannerDidLoad body:nil];
    }
    dispatch_async(dispatch_get_main_queue(), ^{
        UIViewController *viewController = RCTPresentedViewController();
        
        self.bannerView = bannerView;
        
        CGFloat bottomSafeAreaLength = [self getBottomSafeAreaLength];
        
        CGSize bannerSize = self->scaleToFitWidth ? [self getScaledBannerSize:bannerView] : [self getBannerSize:bannerView];
        
        CGFloat bannerX = viewController.view.center.x;
        CGFloat bannerY = viewController.view.frame.size.height - bannerSize.height / 2;
        
        self.bannerView.center = CGPointMake(
             bannerX, bannerY - bottomSafeAreaLength);
        if (self->scaleToFitWidth) {
            CGFloat bannerScale = [self getBannerScale:bannerView];
            self.bannerView.transform = CGAffineTransformMakeScale(bannerScale, bannerScale);
        }
        self.bannerView.hidden = YES;
        [viewController.view addSubview:self.bannerView];
        
        self->resolveLoadBanner(@{
                                  @"width": [NSNumber numberWithFloat:bannerSize.width],
                                  @"height": [NSNumber numberWithFloat:bannerSize.height],
                                  });
    });
}

- (void)bannerDidFailToLoadWithError:(NSError *)error {
    if (hasListeners) {
        [self sendEventWithName:kIronSourceBannerDidFailToLoadWithError body:nil];
    }
    self->rejectLoadBanner(@"Error", @"Failed to load banner", error);
}

- (void)bannerDidDismissScreen {
    if (hasListeners) {
        [self sendEventWithName:kIronSourceBannerDidDismissScreen body:nil];
    }
}


- (void)bannerWillLeaveApplication {
    if (hasListeners) {
        [self sendEventWithName:kIronSourceBannerWillLeaveApplication body:nil];
    }
}


- (void)bannerWillPresentScreen {
    if (hasListeners) {
        [self sendEventWithName:kIronSourceBannerWillPresentScreen body:nil];
    }
}


- (void)didClickBanner {
    if (hasListeners) {
        [self sendEventWithName:kIronSourceDidClickBanner body:nil];
    }
}

@end
