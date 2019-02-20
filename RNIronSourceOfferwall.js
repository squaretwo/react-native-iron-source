import { NativeModules, NativeEventEmitter } from 'react-native';

const RNIronSourceOfferwall = NativeModules.RNIronSourceOfferwall;

const IronSourceOfferwallEventEmitter = new NativeEventEmitter(RNIronSourceOfferwall);

const eventHandlers = {
  ironSourceOfferwallAvailable: new Map(),
  ironSourceOfferwallUnavailable: new Map(),
  ironSourceOfferwallDidShow: new Map(),
  ironSourceOfferwallClosedByUser: new Map(),
  ironSourceOfferwallClosedByError: new Map(),
  ironSourceOfferwallReceivedCredits:new Map(),
  ironSourceOfferwallFailedToReceiveCreditsByError:new Map()
};

const addEventListener = (type, handler) => {
  switch (type) {
    case 'ironSourceOfferwallAvailable':
    case 'ironSourceOfferwallUnavailable':
    case 'ironSourceOfferwallDidShow':
    case 'ironSourceOfferwallClosedByUser':
    case 'ironSourceOfferwallClosedByError':
    case 'ironSourceOfferwallReceivedCredits':
    case 'ironSourceOfferwallFailedToReceiveCreditsByError':
      eventHandlers[type].set(handler, IronSourceOfferwallEventEmitter.addListener(type, handler));
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
  IronSourceOfferwallEventEmitter.removeAllListeners('ironSourceOfferwallAvailable');
  IronSourceOfferwallEventEmitter.removeAllListeners('ironSourceOfferwallUnavailable');
  IronSourceOfferwallEventEmitter.removeAllListeners('ironSourceOfferwallDidShow');
  IronSourceOfferwallEventEmitter.removeAllListeners('ironSourceOfferwallClosedByUser');
  IronSourceOfferwallEventEmitter.removeAllListeners('ironSourceOfferwallClosedByError');
  IronSourceOfferwallEventEmitter.removeAllListeners('ironSourceOfferwallReceivedCredits');
  IronSourceOfferwallEventEmitter.removeAllListeners('ironSourceOfferwallFailedToReceiveCreditsByError');
};

module.exports = {
  ...RNIronSourceOfferwall,
  initializeOfferwall: () => RNIronSourceOfferwall.initializeOfferwall(),
  showOfferwall: () => RNIronSourceOfferwall.showOfferwall(),
  addEventListener,
  removeEventListener,
  removeAllListeners
};
