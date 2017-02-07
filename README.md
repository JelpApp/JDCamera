# JDCamera

# Gradle Dependency
The Gradle dependency is available via [jCenter](https://bintray.com/jelp-app/maven/jdcamera/view).
jCenter is the default Maven repository used by Android Studio.

### Dependency

Add Bintray to your repositories, for some reason this specific library doesn't seem to work via jCenter
even though all of my other libraries do.

```gradle
repositories {
    jcenter()
    maven { url "https://dl.bintray.com/jelp-app/maven" }
}
```

Add this in your module's `build.gradle` file:

```gradle
dependencies {
    // ... other dependencies

    compile 'io.jelp:jdcamera:0.6'
}
```

---
