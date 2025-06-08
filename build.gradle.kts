plugins {
    `java-library`
    id("com.gradleup.shadow") version "9.0.0-beta13"
}

repositories {
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()

    flatDir {
        dirs("lib")
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.13.1")

    api("org.slf4j:slf4j-api:2.1.0-alpha1") 

    testImplementation("ch.qos.logback:logback-classic:1.5.18")
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.1") 
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.12.1")
}

group = "com.kibo.pgar.lib"
version = "1.6.0"
description = "kibo-pgar-lib"
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}

tasks.register<JavaExec>("run") {
    classpath = configurations.runtimeClasspath.get()
    mainClass.set("TypeSafeEmpty")
    // args = listOf("arg1", "arg2") // if you need arguments
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<AbstractArchiveTask>().configureEach {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}

val testJar by tasks.registering(Jar::class) {
    manifest {
        attributes["Description"] = "Chi mola mia dura la vince" 
        attributes["Manifest-Version"] = "1.1.0"
        attributes["Multi-Release"] = "false"
        attributes["reproducibleFileOrder"] = "true"
        attributes["preserveFileTimestamps"] = "false"
        attributes["source"] = "true"

    }
}

tasks.shadowJar {
    archiveClassifier = "shadowjar"

    from(sourceSets.main.get().allSource)

    manifest.inheritFrom(testJar.get().manifest)

    dependencies {
        exclude(dependency("org.junit.jupiter:junit-jupiter:5.12.1"))
        exclude(dependency("org.junit.platform:junit-platform-launcher:1.12.1"))
        exclude(dependency("ch.qos.logback:logback-classic:1.5.18"))
        exclude(dependency("org.slf4j:slf4j-api:2.0.17"))
        include(dependency("com.google.code.gson:gson:2.13.1"))

        configurations = provider { listOf(project.configurations.runtimeClasspath.get()) }
    }

    minimize()
}
