/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  Text,
  View,
  TouchableOpacity
} from 'react-native';


import IronSourceRewardedVideo from 'react-native-iron-source'

export default class ExampleApp extends Component {

  componentWillMount() {
    IronSourceRewardedVideo.initializeAdsForUser("YOUR_APPKEY", "123")
  }

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          React Native Iron Source!
        </Text>
        <TouchableOpacity onPress={() => IronSourceRewardedVideo.showAd()} >
          <Text style={styles.instructions}>
            Show Ad
          </Text>
        </TouchableOpacity>


      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('ExampleApp', () => ExampleApp);
