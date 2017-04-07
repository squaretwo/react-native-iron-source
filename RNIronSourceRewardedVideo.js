'use strict';

import {
  NativeModules,
  DeviceEventEmitter,
} from 'react-native';

const RNIronSourceRewardedVideo = NativeModules.RNIronSourceRewardedVideo;

const eventHandlers = {
  rewardedVideoAvailable: new Map(),
  rewardedVideoUnavailable: new Map(),
  rewardedVideoDidStart: new Map(),
  rewardedVideoClosedByUser: new Map(),
  rewardedVideoClosedByError: new Map(),
  rewardedVideoAdStarted: new Map(),
  rewardedVideoAdEnded: new Map(),
  rewardedVideoAdRewarded:new Map()
};

const addEventListener = (type, handler) => {
  switch (type) {
    case 'rewardedVideoAvailable':
    case 'rewardedVideoUnavailable':
    case 'rewardedVideoDidStart':
    case 'rewardedVideoClosedByUser':
    case 'rewardedVideoClosedByError':
    case 'rewardedVideoAdStarted':
    case 'rewardedVideoAdEnded':
    case 'rewardedVideoAdRewarded':
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
  DeviceEventEmitter.removeAllListeners('rewardedVideoAvailable');
  DeviceEventEmitter.removeAllListeners('rewardedVideoUnavailable');
  DeviceEventEmitter.removeAllListeners('rewardedVideoDidStart');
  DeviceEventEmitter.removeAllListeners('rewardedVideoClosedByUser');
  DeviceEventEmitter.removeAllListeners('rewardedVideoClosedByError');
  DeviceEventEmitter.removeAllListeners('rewardedVideoAdStarted');
  DeviceEventEmitter.removeAllListeners('rewardedVideoAdEnded');
  DeviceEventEmitter.removeAllListeners('rewardedVideoAdRewarded');
};

module.exports = {
  ...RNIronSourceRewardedVideo,
  initializeRewardedVideo: (key, userId) => RNIronSourceRewardedVideo.initializeRewardedVideo(key, userId),
  showRewardedVideo: () => RNIronSourceRewardedVideo.showRewardedVideo(),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
