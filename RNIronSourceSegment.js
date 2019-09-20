import { NativeModules } from 'react-native';

const RNIronSourceSegment = NativeModules.RNIronSourceSegment;

export default class IronSourceSegment {
  constructor() {
    return RNIronSourceSegment.create();
  }

  setSegmentName(name) {
    return RNIronSourceSegment.setSegmentName(name);
  }

  setCustomValue(value) {
    return {
      forKey(key) {
        return RNIronSourceSegment.setCustomValue(value, key);
      }
    }
  }

  activate() {
    return RNIronSourceSegment.activate();
  }

}