# MagicLib
Simple and useful library for Spigot/Paper.

## Building
You can build project with [Gradle](https://gradle.org/) simply running `build` task via [gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html).

## Using
You can add this library and use it into your project like this (copy+paste to [build.gradle](https://docs.gradle.org/current/samples/sample_building_java_libraries.html)):
```gradle
repositories {
  maven {
    url 'https://msweet.xyz/ivy/maven3'
  }
}

dependencies {
  // Replace {VERSION} with latest version available. You can find latest stable version in releases section or unstable one by looking into build.gradle file within this repo
  compile 'com.magicsweet:MagicLib:{VERSION}'
}
```
