# Trident

[![build](https://github.com/ClubObsidian/wrappy/workflows/build/badge.svg)](https://github.com/ClubObsidian/trident/actions?query=workflow%3Atest)
[![build artifacts](https://jitpack.io/v/clubobsidian/trident.svg)](https://jitpack.io/#clubobsidian/trident)
[![codecov](https://codecov.io/gh/ClubObsidian/trident/branch/master/graph/badge.svg)](https://codecov.io/gh/ClubObsidian/trident)
[![Known Vulnerabilities](https://snyk.io//test/github/ClubObsidian/trident/badge.svg?targetFile=build.gradle)](https://snyk.io//test/github/ClubObsidian/trident?targetFile=build.gradle)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Discord](https://img.shields.io/discord/482823104905609248.svg?logo=discord)](https://discord.gg/EY5Tq6r)
[![javadocs](https://img.shields.io/badge/Javadocs-3.0.0-success.svg)](https://jitpack.io/com/github/clubobsidian/trident/3.0.0/javadoc/)

A dead simpile annotation-based event bus that allows different event executor implementations.

Trident implements reflection and generated Javassist method executors.

## Inspiration

Inspiration for Trident.

* [Event4j](https://github.com/Techcable/Event4J)
* [Guava](https://github.com/google/guava)
* [mbassador](https://github.com/bennidi/mbassador)

## Build Artifacts

Build artifacts are hosted via [Jitpack.](https://jitpack.io/#clubobsidian/Trident/)

## Setting up as a dependency

### Gradle

``` groovy
repositories {
	maven { url 'https://jitpack.io' }
}

compile 'com.github.clubobsidian:trident:3.0.0'
```

### Maven

``` xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.github.clubobsidian</groupId>
	<artifactId>trident</artifactId>
	<version>3.0.0</version>
</dependency>
```

## Dependencies

* [Javassist](https://github.com/jboss-javassist/javassist)

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

### Projects that use Trident

* [DynamicGui](https://github.com/ClubObsidian/DynamicGui)
* [Hydra](https://github.com/ClubObsidian/hydra)

Do you use Trident in your project? If so make a pull request and add your repository to the list!
