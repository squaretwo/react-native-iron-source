require "json"
version = JSON.parse(File.read("package.json"))["version"]

Pod::Spec.new do |s|
  s.name         = "RNIronSource"
  s.version      = version
  s.summary      = "RNIronSource"
  s.description  = <<-DESC
                  Iron Source SDK React Native bridge
                   DESC
  s.homepage     = "https://github.com/squaretwo/react-native-iron-source"
  s.license      = "MIT"
  s.author       = { "Ben Yee" => "benyee@gmail.com" }
  s.platform     = :ios, "7.0"
  s.source       = { :git => "https://github.com/wowmaking/react-native-iron-source", tag: "v" + s.version.to_s }
  s.requires_arc = true
  s.default_subspec = 'Core'

  s.dependency "React"
  s.dependency "IronSourceSDK", "~> 6.8"

  s.subspec "Core" do |ss|
    ss.source_files  = "ios/**/*.{h,m}"
  end
  s.subspec "AdColony" do |ss|
    ss.dependency         "IronSourceAdColonyAdapter", "4.1.5.1"
  end
  s.subspec "Admob" do |ss|
    ss.dependency         "IronSourceAdMobAdapter", "4.3.4.1"
  end
  s.subspec "Amazon" do |ss|
    ss.dependency         "IronSourceAmazonAdapter", "4.3.0.1"
  end
  s.subspec "AppLovin" do |ss|
    ss.dependency         "IronSourceAppLovinAdapter", "4.3.3.2"
  end
  s.subspec "Chartboost" do |ss|
    ss.dependency         "IronSourceChartboostAdapter", "4.1.6.1"
  end
  s.subspec "Facebook" do |ss|
    ss.dependency         "IronSourceFacebookAdapter", "4.3.4.2"
  end
  s.subspec "HyprMX" do |ss|
    ss.dependency         "IronSourceHyprMXAdapter", "4.1.2.1"
  end
  s.subspec "InMobi" do |ss|
    ss.dependency         "IronSourceInMobiAdapter", "4.3.1.2"
  end
  s.subspec "Maio" do |ss|
    ss.dependency         "IronSourceMaioAdapter", "4.1.3.1"
  end
  s.subspec "Tapjoy" do |ss|
    ss.dependency         "IronSourceTapjoyAdapter", "4.1.4.0"
  end
  s.subspec "TikTok" do |ss|
    ss.dependency         "IronSourceTikTokAdapter", "4.1.0.1"
  end
  s.subspec "UnityAds" do |ss|
    ss.dependency         "IronSourceUnityAdsAdapter", "4.1.3.0"
  end
  s.subspec "Vungle" do |ss|
    ss.dependency         "IronSourceVungleAdapter", "4.1.6.2"
  end

end
