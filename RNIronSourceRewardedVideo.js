'use strict';

import {
  NativeModules,
  DeviceEventEmitter,
} from 'react-native';

const RNIronSourceRewardedVideo = NativeModules.RNIronSourceRewardedVideo;

const eventHandlers = {
  ironSourceRewardedVideoAvailable: new Map(),
  ironSourceRewardedVideoUnavailable: new Map(),
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
    case 'ironSourceRewardedVideoDidStart':
    case 'ironSourceRewardedVideoClosedByUser':
    case 'ironSourceRewardedVideoClosedByError':
    case 'ironSourceRewardedVideoAdStarted':
    case 'ironSourceRewardedVideoAdEnded':
    case 'ironSourceRewardedVideoAdRewarded':
      eventHandlers[type].set(handler, DeviceEventEmitter.addListener(type, handler));
      break;
    default:
      console.log(`Event with type ${type} does not exist.`);
  }
}

const removeEventListener = (type, handler) => {
  if (!eventHandlers[type].has(handler)) {
    return;
  }
  eventHandlers[type].get(handler).remove();
  eventHandlers[type].delete(handler);
}

const removeAllListeners = () => {
  DeviceEventEmitter.removeAllListeners('ironSourceRewardedVideoAvailable');
  DeviceEventEmitter.removeAllListeners('ironSourceRewardedVideoUnavailable');
  DeviceEventEmitter.removeAllListeners('ironSourceRewardedVideoDidStart');
  DeviceEventEmitter.removeAllListeners('ironSourceRewardedVideoClosedByUser');
  DeviceEventEmitter.removeAllListeners('ironSourceRewardedVideoClosedByError');
  DeviceEventEmitter.removeAllListeners('ironSourceRewardedVideoAdStarted');
  DeviceEventEmitter.removeAllListeners('ironSourceRewardedVideoAdEnded');
  DeviceEventEmitter.removeAllListeners('ironSourceRewardedVideoAdRewarded');
};

module.exports = {
  ...RNIronSourceRewardedVideo,
  initializeRewardedVideo: (key, userId) => RNIronSourceRewardedVideo.initializeRewardedVideo(key, userId),
  showRewardedVideo: () => RNIronSourceRewardedVideo.showRewardedVideo(),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
