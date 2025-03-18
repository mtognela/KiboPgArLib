/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "9.0.0-beta10"
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {

    api("org.slf4j:slf4j-api:2.0.17")
    api("com.google.guava:guava:33.4.0-jre")
    runtimeOnly("ch.qos.logback:logback-classic:1.5.17")
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.12.1")
}

group = "com.kibo.pgar.lib"
version = "1.0.0"
description = "kibo-pgar-lib"
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }

    publications.create<MavenPublication>("shadow") {
        from(components["shadow"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

val testJar by tasks.registering(Jar::class) {
    manifest {
        attributes["Description"] = "Don't know what I'm doing" // help me out to use manisfst jar
    }
}

tasks.shadowJar {
    archiveClassifier = ""

    manifest.inheritFrom(testJar.get().manifest)

    dependencies {
        exclude(dependency("org.junit.jupiter:junit-jupiter:5.12.1"))
        exclude(dependency("org.junit.platform:junit-platform-launcher:1.12.1"))

        include(dependency("org.slf4j:slf4j-api:2.0.17"))
        include(dependency("com.google.guava:guava:33.4.0-jre"))

        configurations = provider { listOf(project.configurations.runtimeClasspath.get()) }
    }

    minimize {
        exclude(dependency("org.slf4j:slf4j-api:2.0.17"))
        exclude(dependency("com.google.guava:guava:33.4.0-jre"))
    }

}