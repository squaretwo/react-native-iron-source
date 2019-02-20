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
  s.source       = { :git => "https://github.com/squaretwo/react-native-iron-source" }
  s.source_files  = "ios/**/*.{h,m}"
  s.requires_arc = true

  s.dependency "React"
  s.dependency "IronSourceSDK", "6.8.1.0"

end
