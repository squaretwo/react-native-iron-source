
# react-native-iron-source

Iron Source SDK React Native bridge

## Getting started

It is recommended to use version tags. Example:

`npm install https://github.com/wowmaking/react-native-iron-source\#v2.5.0 --save`

You can find available version tags [here](https://github.com/wowmaking/react-native-iron-source/releases).

### Mostly automatic installation

`$ react-native link react-native-iron-source`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-iron-source` and add `RNIronSource.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNIronSource.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)

#### iOS CocoaPods
1. Add `pod 'RNIronSource', :path => '../node_modules/react-native-iron-source'` to your `ios/Podfile`
2. Run `pod install` while in `ios` directory

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNIronSourcePackage;` to the imports at the top of the file
  - Add `new RNIronSourcePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-iron-source'
  	project(':react-native-iron-source').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-iron-source/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-iron-source')
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
- ironSourceBannerDidFailToLoadWithError
- ironSourceBannerDidDismissScreen
- ironSourceBannerWillLeaveApplication
- ironSourceBannerWillPresentScreen
- ironSourceDidClickBanner

### Rewarded video events
- ironSourceRewardedVideoAvailable
- ironSourceRewardedVideoUnavailable
- ironSourceRewardedVideoDidOpen
- ironSourceRewardedVideoDidStart
- ironSourceRewardedVideoClosedByUser
- ironSourceRewardedVideoClosedByError
- ironSourceRewardedVideoAdStarted
- ironSourceRewardedVideoAdEnded
- ironSourceRewardedVideoAdRewarded

### Interstitial events
- interstitialDidLoad
- interstitialDidShow
- interstitialDidFailToShowWithError
- didClickInterstitial
- interstitialDidClose
- interstitialDidOpen
- interstitialDidFailToLoadWithError

#### Offerwall events
- ironSourceOfferwallAvailable
- ironSourceOfferwallUnavailable
- ironSourceOfferwallDidShow
- ironSourceOfferwallClosedByUser
- ironSourceOfferwallClosedByError
- ironSourceOfferwallReceivedCredits
- ironSourceOfferwallFailedToReceiveCreditsByError

## Mediation Setup

WARNING! Make sure you following the doc carefully. If you miss something in the mediation setup process for some network it may not work partially or entirely.

Official doc:
- [Android](https://developers.ironsrc.com/ironsource-mobile/android/mediation-networks-android/#step-1).
- [iOS](https://developers.ironsrc.com/ironsource-mobile/ios/mediation-networks-ios/#step-1).

You can optionally use this syntax to add iOS mediation adapters instead of suggested by the doc.
```
pod 'RNIronSource', :path => '../node_modules/react-native-iron-source', :subspecs => [
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

### Known issues
Ads may stop loading properly when "Reload" option in your React Native app is used (or CMD+R). You have to restart the app completely if you want to check that ads load and display correctly.
