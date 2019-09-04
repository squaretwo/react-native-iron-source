import React, {Component} from 'react';
import {StyleSheet, Text, View, TouchableOpacity} from 'react-native';
import {
  IronSource,
  IronSourceSegment,
  IronSourceRewardedVideo,
  IronSourceInterstitials,
  IronSourceOfferwall,
  IronSourceBanner,
} from '@wowmaking/react-native-iron-source';

const segment = new IronSourceSegment();

export default class App extends Component {

  state = {
    hasRewardedVideo: false,
  };

  componentDidMount() {
    // It’s recommended to set consent prior to SDK Initialization.
    IronSource.setConsent(true);

    segment.setCustomValue('VALUE').forKey('KEY');
    segment.setSegmentName('NAME');
    segment.activate();

    IronSource.initializeIronSource('8a19a09d', 'demoapp', {
      validateIntegration: false,
    }).then(() => {
      IronSourceRewardedVideo.addEventListener('ironSourceRewardedVideoAvailable', () => {
        this.setState({ hasRewardedVideo: true });
        console.warn('Rewarded video became available');
      });
      IronSourceRewardedVideo.addEventListener('ironSourceRewardedVideoUnavailable', () => {
        this.setState({ hasRewardedVideo: false });
      });
      IronSourceRewardedVideo.initializeRewardedVideo();

      IronSourceOfferwall.initializeOfferwall();
    });
  }

  showRewardedVideo = () => {
    if (!this.state.hasRewardedVideo) {
      console.warn('Rewarded video is not available');
    }

    const onClose = () => IronSourceRewardedVideo.removeAllListeners();

    IronSourceRewardedVideo.addEventListener('ironSourceRewardedVideoAdRewarded', res => {
      console.warn('Rewarded!', res);
    });

    IronSourceRewardedVideo.addEventListener('ironSourceRewardedVideoClosedByUser', onClose);
    IronSourceRewardedVideo.addEventListener('ironSourceRewardedVideoClosedByError', onClose);

    IronSourceRewardedVideo.showRewardedVideo();
  };


  showInterstitial = () => {
    const onClose = () => IronSourceInterstitials.removeAllListeners();

    IronSourceInterstitials.addEventListener('interstitialDidLoad', () => {
      IronSourceInterstitials.showInterstitial();
    });
    IronSourceInterstitials.addEventListener('interstitialDidFailToLoadWithError', (err) => {
      console.warn('Failed to load inter', err);
      onClose();
    });
    IronSourceInterstitials.addEventListener('interstitialDidFailToShowWithError', (err) => {
      console.warn('Failed to show inter', err);
      onClose();
    });
    IronSourceInterstitials.addEventListener('interstitialDidClose', () => {
      onClose();
    });

    IronSourceInterstitials.loadInterstitial();
  };

  showOfferwall = () => {
    IronSourceOfferwall.showOfferwall();
    IronSourceOfferwall.addEventListener('ironSourceOfferwallReceivedCredits', res => {
      console.warn('Got credits', res)
    });
  };

  loadBanner = () => {
    IronSourceBanner.loadBanner('BANNER', {
      position: 'top',
      scaleToFitWidth: true,
    }).then(res => {
      console.warn(res);
      IronSourceBanner.showBanner();
    }).catch(err => {
      console.warn(err.message);
    });
  };

  loadLargeBanner = () => {
    IronSourceBanner.loadBanner('LARGE', {
      position: 'top',
      scaleToFitWidth: true,
    }).then(res => {
      console.warn(res);
      IronSourceBanner.showBanner();
    }).catch(err => {
      console.warn(err.message);
    });
  };

  loadRectangleBanner = () => {
    IronSourceBanner.loadBanner('RECTANGLE', {
      position: 'top',
      scaleToFitWidth: true,
    }).then(res => {
      console.warn(res);
      IronSourceBanner.showBanner();
    }).catch(err => {
      console.warn(err.message);
    });
  };

  loadSmartBanner = () => {
    IronSourceBanner.loadBanner('SMART', {
      position: 'top',
      scaleToFitWidth: true,
    }).then(res => {
      console.warn(res);
      IronSourceBanner.showBanner();
    }).catch(err => {
      console.warn(err.message);
    });
  };

  destroyBanner = () => {
    IronSourceBanner.destroyBanner();
  };

  grantConsent = () => {
    IronSource.setConsent(true);
  };

  withdrawConsent = () => {
    IronSource.setConsent(false);
  };

  setUserProperty = () => {
    segment.setCustomValue('VALUE').forKey('KEY');
  };

  setSegmentName = () => {
    segment.setSegmentName('NAME');
  };

  activateSegment = () => {
    segment.activate();
  };

  render() {
    return (
      <View style={styles.container}>
        <TouchableOpacity onPress={this.showRewardedVideo}>
          <Text style={styles.button}>Show Rewarded Video</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.showInterstitial}>
          <Text style={styles.button}>Show Interstitial</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.showOfferwall}>
          <Text style={styles.button}>Show Offerwall</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.loadBanner}>
          <Text style={styles.button}>Load Banner</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.loadLargeBanner}>
          <Text style={styles.button}>Load Large Banner</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.loadRectangleBanner}>
          <Text style={styles.button}>Load Rectangle Banner</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.loadSmartBanner}>
          <Text style={styles.button}>Load Smart Banner</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.destroyBanner}>
          <Text style={styles.button}>Destroy Banner</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.showRewardedVideo}>
          <Text style={styles.button}>Show Rewarded Video</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.grantConsent}>
          <Text style={styles.button}>Grant Consent</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.withdrawConsent}>
          <Text style={styles.button}>Withdraw Consent</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.setSegmentName}>
          <Text style={styles.button}>Set Segment Name</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.setUserProperty}>
          <Text style={styles.button}>Set User Property</Text>
        </TouchableOpacity>
        <TouchableOpacity onPress={this.activateSegment}>
          <Text style={styles.button}>Activate Segment</Text>
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
  button: {
    alignSelf: 'center',
    fontSize: 21,
    backgroundColor: '#face8d',
    margin: 7,
  },
  bannerContainer: {
    borderColor: 'red',
    borderWidth: 1,
    width: '90%',
  },
  banner: {
    borderWidth: 1,
    height: 150,
  },
});
