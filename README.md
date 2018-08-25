![trident](/img/trident_logo.png)

[![Build Status](https://api.travis-ci.org/ClubObsidian/trident.svg?branch=master)](https://travis-ci.org/ClubObsidian/trident)
[![build artifacts](https://jitpack.io/v/clubobsidian/trident.svg)](https://jitpack.io/#clubobsidian/Trident)
[![codecov](https://codecov.io/gh/ClubObsidian/Trident/branch/master/graph/badge.svg)](https://codecov.io/gh/ClubObsidian/trident)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[Javadocs](https://jitpack.io/com/github/clubobsidian/trident/1.0.8/javadoc/)

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

compile 'com.github.clubobsidian:trident:1.0.8'
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
	<artifactId>trident</artifactId>
	<version>1.0.8</version>
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
`gradlew shadowJar`