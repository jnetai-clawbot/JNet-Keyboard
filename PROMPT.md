J~Net Keyboard

Android keybaord with unicode compatible fonts

Project location /home/jay/Documents/Scripts/AI/OpenCode/Android-Keyboard/

Must work with both old and new android versions (for samsung s8 and google pixel 7+)
a complete keyboard replacement that can be used in all android apps as a full keyboard replacement to translate text to and from english (or other languages defined in settings) , insert emojis and symbols, and set a current font for if i chat in an app it auto translates (if enabled in settings) auto converts to desired current font / typeface (defined / selected from a list of all unicode compatible fonts in settings), have all numbers on top line of the keyboard, all standard english (uk) letters, (in settings options can be set to US style aswell as other common keyboard layouts and settings)
must have a settings icon (cog) i can toggle on and off settings, select language to auto convert to and from (using text input of chat if available or its own if not available)

must be dark themed by default (but allow light mode in settings)
put about section in the settings page for when settings cog is pressed (at the bottom) aswell as check for update available (latest github check)
it will need internet permissions and all other permissions to work so should be accepted upon 1st use.


Use github workflows to build the app and put finally release in apk folder in the project location

Dont edit this file

Never change anything in Backup folders (if it exists) but you can use them as a read-only reference if a mistake is made and you need to fix something

Save changes to file(s) in question

Then after files are added / edited then save any changes made to changes.txt

Implement persistent error handling and debugging throughout the project. Every failure, exception, or unexpected state should generate a clear error code, detailed debug output, and useful diagnostic information to help identify the exact cause quickly.

Do not remove debugging systems after issues are fixed — keep all error codes, logging, stack traces, validation checks, and diagnostic tools permanently integrated so that any future bugs, crashes, or unexpected behaviour can be traced and resolved efficiently.

Always use same key-store for each app made via github workflows so it can update correctly without requiring uninstallation

Save changes to changes.txt (create if not exists)

Tell me when ready to test (stay quiet after acknowledging you got the message / request / mission every time and stay quiet till its ready to test and respond only if fully complete  or if you need input from me or if I ask for an update)!

When giving final github release link (where applicable), make sure it points to the newest release but without the tag or filename so I can see the correct location without direct downloading the file as thats best practice!

Each app needs an About section showing
In about section it should say Made by jnetai.com 
The full version number (same as github release version tag) also add a Check for update button (so internet permissions required) to check latest release version (tag in full)
Add a Share App button so users can share the app.
 
Each update should use same key store so the app can update and not require uninstall of the app to update it.

Each app should have its own local folder and own github repository and own keystore that remains the same so it can update without uninstall 1st and be dark centered themed and allow space at bottom so buttons or elements at the bottom of the app should not be cut off, it should look professional.

App compatibility: apps needs to work on samsung s8 and onwards and google pixel 6 and onwards
full path to Downloads is /storage/emulated/0/Download/ (called Downloads as an alias in android)

In releases on github a meaningful name should be used for example Tetris.apk (no need for a debug version of any app or game for android just put the debug version as the main version!

Github api tokens / passwords etc can be found in /home/jay/Documents/Scripts/AI/openclaw/password-vault/

Build the releases via github actions / workflows (not locally) in there own repository (1 per app)
start now with all in order no questions asked!


