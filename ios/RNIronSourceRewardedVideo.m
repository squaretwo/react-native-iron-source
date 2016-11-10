
#import "RNIronSourceRewardedVideo.h"

@implementation RNIronSourceRewardedVideo

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}
RCT_EXPORT_MODULE()


// Initialize Fyber before showing the offer wall
RCT_EXPORT_METHOD(initializeAdsForUser:(NSString *)appId userId:(NSString *)userId)
{
  [[Supersonic sharedInstance] setRVDelegate:[UIApplication sharedApplication].delegate];
  [[Supersonic sharedInstance] initRVWithAppKey:appId withUserId:userId];
}

//
// Show the Ad
//
RCT_EXPORT_METHOD(showAd)
{
  if ([[Supersonic sharedInstance] isAdAvailable]) {
    [[Supersonic sharedInstance] showRVWithViewController:[UIApplication sharedApplication].delegate.window.rootViewController];
    NSLog(@"showAd called!!");
  } else {
      NSLog(@"showAd called!! but no adds are available");
  }

}

@end
