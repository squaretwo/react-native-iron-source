import { NativeModules, NativeEventEmitter } from 'react-native';

const RNIronSourceRewardedVideo = NativeModules.RNIronSourceRewardedVideo;
const IronSourceRewardedVideoEventEmitter = new NativeEventEmitter(RNIronSourceRewardedVideo);

const eventHandlers = {
  ironSourceRewardedVideoAvailable: new Map(),
  ironSourceRewardedVideoUnavailable: new Map(),
  ironSourceRewardedVideoDidOpen: new Map(),
  ironSourceRewardedVideoDidStart: new Map(),
  ironSourceRewardedVideoClosedByUser: new Map(),
  ironSourceRewardedVideoClosedByError: new Map(),
  ironSourceRewardedVideoAdStarted: new Map(),
  ironSourceRewardedVideoAdEnded: new Map(),
  ironSourceRewardedVideoAdRewarded:new Map()
};

const addEventListener = (type, handler) => {
  switch (type) {
    case 'ironSourceRewardedVideoAvailable':
    case 'ironSourceRewardedVideoUnavailable':
    case 'ironSourceRewardedVideoDidOpen':
    case 'ironSourceRewardedVideoDidStart':
    case 'ironSourceRewardedVideoClosedByError':
    case 'ironSourceRewardedVideoAdStarted':
    case 'ironSourceRewardedVideoAdEnded':
    case 'ironSourceRewardedVideoAdRewarded':
      eventHandlers[type].set(handler, IronSourceRewardedVideoEventEmitter.addListener(type, handler));
      break;
    case 'ironSourceRewardedVideoClosedByUser':
      eventHandlers[type].set(handler, IronSourceRewardedVideoEventEmitter.addListener(type, () => {
        // This is a dirty hack that is required by some Ad Networks (Vungle, UnityAds)
        // It makes 'ironSourceRewardedVideoClosedByUser' and 'ironSourceRewardedVideoAdRewarded'
        // events order match with all other networks
        setTimeout(handler);
      }));
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
  IronSourceRewardedVideoEventEmitter.removeAllListeners('ironSourceRewardedVideoAvailable');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('ironSourceRewardedVideoUnavailable');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('ironSourceRewardedVideoDidOpen');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('ironSourceRewardedVideoDidStart');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('ironSourceRewardedVideoClosedByUser');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('ironSourceRewardedVideoClosedByError');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('ironSourceRewardedVideoAdStarted');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('ironSourceRewardedVideoAdEnded');
  IronSourceRewardedVideoEventEmitter.removeAllListeners('ironSourceRewardedVideoAdRewarded');
};

isRewardedVideoAvailable = async () => {
  const timeoutTime = 5000;
  setTimeout(() => Promise.reject(new Error(`Timeout isRewardedVideoAvailable: timeout: ${timeoutTime}`))
    , timeoutTime);
  return RNIronSourceRewardedVideo.isRewardedVideoAvailable();
}

module.exports = {
  ...RNIronSourceRewardedVideo,
  initializeRewardedVideo: () => RNIronSourceRewardedVideo.initializeRewardedVideo(),
  showRewardedVideo: (placementName) => RNIronSourceRewardedVideo.showRewardedVideo(placementName),
  isRewardedVideoAvailable: () => RNIronSourceRewardedVideo.isRewardedVideoAvailable(),
  setDynamicUserId: (userId) => RNIronSourceRewardedVideo.setDynamicUserId(userId),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
