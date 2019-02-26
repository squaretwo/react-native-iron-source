
# react-native-iron-source

Iron Source SDK React Native bridge

## Getting started

`$ npm install react-native-iron-source --save`

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

## IronSource iOS SDK

### For projects with CocoaPods

Do nothing.

### For other project

1. Download the iOS SDK from [Ironsrc.com](http://developers.ironsrc.com/ironsource-mobile/ios/ios-sdk/)
2. Unzip and rename the directory to `IronSourceSDK`
3. Copy the SDK to `~/Documents/IronSourceSDK`
4. Drag the `IronSource.framework` to your react native target build phases from the `~/Documents/IronSourceSDK` directory
5. Add `~/Documents/IronSourceSDK` to your target's Framework Search Paths in Build Settings


## Usage

### Initialization

First initialize IronSource SDK

```javascript
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