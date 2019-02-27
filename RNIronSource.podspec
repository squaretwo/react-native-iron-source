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
  s.source       = { :git => "https://github.com/wowmaking/react-native-iron-source" }
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency "IronSourceSDK", "6.8.1.0"

  s.subspec "Core" do |ss|
    ss.source_files  = "ios/**/*.{h,m}"
  end
  s.subspec "AdColony" do |ss|
    ss.dependency         "IronSourceAdColonyAdapter"
  end
  s.subspec "Admob" do |ss|
    ss.dependency         "IronSourceAdMobAdapter"
  end
  s.subspec "AppLoving" do |ss|
    ss.dependency         "IronSourceAppLovinAdapter"
  end
  s.subspec "Chartboost" do |ss|
    ss.dependency         "IronSourceChartboostAdapter"
  end
  s.subspec "Facebook" do |ss|
    ss.dependency         "IronSourceFacebookAdapter"
  end
  s.subspec "HyprMX" do |ss|
    ss.dependency         "IronSourceHyprMXAdapter"
  end
  s.subspec "InMobi" do |ss|
    ss.dependency         "IronSourceInMobiAdapter"
  end
  s.subspec "Maio" do |ss|
    ss.dependency         "IronSourceMaioAdapter"
  end
  s.subspec "MediaBrix" do |ss|
    ss.dependency         "IronSourceMediaBrixAdapter"
  end
  s.subspec "MoPub" do |ss|
    ss.dependency         "IronSourceMoPubAdapter"
  end
  s.subspec "Tapjoy" do |ss|
    ss.dependency         "IronSourceTapjoyAdapter"
  end
  s.subspec "UnityAds" do |ss|
    ss.dependency         "IronSourceUnityAdsAdapter"
  end
  s.subspec "Vungle" do |ss|
    ss.dependency         "IronSourceVungleAdapter"
  end

end
