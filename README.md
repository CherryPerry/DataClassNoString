# DataClassNoString

[![Download](https://api.bintray.com/packages/cherryperry/maven/DataClassNoString/images/download.svg)](https://bintray.com/cherryperry/maven/DataClassNoString/_latestVersion)
[![License](https://img.shields.io/badge/Licence-MIT-blue)](https://github.com/badoo/Reaktive/blob/master/LICENSE)
[![Build Status](https://github.com/CherryPerry/DataClassNoString/workflows/Build/badge.svg?branch=master)](https://github.com/CherryPerry/DataClassNoString/actions)

Kotlin compiler plugin, that removes `toString()`, `equals(any: Any)` and `hashCode()` functions from your data classes.

## Motivation

Kotlin is cool and data class are cool. But sometimes we overuse them in places where we actually do not need to. So the
question is what is a price of using data classes when we actually do not need them? To answer this question I decide to
explore edge case situation when we consider all data classes in a project as useless and replaceable with simple
classes. After applying this compiler plugin we should see the price (APK size) which we pay for using data classes.

## Usage

```groovy
buildscript {
    repositories {
        maven { url 'https://dl.bintray.com/cherryperry/maven' }
    }
    dependencies {
        classpath 'com.cherryperry.nostrings:gradle-plugin:VERSION'
    }
}

repositories {
    maven { url 'https://dl.bintray.com/cherryperry/maven' }
}

apply plugin: 'com.cherryperry.nostrings'

dataClassNoString {
    enabled = true / false
    removeAll = true / false // Remove everything or toString() only
}
```

Compile your app and compare the results by using [diffuse](https://github.com/JakeWharton/diffuse).

You should see the difference in dex size, method and string count.
