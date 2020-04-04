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
  s.dependency "IronSourceSDK", "~> 6"

  s.subspec "Core" do |ss|
    ss.source_files  = "ios/**/*.{h,m}"
  end
  s.subspec "AdColony" do |ss|
    ss.dependency         "IronSourceAdColonyAdapter", "> 1"
  end
  s.subspec "Admob" do |ss|
    ss.dependency         "IronSourceAdMobAdapter", "> 1"
  end
  s.subspec "Amazon" do |ss|
    ss.dependency         "IronSourceAmazonAdapter", "> 1"
  end
  s.subspec "AppLovin" do |ss|
    ss.dependency         "IronSourceAppLovinAdapter", "> 1"
  end
  s.subspec "Chartboost" do |ss|
    ss.dependency         "IronSourceChartboostAdapter", "> 1"
  end
  s.subspec "Facebook" do |ss|
    ss.dependency         "IronSourceFacebookAdapter", "> 1"
  end
  s.subspec "Fyber" do |ss|
    ss.dependency         "IronSourceFyberAdapter", "> 1"
  end
  s.subspec "HyprMX" do |ss|
    ss.dependency         "IronSourceHyprMXAdapter", "> 1"
  end
  s.subspec "InMobi" do |ss|
    ss.dependency         "IronSourceInMobiAdapter", "> 1"
  end
  s.subspec "Maio" do |ss|
    ss.dependency         "IronSourceMaioAdapter", "> 1"
  end
  s.subspec "Mintegral" do |ss|
    ss.dependency         "IronSourceMintegralAdapter", "> 1"
  end
  s.subspec "Tapjoy" do |ss|
    ss.dependency         "IronSourceTapjoyAdapter", "> 1"
  end
  s.subspec "TikTok" do |ss|
    ss.dependency         "IronSourceTikTokAdapter", "> 1"
  end
  s.subspec "UnityAds" do |ss|
    ss.dependency         "IronSourceUnityAdsAdapter", "> 1"
  end
  s.subspec "Vungle" do |ss|
    ss.dependency         "IronSourceVungleAdapter", "> 1"
  end

  if defined?($RNIronSourceAsStaticFramework)
    Pod::UI.puts "#{s.name}: Using overridden static_framework value of '#{$RNIronSourceAsStaticFramework}'"
    s.static_framework = $RNIronSourceAsStaticFramework
  else
    s.static_framework = false
  end

end
