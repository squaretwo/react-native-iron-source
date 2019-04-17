#import "RNIronSourceRewardedVideo.h"

#import "RCTUtils.h"

NSString *const kIronSourceRewardedVideoAvailable = @"ironSourceRewardedVideoAvailable";
NSString *const kIronSourceRewardedVideoUnavailable = @"ironSourceRewardedVideoUnavailable";
NSString *const kIronSourceRewardedVideoAdRewarded = @"ironSourceRewardedVideoAdRewarded";
NSString *const kIronSourceRewardedVideoClosedByError = @"ironSourceRewardedVideoClosedByError";
NSString *const kIronSourceRewardedVideoClosedByUser = @"ironSourceRewardedVideoClosedByUser";
NSString *const kIronSourceRewardedVideoDidStart = @"ironSourceRewardedVideoDidStart";
NSString *const kIronSourceRewardedVideoDidOpen = @"ironSourceRewardedVideoDidOpen";
NSString *const kIronSourceRewardedVideoAdStarted = @"ironSourceRewardedVideoAdStarted";
NSString *const kIronSourceRewardedVideoAdEnded = @"ironSourceRewardedVideoAdEnded";

@implementation RNIronSourceRewardedVideo {
    RCTResponseSenderBlock _requestRewardedVideoCallback;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

- (NSArray<NSString *> *)supportedEvents {
    return @[kIronSourceRewardedVideoAvailable,
             kIronSourceRewardedVideoUnavailable,
             kIronSourceRewardedVideoAdRewarded,
             kIronSourceRewardedVideoClosedByError,
             kIronSourceRewardedVideoClosedByUser,
             kIronSourceRewardedVideoDidStart,
             kIronSourceRewardedVideoDidOpen,
             kIronSourceRewardedVideoAdStarted,
             kIronSourceRewardedVideoAdEnded
             ];
}

// Initialize IronSource before showing the Rewarded Video
RCT_EXPORT_METHOD(initializeRewardedVideo:(RCTPromiseResolveBlock)resolve
                  rejector:(RCTPromiseRejectBlock)reject)
{
    NSLog(@"initializeRewardedVideo called!!");
    [IronSource setRewardedVideoDelegate:self];
    resolve(nil);
}

//
// Show the Ad
//
RCT_EXPORT_METHOD(showRewardedVideo)
{
    if ([IronSource hasRewardedVideo]) {
        NSLog(@"showRewardedVideo - video available");
        [self sendEventWithName:kIronSourceRewardedVideoAvailable body:nil];
        [IronSource showRewardedVideoWithViewController:RCTPresentedViewController()];
    } else {
        NSLog(@"showRewardedVideo - video unavailable");
        [self sendEventWithName:kIronSourceRewardedVideoUnavailable body:nil];
    }
}

#pragma mark delegate events

/**
 * Invoked when there is a change in the ad availability status.
 * @param - hasAvailableAds - value will change to YES when rewarded videos are available. * You can then show the video by calling showRV(). Value will change to NO when no videos are available.
 */
 - (void)rewardedVideoHasChangedAvailability:(BOOL)available {
     if(available == YES){
         NSLog(@">>>>>>>>>>>> RewardedVideo available");
         [self sendEventWithName:kIronSourceRewardedVideoAvailable body:nil];
     } else {
         NSLog(@">>>>>>>>>>>> RewardedVideo NOT available");
         [self sendEventWithName:kIronSourceRewardedVideoUnavailable body:nil];
     }
 }

- (void)didReceiveRewardForPlacement:(ISPlacementInfo*)placementInfo {
    NSNumber * rewardAmount = [placementInfo rewardAmount];
    NSString * rewardName = [placementInfo rewardName];
    NSLog(@">>>>>>>>>>>> RewardedVideo %@ reward amount %@", rewardName, rewardAmount);
    [self sendEventWithName:kIronSourceRewardedVideoAdRewarded body:nil];
}

/**
 * Invoked when an Ad failed to display.
 * @param - error - NSError which contains the reason for the
 * failure. The error contains error.code and error.message.
 */
- (void)rewardedVideoDidFailToShowWithError:(NSError *)error {
    NSLog(@">>>>>>>>>>>> RewardedVideo ad closed due to an error: %@!", error);
    [self sendEventWithName:kIronSourceRewardedVideoClosedByError body:nil];
}

/**
 * Invoked when the RewardedVideo ad view has opened.
 */
- (void)rewardedVideoDidOpen{
    NSLog(@">>>>>>>>>>>> RewardedVideo opened!");
    // @Deprecated kIronSourceRewardedVideoDidStart
    [self sendEventWithName:kIronSourceRewardedVideoDidStart body:nil];
    [self sendEventWithName:kIronSourceRewardedVideoDidOpen body:nil];
}

/**
 * Invoked when the user is about to return to the application after closing the
 * RewardedVideo ad.
 */
- (void)rewardedVideoDidClose {
    NSLog(@">>>>>>>>>>>> RewardedVideo closed!");
    [self sendEventWithName:kIronSourceRewardedVideoClosedByUser body:nil];
}

/**
 * Note: the events below are not available
 * for all supported Rewarded Video Ad Networks.
 * Check which events are available per Ad Network
 * you choose to include in your build.
 * We recommend only using events which register to
 * ALL Ad Networks you include in your build.
 */
/**
 * Available for: AdColony, Vungle, AppLovin, UnityAds
 * Invoked when the video ad starts playing.
 */
- (void)rewardedVideoDidStart {
    NSLog(@">>>>>>>>>>>> RewardedVideo Ad Started!");
    [self sendEventWithName:kIronSourceRewardedVideoAdStarted body:nil];
}

/**
 * Available for: AdColony, Vungle, AppLovin, UnityAds
 * Invoked when the video ad finishes playing.
 */
- (void)rewardedVideoDidEnd {
    NSLog(@">>>>>>>>>>>> RewardedVideo Ad Ended!");
    [self sendEventWithName:kIronSourceRewardedVideoAdEnded body:nil];
}

@end
