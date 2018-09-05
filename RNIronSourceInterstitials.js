import { NativeModules, NativeEventEmitter } from 'react-native';

const RNIronSourceInterstitials = NativeModules.RNIronSourceInterstitials;
const IronSourceInterstialsEventEmitter = new NativeEventEmitter(RNIronSourceInterstitials);

const eventHandlers = {
  interstitialDidLoad: new Map(),
  interstitialDidShow: new Map(),
  interstitialDidFailToShowWithError: new Map(),
  didClickInterstitial: new Map(),
  interstitialDidClose: new Map(),
  interstitialDidOpen: new Map(),
  interstitialDidFailToLoadWithError: new Map()
};

const addEventListener = (type, handler) => {
  switch (type) {
    case 'interstitialDidLoad':
    case 'interstitialDidShow':
    case 'interstitialDidFailToShowWithError':
    case 'didClickInterstitial':
    case 'interstitialDidClose':
    case 'interstitialDidOpen':
    case 'interstitialDidFailToLoadWithError':
      eventHandlers[type].set(handler, IronSourceInterstialsEventEmitter.addListener(type, handler));
      break;
    default:
      console.log(`Event with type ${type} does not exist.`);
  }
};

const removeEventListener = (type, handler) => {
  if (!eventHandlers[type].has(handler)) {
    return;
  }
  eventHandlers[type].get(handler).remove();
  eventHandlers[type].delete(handler);
};

const removeAllListeners = () => {
  IronSourceRewardedVideoEventEmitter.removeAllListeners('interstitialDidLoad');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('interstitialDidShow');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('interstitialDidFailToShowWithError');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('didClickInterstitial');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('interstitialDidClose');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('interstitialDidOpen');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('interstitialDidFailToLoadWithError');
};

module.exports = {
  ...RNIronSourceInterstitials,
  loadInterstitial: () => RNIronSourceInterstitials.loadInterstitial(),
  showInterstitial: (placementName) => RNIronSourceInterstitials.showInterstitial(placementName),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
