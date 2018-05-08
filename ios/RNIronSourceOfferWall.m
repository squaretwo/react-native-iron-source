#import "RNIronSourceOfferwall.h"

NSString *const kIronSourceOfferwallAvailable = @"ironSourceOfferwallAvailable";
NSString *const kIronSourceOfferwallUnavailable = @"ironSourceOfferwallUnavailable";
NSString *const kIronSourceOfferwallDidShow = @"ironSourceOfferwallDidShow";
NSString *const kIronSourceOfferwallClosedByError = @"ironSourceOfferwallClosedByError";
NSString *const kIronSourceOfferwallClosedByUser = @"ironSourceOfferwallClosedByUser";

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
             kIronSourceOfferwallClosedByUser
             ];
}

// Initialize IronSource before showing the Offerwall
RCT_EXPORT_METHOD(initializeOfferwall)
{
    NSLog(@"initializeOfferwall called!!");
    [IronSource setOfferwallDelegate:self];
}

//
// Show the Ad
//
RCT_EXPORT_METHOD(showOfferwall)
{
    if ([IronSource hasOfferwall]) {
        NSLog(@"showOfferwall - offerwall available");
        [self sendEventWithName:@"ironSourceOfferwallAvailable" body:nil];
        [IronSource showOfferwallWithViewController:[UIApplication sharedApplication].delegate.window.rootViewController];
    } else {
        NSLog(@"showOfferwall - offerwall unavailable");
        [self sendEventWithName:@"ironSourceOfferwallUnavailable" body:nil];
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
        [self sendEventWithName:@"ironSourceOfferwallAvailable" body:nil];
    } else {
        NSLog(@">>>>>>>>>>>> Offerwall NOT available");
    }
}

//Called each time the Offerwall successfully loads for the user
-(void)offerwallDidShow {
    NSLog(@">>>>>>>>>>>> Offerwall did show!");
    [self sendEventWithName:@"ironSourceOfferwallDidShow" body:nil];
}

//Called each time the Offerwall fails to show
//@param error - will contain the failure code and description
- (void)offerwallDidFailToShowWithError:(NSError *)error {
    NSLog(@">>>>>>>>>>>> Offerwall closed due to an error: %@!", error);
    [self sendEventWithName:@"ironSourceOfferwallClosedByError" body:nil];
}

//Called when the user closes the Offerwall
-(void)offerwallDidClose{
    NSLog(@">>>>>>>>>>>> Offerwall closed!");
    [self sendEventWithName:@"ironSourceOfferwallClosedByUser" body:nil];
}

- (BOOL)didReceiveOfferwallCredits:(NSDictionary *)creditInfo{
    return YES;
}

- (void)didFailToReceiveOfferwallCreditsWithError:(NSError *)error{
}


@end
