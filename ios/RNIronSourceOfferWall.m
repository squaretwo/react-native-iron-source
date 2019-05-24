#import "RNIronSourceOfferwall.h"
#import "RCTUtils.h"

NSString *const kIronSourceOfferwallAvailable = @"ironSourceOfferwallAvailable";
NSString *const kIronSourceOfferwallUnavailable = @"ironSourceOfferwallUnavailable";
NSString *const kIronSourceOfferwallDidShow = @"ironSourceOfferwallDidShow";
NSString *const kIronSourceOfferwallClosedByError = @"ironSourceOfferwallClosedByError";
NSString *const kIronSourceOfferwallClosedByUser = @"ironSourceOfferwallClosedByUser";
NSString *const kIronSourceOfferwallReceivedCredits = @"ironSourceOfferwallReceivedCredits";
NSString *const kIronSourceOfferwallFailedToReceiveCreditsByError = @"ironSourceOfferwallFailedToReceiveCreditsByError";

@implementation RNIronSourceOfferwall {
    RCTResponseSenderBlock _requestOfferwallCallback;
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

RCT_EXPORT_MODULE()

- (NSArray<NSString *> *)supportedEvents {
    return @[kIronSourceOfferwallAvailable,
             kIronSourceOfferwallUnavailable,
             kIronSourceOfferwallDidShow,
             kIronSourceOfferwallClosedByError,
             kIronSourceOfferwallClosedByUser,
             kIronSourceOfferwallReceivedCredits,
             kIronSourceOfferwallFailedToReceiveCreditsByError,
             ];
}

// Initialize IronSource before showing the Offerwall
RCT_EXPORT_METHOD(initializeOfferwall)
{
    NSLog(@"initializeOfferwall called!!");
    [IronSource setOfferwallDelegate:self];
    [ISSupersonicAdsConfiguration configurations].useClientSideCallbacks = [NSNumber numberWithInt:1];
}

//
// Show the Ad
//
RCT_EXPORT_METHOD(showOfferwall)
{
    if ([IronSource hasOfferwall]) {
        NSLog(@"showOfferwall - offerwall available");
        [self sendEventWithName:kIronSourceOfferwallAvailable body:nil];
        
        dispatch_async(dispatch_get_main_queue(), ^{
            [IronSource showOfferwallWithViewController:RCTPresentedViewController()];
        });
    } else {
        NSLog(@"showOfferwall - offerwall unavailable");
        [self sendEventWithName:kIronSourceOfferwallUnavailable body:nil];
    }
}


#pragma mark delegate events

#pragma mark - ISOfferwallDelegate

//Invoked when there is a change in the Offerwall availability status.
//@param - available - value will change to YES when Offerwall are //available.
//You can then show the video by calling showOfferwall(). Value will //change to NO when Offerwall isn't available.
- (void)offerwallHasChangedAvailability:(BOOL)available {
    if(available == YES){
        NSLog(@">>>>>>>>>>>> Offerwall available");
        [self sendEventWithName:kIronSourceOfferwallAvailable body:nil];
    } else {
        NSLog(@">>>>>>>>>>>> Offerwall NOT available");
    }
}

//Called each time the Offerwall successfully loads for the user
-(void)offerwallDidShow {
    NSLog(@">>>>>>>>>>>> Offerwall did show!");
    [self sendEventWithName:kIronSourceOfferwallDidShow body:nil];
}

//Called each time the Offerwall fails to show
//@param error - will contain the failure code and description
- (void)offerwallDidFailToShowWithError:(NSError *)error {
    NSLog(@">>>>>>>>>>>> Offerwall closed due to an error: %@!", error);
    [self sendEventWithName:kIronSourceOfferwallClosedByError body:nil];
}

//Called when the user closes the Offerwall
-(void)offerwallDidClose{
    NSLog(@">>>>>>>>>>>> Offerwall closed!");
    [self sendEventWithName:kIronSourceOfferwallClosedByUser body:nil];
}

- (BOOL)didReceiveOfferwallCredits:(NSDictionary *)creditInfo{
    [self sendEventWithName:kIronSourceOfferwallReceivedCredits body:creditInfo];
    return YES;
}

- (void)didFailToReceiveOfferwallCreditsWithError:(NSError *)error{
    [self sendEventWithName:kIronSourceOfferwallFailedToReceiveCreditsByError body:nil];
}

@end
