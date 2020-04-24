# Dynamic Xposed

## Small Description
When updating an apk that uses the Xposed Api, you need to install the apk, then reboot. However, this should not be
necessary if the apk is loaded dynamically. It should not even be necessary to install the Apk. This is a test to check
if it is possible to load Instaprefs dynamically to update hooks on Instagram without needing to reboot or even install. 

*2 hours later:* **It works.**