import { NativeModules } from 'react-native';

const RNIronSource = NativeModules.RNIronSource;

const defaultOptions = {
  validateIntegration: false,
};

module.exports = {
  ...RNIronSource,
  initializeIronSource: (key, userId, options) => RNIronSource.initializeIronSource(key, userId, {
    ...defaultOptions,
    ...options,
  }),
};
