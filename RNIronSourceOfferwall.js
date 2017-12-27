'use strict';

import {
  NativeModules,
  DeviceEventEmitter,
} from 'react-native';

const RNIronSourceOfferwall = NativeModules.RNIronSourceOfferwall;

const eventHandlers = {
  ironSourceOfferwallAvailable: new Map(),//
  ironSourceOfferwallUnavailable: new Map(),//
  ironSourceOfferwallDidShow: new Map(),//
  ironSourceOfferwallClosedByUser: new Map(),//
  ironSourceOfferwallClosedByError: new Map(),//
  ironSourceOfferwallReceivedCredits:new Map(),
  ironSourceOfferwallFailedToReceiveCreditsByError:new Map()//
};

const addEventListener = (type, handler) => {
  switch (type) {
    case 'ironSourceOfferwallOfferwallAvailable':
    case 'ironSourceOfferwallUnavailable':
    case 'ironSourceOfferwallDidShow':
    case 'ironSourceOfferwallClosedByUser':
    case 'ironSourceOfferwallClosedByError':
    case 'ironSourceOfferwallReceivedCredits':
    case 'ironSourceOfferwallFailedToReceiveCreditsByError':
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
  DeviceEventEmitter.removeAllListeners('ironSourceOfferwallAvailable');
  DeviceEventEmitter.removeAllListeners('ironSourceOfferwallUnavailable');
  DeviceEventEmitter.removeAllListeners('ironSourceOfferwallDidShow');
  DeviceEventEmitter.removeAllListeners('ironSourceOfferwallClosedByUser');
  DeviceEventEmitter.removeAllListeners('ironSourceOfferwallClosedByError');
  DeviceEventEmitter.removeAllListeners('ironSourceOfferwallReceivedCredits');
  DeviceEventEmitter.removeAllListeners('ironSourceOfferwallFailedToReceiveCreditsByError');
};

module.exports = {
  ...RNIronSourceOfferwall,
  initializeOfferwall: () => RNIronSourceOfferwall.initializeOfferwall(),
  showOfferwall: () => RNIronSourceOfferwall.showOfferwall(),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
