/**
 * Sample React Native App using React Native Iron Source
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


import {IronSourceRewardedVideo} from 'react-native-iron-source'

export default class ExampleApp extends Component {
  constructor(props) {
    super(props)
    this.state = {
      rewardedVideoState: 'request'
    }
  }

  componentWillMount() {
    IronSourceRewardedVideo.initializeRewardedVideo("app key", "user id")
  }
  
  componentDidMount() {
    // Rewarded Video
    IronSourceRewardedVideo.addEventListener('rewardedVideoInitialized',
      () => {
        console.log('rewardedVideoInitialized ----');
        this.setState({rewardedVideoState: 'initialized'})
      });

    IronSourceRewardedVideo.addEventListener('rewardedVideoInitializationFailed',
      () => {
        console.log('rewardedVideoInitializationFailed event');
        this.setState({rewardedVideoState: 'error'})
      });

    IronSourceRewardedVideo.addEventListener('rewardedVideoAvailabilityStatus',
      (hasAvailableVideo) => {
        console.log('rewardedVideoAvailabilityStatus ----', hasAvailableVideo);
        if (hasAvailableVideo) {
          this.setState({rewardedVideoState: 'ready'})
        }
      });

    IronSourceRewardedVideo.addEventListener('rewardedVideoDidStart',
      () => {
        console.log('rewardedVideoDidStart event');
        this.props.actions.rewardedVideoViewedSuccess();
        this.setState({rewardedVideoState: 'request'})
      });

    IronSourceRewardedVideo.addEventListener('rewardedVideoClosedByUser',
      () => {
        console.log('rewardedVideoClosedByUser event');
        this.setState({rewardedVideoState: 'request'})
      });

    IronSourceRewardedVideo.addEventListener('rewardedVideoClosedByError',
      () => {
        console.log('rewardedVideoClosedByError event');
        this.setState({rewardedVideoState: 'error'})
      });
  }

  componentWillUnmount() {
    IronSourceRewardedVideo.removeAllListeners();
  }


  showRewardedVideo() {
    IronSourceRewardedVideo.showRewardedVideo()
  }

  render() {
    let showAdButton = <Text style={styles.instructions}>
      Loading Ad...
    </Text>;

    if (this.state.rewardedVideoState == 'ready') {
      showAdButton = <TouchableOpacity onPress={() => this.showRewardedVideo()} >
        <Text style={styles.instructions}>
          Show Ad
        </Text>
      </TouchableOpacity>

    }

    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          React Native Iron Source!
        </Text>
        {showAdButton}
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
