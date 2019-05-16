
# @wowmaking/react-native-iron-source

[Iron Source SDK](https://developers.ironsrc.com/) React Native bridge. 
Supports all ad units (Rewarded Video, Interstitial, Banner, Offerwall).

Many thanks to all contributors of [squaretwo/react-native-iron-source](https://github.com/squaretwo/react-native-iron-source). 

The fork includes following improvements:
* Banner implementation
* Bug fixes for Offerwall, Interstitial, Rewarded
* Documentation
* Installation using CocoaPods
* Easier installation on android 
* etc

## Getting started

`npm install @wowmaking/react-native-iron-source --save`

You can find available versions [here](https://github.com/wowmaking/react-native-iron-source/releases).

### Mostly automatic installation

`$ react-native link @wowmaking/react-native-iron-source`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `@wowmaking/react-native-iron-source` and add `RNIronSource.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNIronSource.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)

#### iOS CocoaPods
1. Add `pod 'RNIronSource', :path => '../node_modules/@wowmaking/react-native-iron-source'` to your `ios/Podfile`
2. Run `pod install` while in `ios` directory

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNIronSourcePackage;` to the imports at the top of the file
  - Add `new RNIronSourcePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':@wowmaking_react-native-iron-source'
  	project(':@wowmaking_react-native-iron-source').projectDir = new File(rootProject.projectDir, 	'../node_modules/@wowmaking/react-native-iron-source/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
    implementation project(':@wowmaking_react-native-iron-source')
  	```

## Manual Setup

### IronSource iOS SDK

#### For projects with CocoaPods

Do nothing.

#### For other projects

1. Download the iOS SDK from [Ironsrc.com](http://developers.ironsrc.com/ironsource-mobile/ios/ios-sdk/)
2. Unzip and rename the directory to `IronSourceSDK`
3. Copy the SDK to `~/Documents/IronSourceSDK`
4. Drag the `IronSource.framework` to your react native target build phases from the `~/Documents/IronSourceSDK` directory
5. Add `~/Documents/IronSourceSDK` to your target's Framework Search Paths in Build Settings


### Android
Add a repo to your `app/build.gradle` file 
```
repositories {
    maven { url "https://dl.bintray.com/ironsource-mobile/android-sdk" }
}
```

## Usage

### Initialization

First initialize IronSource SDK

```javascript
import { IronSource } from 'react-native-iron-source';

IronSource.initializeIronSource('12345678', 'userId');
```

### Interstitial

```javascript
import { IronSourceInterstitials } from 'react-native-iron-source';

IronSourceInterstitials.loadInterstitial();
IronSourceInterstitials.addEventListener('interstitialDidLoad', () => {
  IronSourceInterstitials.showInterstitial();
});
```
### Rewarded Video

```javascript
import { IronSourceRewardedVideo } from 'react-native-iron-source';

IronSourceRewardedVideo.initializeRewardedVideo();
IronSourceRewardedVideo.showRewardedVideo();
IronSourceRewardedVideo.addEventListener('ironSourceRewardedVideoAdRewarded', res => {
  console.warn('Rewarded!', res)
});
```
### Banner

```javascript
import { IronSourceBanner } from 'react-native-iron-source';

IronSourceBanner.initializeBanner();
IronSourceBanner.loadBanner('LARGE');
IronSourceBanner.addEventListener('ironSourceBannerDidLoad', () => {
  console.warn('Iron Source banner loaded');
  IronSourceBanner.showBanner();
});
```
### Offerwall

```javascript
import { IronSourceOfferwall } from 'react-native-iron-source';

IronSourceOfferwall.initializeOfferwall();
IronSourceOfferwall.showOfferwall();
IronSourceOfferwall.addEventListener('ironSourceOfferwallReceivedCredits', res => {
  console.warn('Got credits', res)
});
```

## Events

### Banner events
| Name | Description |
| ---- | ----------- |
| ironSourceBannerDidLoad | Called after a banner ad has been successfully loaded |
| ironSourceBannerDidFailToLoadWithError | Called after a banner has attempted to load an ad but failed |
| ironSourceBannerDidDismissScreen | Called after a full screen content has been dismissed |
| ironSourceBannerWillLeaveApplication | Called when a user would be taken out of the application context |
| ironSourceBannerWillPresentScreen | Called when a banner is about to present a full screen content |
| ironSourceDidClickBanner | Called after a banner has been clicked |

### Rewarded video events
| Name | Description |
| ---- | ----------- |
| ironSourceRewardedVideoAvailable | Called after a rewarded video has changed its availability to YES. It means that now you can show the ad |
| ironSourceRewardedVideoUnavailable | Called after a rewarded video has changed its availability to NO |
| ironSourceRewardedVideoClosedByUser | Called after a rewarded video has been dismissed |
| ironSourceRewardedVideoDidStart | Called after a rewarded video has started playing. Note: this event is not available for all supported rewarded video ad networks. Check which events are available per ad network you choose |
| ironSourceRewardedVideoClosedByError | Called after a rewarded video has attempted to show but failed |
| ironSourceRewardedVideoDidOpen | Called after a rewarded video has been opened |
| ironSourceRewardedVideoAdStarted | Called after a rewarded video has been opened |
| ironSourceRewardedVideoAdEnded | Called after a rewarded video has finished playing. Note: this event is not available for all supported rewarded video ad networks. Check which events are available per ad network you choose |
| ironSourceRewardedVideoAdRewarded | Invoked when the user completed the video and should be rewarded |

### Interstitial events
| Name | Description |
| ---- | ----------- |
| interstitialDidLoad | Invoked when Interstitial Ad is ready to be shown after load function was called |
| interstitialDidShow | Called each time the Interstitial window has opened successfully |
| interstitialDidFailToShowWithError | Called if showing the Interstitial for the user has failed |
| didClickInterstitial | Called each time the end user has clicked on the Interstitial ad |
| interstitialDidClose | Called each time the Interstitial window is about to close |
| interstitialDidOpen | Called each time the Interstitial window is about to open |
| interstitialDidFailToLoadWithError | Invoked when there is no Interstitial Ad available after calling load function |


### Offerwall events
| Name | Description |
| ---- | ----------- |
| ironSourceOfferwallAvailable | Invoked when there is a change in the Offerwall availability status to YES |
| ironSourceOfferwallUnavailable | Invoked when there is a change in the Offerwall availability status to NO |
| ironSourceOfferwallDidShow | Called each time the Offerwall successfully loads for the user |
| ironSourceOfferwallClosedByUser | Called when the user closes the Offerwall |
| ironSourceOfferwallClosedByError | Called each time the Offerwall fails to show |
| ironSourceOfferwallReceivedCredits | Called each time the user completes an offer |
| ironSourceOfferwallFailedToReceiveCreditsByError | Called when failed to retrieve the users credit balance info |

You can find out more about events in the official doc. Start [here](https://developers.ironsrc.com/ironsource-mobile/ios/rewarded-video-integration-ios/) if you wish.

## Mediation Setup

**WARNING!** Make sure you following the doc carefully. If you miss something in the mediation setup process for some network it may not work partially or entirely.

Official doc:
- [Android](https://developers.ironsrc.com/ironsource-mobile/android/mediation-networks-android/#step-1).
- [iOS](https://developers.ironsrc.com/ironsource-mobile/ios/mediation-networks-ios/#step-1).

You can optionally use this syntax to add iOS mediation adapters instead of suggested by the doc.
```
pod 'RNIronSource', :path => '../node_modules/@wowmaking/react-native-iron-source', :subspecs => [
    'Core', # required
    'AdColony',
    'Admob',
    'AppLovin',
    'Chartboost',
    'Facebook',
    'HyprMX',
    'InMobi',
    'Maio',
    'MediaBrix',
    'MoPub',
    'Tapjoy',
    'UnityAds',
    'Vungle'
]
```

## Known issues
Ads may stop loading properly when "Reload" option (or CMD+R) in your React Native app was used. You have to restart the app completely if you want to check that ads load and display correctly.

## API

### IronSourceBanner.loadBanner(options)
Loads IronSource banner

#### Parameter(s)
* **options:** Object (optional)
    * **scaleToFitWidth:** Boolean
#### Returns Promise of
* **response:** Object
    * **width:** Number
    * **height:** Number

```javascript
IronSourceBanner.loadBanner()
  .then((response) => {
    console.warn(`width: ${response.width}, height: ${response.height}`);
  })
  .catch(err => {
    console.warn(err.message);
  });
```