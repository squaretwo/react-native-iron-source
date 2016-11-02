
# react-native-iron-source

## Getting started

`$ npm install react-native-iron-source --save`

### Mostly automatic installation

`$ react-native link react-native-iron-source`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-iron-source` and add `RNIronSource.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNIronSource.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNIronSourcePackage;` to the imports at the top of the file
  - Add `new RNIronSourcePackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-iron-source'
  	project(':react-native-iron-source').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-iron-source/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      compile project(':react-native-iron-source')
  	```

#### Windows
[Read it! :D](https://github.com/ReactWindows/react-native)

1. In Visual Studio add the `RNIronSource.sln` in `node_modules/react-native-iron-source/windows/RNIronSource.sln` folder to their solution, reference from their app.
2. Open up your `MainPage.cs` app
  - Add `using Cl.Json.RNIronSource;` to the usings at the top of the file
  - Add `new RNIronSourcePackage()` to the `List<IReactPackage>` returned by the `Packages` method


## Usage
```javascript
import RNIronSource from 'react-native-iron-source';

// TODO: What do with the module?
RNIronSource;
```
  