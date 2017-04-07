#import "RNIronSourceRewardedVideo.h"

@implementation RNIronSourceRewardedVideo {
    RCTResponseSenderBlock _requestRewardedVideoCallback;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE()

// Initialize IronSource before showing the Rewarded Video
RCT_EXPORT_METHOD(initializeRewardedVideo:(NSString *)appId userId:(NSString *)userId)
{
    NSLog(@"initializeRewardedVideo called!! with key %@ and user id %@", appId, userId);
    [IronSource setRewardedVideoDelegate:self];
    [IronSource initWithAppKey:appId];
    [IronSource setUserId:userId];
    [ISIntegrationHelper validateIntegration];
}

//
// Show the Ad
//
RCT_EXPORT_METHOD(showRewardedVideo)
{
    if ([IronSource hasRewardedVideo]) {
        NSLog(@"showRewardedVideo - video available");
        [self.bridge.eventDispatcher sendDeviceEventWithName:@"rewardedVideoAvailable" body:nil];
        [IronSource showRewardedVideoWithViewController:[UIApplication sharedApplication].delegate.window.rootViewController];

    } else {
        NSLog(@"showRewardedVideo - video unavailable");
        [self.bridge.eventDispatcher sendDeviceEventWithName:@"rewardedVideoUnavailable" body:nil];
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
         [self.bridge.eventDispatcher sendDeviceEventWithName:@"rewardedVideoAvailable" body:nil];
     } else {
         NSLog(@">>>>>>>>>>>> RewardedVideo NOT available");
     }
 }

- (void)didReceiveRewardForPlacement:(ISPlacementInfo*)placementInfo {
    NSNumber * rewardAmount = [placementInfo rewardAmount];
    NSString * rewardName = [placementInfo rewardName];
    NSLog(@">>>>>>>>>>>> RewardedVideo %@ reward amount %@", rewardName, rewardAmount);
    [self.bridge.eventDispatcher sendDeviceEventWithName:@"rewardedVideoAdRewarded" body:nil];
}

/**
 * Invoked when an Ad failed to display.
 * @param - error - NSError which contains the reason for the
 * failure. The error contains error.code and error.message.
 */
- (void)rewardedVideoDidFailToShowWithError:(NSError *)error {
    NSLog(@">>>>>>>>>>>> RewardedVideo ad closed due to an error: %@!", error);
    [self.bridge.eventDispatcher sendDeviceEventWithName:@"rewardedVideoClosedByError" body:nil];
}

/**
 * Invoked when the RewardedVideo ad view has opened.
 */
- (void)rewardedVideoDidOpen{
    NSLog(@">>>>>>>>>>>> RewardedVideo opened!");
    [self.bridge.eventDispatcher sendDeviceEventWithName:@"rewardedVideoDidStart" body:nil];
}

/**
 * Invoked when the user is about to return to the application after closing the
 * RewardedVideo ad.
 */
- (void)rewardedVideoDidClose {
    NSLog(@">>>>>>>>>>>> RewardedVideo closed!");
    [self.bridge.eventDispatcher sendDeviceEventWithName:@"rewardedVideoClosedByUser" body:nil];
}

/**
 * Note: the events below are not available for all supported Rewarded Video
 * Ad Networks.
 * Check which events are available per Ad Network you choose to include in
 * your build.
 * We recommend only using events which register to ALL Ad Networks you
 * include in your build.
 */
/**
 * Available for: AdColony, Vungle, AppLovin, UnityAds
 * Invoked when the video ad starts playing.
 */
- (void)rewardedVideoDidStart {
    NSLog(@">>>>>>>>>>>> RewardedVideo Ad Started!");
    [self.bridge.eventDispatcher sendDeviceEventWithName:@"rewardedVideoAdStarted" body:nil];
}

/**
 * Available for: AdColony, Vungle, AppLovin, UnityAds
 * Invoked when the video ad finishes playing.
 */
- (void)rewardedVideoDidEnd {
    NSLog(@">>>>>>>>>>>> RewardedVideo Ad Ended!");
    [self.bridge.eventDispatcher sendDeviceEventWithName:@"rewardedVideoAdEnded" body:nil];
}

@end
