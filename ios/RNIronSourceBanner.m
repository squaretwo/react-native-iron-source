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

RCT_EXPORT_METHOD(loadBanner:(NSString *)bannerSizeDescription) {
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

- (void)bannerDidLoad:(ISBannerView *)bannerView {
    if (hasListeners) {
        [self sendEventWithName:kIronSourceBannerDidLoad body:nil];
    }
    dispatch_async(dispatch_get_main_queue(), ^{
        UIViewController *viewController = RCTPresentedViewController();
        CGFloat bottomSafeAreaLength = 0;
        if (@available(iOS 11.0, *)) {
            bottomSafeAreaLength = viewController.view.safeAreaInsets.bottom;
        } else {
            bottomSafeAreaLength = viewController.bottomLayoutGuide.length;
        }
        self.bannerView = bannerView;
        self.bannerView.center = CGPointMake(viewController.view.center.x, viewController.view.frame.size.height - self.bannerView.frame.size.height / 2 - bottomSafeAreaLength);
        [viewController.view addSubview:self.bannerView];
        self.bannerView.hidden = YES;
    });
}

- (void)bannerDidFailToLoadWithError:(NSError *)error {
    if (hasListeners) {
        [self sendEventWithName:kIronSourceBannerDidFailToLoadWithError body:nil];
    }
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
