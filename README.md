Installation:
* Install JDK 8
* Install IntelliJ with Kotlin plugin

Project setup:
* Create project from sources

Deployment setup:
* Set settings to connect to EV3 brick (see build.gradle)
** Password is required. It needs to be set.
* Set main class name in build.gradle
* Run (only once) gradle deployEv3Libs

Running:
* Run gradle deployEv3 (which will trigger the build)
* Run the program on the brick

If you add libraries, also run gradle deployEv3Libs