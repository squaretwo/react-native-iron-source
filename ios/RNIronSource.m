#import "RNIronSource.h"

@implementation RNIronSource {
    RCTResponseSenderBlock _requestRewardedVideoCallback;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

// Initialize IronSource before showing the Rewarded Video
RCT_EXPORT_METHOD(initializeIronSource:(NSString *)appId userId:(NSString *)userId)
{
    NSLog(@"initializeRewardedVideo called!! with key %@ and user id %@", appId, userId);
    [IronSource setUserId:userId];
    [IronSource initWithAppKey:appId];
}
@end

