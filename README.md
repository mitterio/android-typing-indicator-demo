# Typing Indicator Demo App for Android
This is a demo app showing how to implement the popular typing indicator feature on a simple chat app using Mitter.io's Stream Data.

## Before you start

Navigate to this project and follow the instructions to get a local server running before you try to run this app.

* [Server for Running Typing Indicator Demo](https://github.com/mitterio/server-typing-indicator-demo)

## How to setup?

Get a copy of this project by doing a git clone:

```
git clone https://github.com/mitterio/android-typing-indicator-demo.git
```

After that, open your project using Android Studio and make following changes:

* **Add your application ID**. Open `App.kt` and replace the existing application ID with your own application ID.
* **Add a channel ID**. Open `MainActivity.kt` and replace the existing channel ID with the channel ID that you received after running the local server (as mentioned in the previous section).

## How to run?

Run this project using Android Studio on **2** or more devices/emulators and choose a user to login from the first screen that pops up.

Type messages in one emulator see the typing indicator pop up the other other emulator chat window.

## More details

This project is a part of the Typing Indicator recipe on Mitter.io's website. Visit the following link to learn more about this implementation:

* Recipe - [link here]
