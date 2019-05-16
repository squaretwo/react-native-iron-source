import { NativeModules, NativeEventEmitter } from 'react-native';

const RNIronSourceBanner = NativeModules.RNIronSourceBanner;
const IronSourceBannerEventEmitter = new NativeEventEmitter(RNIronSourceBanner);

const supportedEvents = [
  'ironSourceBannerDidLoad',
  'ironSourceBannerDidFailToLoadWithError',
  'ironSourceBannerDidDismissScreen',
  'ironSourceBannerWillLeaveApplication',
  'ironSourceBannerWillPresentScreen',
  'ironSourceDidClickBanner',
]

const loadBannerDefaultOptions = {
  scaleToFitWidth: false,
};

const eventHandlers = supportedEvents.reduce((acc, eventName) => {
  acc[eventName] = new Map();
  return acc;
}, {});

const addEventListener = (type, handler) => {
  if (supportedEvents.includes(type)) {
    eventHandlers[type].set(handler, IronSourceBannerEventEmitter.addListener(type, handler));
  } else {
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
  supportedEvents.map(IronSourceBannerEventEmitter.removeAllListeners);
};

module.exports = {
  ...RNIronSourceBanner,
  initializeBanner: () => RNIronSourceBanner.initializeBanner(),
  loadBanner: (size = 'BANNER', options) => RNIronSourceBanner.loadBanner(size, {
    ...loadBannerDefaultOptions,
    ...options,
  }),
  showBanner: () => RNIronSourceBanner.showBanner(),
  hideBanner: () => RNIronSourceBanner.hideBanner(),
  getActualBannerSize: () => RNIronSourceBanner.getActualBannerSize(),
  destroyBanner: () => RNIronSourceBanner.destroyBanner(),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
