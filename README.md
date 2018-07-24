# Trident 
[![Build Status](https://api.travis-ci.org/ClubObsidian/Trident.svg?branch=master)](https://travis-ci.org/ClubObsidian/Trident)
[![](https://jitpack.io/v/clubobsidian/Trident.svg)](https://jitpack.io/#clubobsidian/Trident)
[![codecov](https://codecov.io/gh/ClubObsidian/Trident/branch/master/graph/badge.svg)](https://codecov.io/gh/ClubObsidian/Trident)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A dead simpile annotation event system that allows different event executor implementations.

Trident implements reflection and generated Javassist method executors.

## Build Artifacts

Build artifacts are hosted via [Jitpack.](https://jitpack.io/#clubobsidian/Trident/)

## Setting up as a dependency

### Gradle

```
repositories {
	maven { url 'https://jitpack.io' }
}

compile 'com.github.clubobsidian:Trident:1.0.2'
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
	<version>1.0.2</version>
</dependency>
```

## Dependencies
* JavaAssist

## Development

### Eclipse

1. Git clone the project
2. Generate eclipse files with `gradlew eclipse`
3. Import project

### Intellij

1. Git clone the project
2. Generate intellij files with `gradlew idea`
3. Import project

### Building
gradlew shadowJar
