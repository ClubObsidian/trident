# Trident 
[![Build Status](https://api.travis-ci.org/ClubObsidian/Trident.svg?branch=master)](https://travis-ci.org/ClubObsidian/Trident)
[![](https://jitpack.io/v/clubobsidian/Trident.svg)](https://jitpack.io/#clubobsidian/Trident)
[![codecov](https://codecov.io/gh/ClubObsidian/Trident/branch/master/graph/badge.svg)](https://codecov.io/gh/ClubObsidian/Trident)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A dead simpile annotation event system that allows different event executor implementations.

The current implementation of Trident used JavaAssist to generate code on runtime for method executors.

# Build Artifacts

Build artifacts are hosted via [Jitpack.](https://jitpack.io/#clubobsidian/Trident/)

## Setting up as a dependency

### Gradle

```
repositories {
	maven { url 'https://jitpack.io' }
}

compile 'com.github.clubobsidian:Trident:1.0.0'

```

### Maven

```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.github.clubobsidian</groupId>
	<artifactId>Trident</artifactId>
	<version>1.0.0</version>
</dependency>
```

# Building
./gradlew shadowJar

# Dependencies
* JavaAssist
