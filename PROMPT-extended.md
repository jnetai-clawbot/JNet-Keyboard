App Specification: J~Net Keyboard — Android Unicode Keyboard & Translation IME

1. Project Overview
App Name: J~Net Keyboard
Branding: Made by jnetai.com

Project Location: /home/jay/Documents/Scripts/AI/OpenCode/Android-Keyboard/

Repository: This app must have its own dedicated GitHub repository.

APK Output: Final workflow-built APK must be placed/copied into:
/home/jay/Documents/Scripts/AI/OpenCode/Android-Keyboard/apk/
Release APK Filename: J~Net-Keyboard.apk
Do not create separate release/debug APK variants for the user. The main APK is the installable release build.

The application must be a complete Android keyboard replacement, not a normal application that merely provides a text input screen.

2. Android IME Architecture
2.1 Native Keyboard

Implement J~Net Keyboard as a proper Android Input Method Editor (IME) using InputMethodService.

It must:

Appear in Android's installed keyboard list.
Be selectable from Android keyboard settings.
Be capable of becoming the user's default keyboard.
Work as a replacement for the standard Android keyboard.
Work across normal Android applications without requiring those applications to be modified.
Correctly communicate with applications through Android's InputConnection API.

The keyboard must properly support:

commitText()
composing text
cursor positioning
selection
deletion
replacement of selected text
backspace
enter/return
multiline text fields
EditorInfo
different input types
switching between applications
switching between text fields
opening/closing the keyboard
keyboard process restarts
screen rotation
portrait/landscape
configuration changes
switching to another keyboard and back

Do not use Accessibility Services, overlays, floating windows, or other workarounds to implement the keyboard.

3. Android Compatibility

The keyboard must support:

Samsung Galaxy S8 and newer
Google Pixel 6 and newer
Pixel 7 and later
Modern Android versions including Android 17

Target the oldest Android version required for the Samsung Galaxy S8 while maintaining a modern target SDK.

Use compatibility fallbacks where necessary.

Avoid unnecessary deprecated APIs, but where an older API is required for compatibility, provide an appropriate fallback.

The keyboard must remain functional across the supported Android range rather than only being designed for the newest Android version.

4. Unicode Font / Style System
4.1 Important: These Are Unicode Transformations

J~Net Keyboard must NOT attempt to install fonts into Android and must NOT depend on the receiving application having a particular font installed.

"Fonts" in this application mean Unicode character styles.

For example:

hello

may become:

𝒉𝒆𝒍𝒍𝒐
(add all available unicode compatible fonts like that example dont forget any)
keep the most used at the top of the list of fonts in settings font selection

The receiving application receives the actual Unicode characters.

Therefore:

The transformed text must work when pasted into other applications.
The receiving application does not need J~Net Keyboard installed.
The receiving application does not need the font installed.
Do not use PUA/custom characters.
Do not fake Unicode fonts using images.
Do not rely on HTML/CSS styling in external applications.
4.2 Unicode Mapping Database

Maintain a local Unicode transformation database.

Each style must contain:

unique internal ID
display name
preview
Unicode mappings
supported characters
category
favourite status

The system must:

Transform supported characters.
Leave unsupported characters unchanged.
Preserve spaces.
Preserve punctuation when no valid transformation exists.
Preserve emojis.
Preserve characters outside the selected mapping.
Never corrupt surrogate pairs or Unicode code points.
Correctly handle Unicode supplementary characters.
Correctly handle numbers where a Unicode equivalent exists.
Correctly handle uppercase/lowercase mappings where available.

Never claim a style is supported if it does not have valid Unicode mappings.

5. Font Selection

The user must be able to select a current Unicode style.

The font selector must provide:

Search
Categories
Alphabetical sorting
Favourites
Preview
Current/active style indicator
Test text
Clear selection
A sensible default style

The preview must show the actual Unicode output, not merely render ordinary text using a different Android typeface.

The selected style must persist across:

keyboard restarts
application changes
device restarts
Android process restarts

6. Keyboard Layout
6.1 Default Layout

Default keyboard layout must be UK QWERTY.

It must contain:

Dedicated number row at the top
QWERTY letter keys
Shift
Backspace
Enter/Return
Space
Comma
Period
Emoji
Symbols
Language/Translation
Font selector
Settings cog

The number row must remain visible above the letters.

6.2 Alternative Layouts

Settings must allow the user to select:

UK QWERTY
US QWERTY
Other sensible/common layouts where practical

The layout system must be modular so additional layouts can be added later without rewriting the entire keyboard.

7. Keyboard Behaviour
7.1 Normal Typing

Normal typing must:

Receive the typed character.
Apply the selected Unicode style if enabled.
Apply translation if enabled.
Insert the resulting text through InputConnection.

The keyboard must not modify text unexpectedly.

7.2 Translation and Font Processing Order

When both automatic translation and Unicode styling are enabled, the processing pipeline must be:

Typed text → Translation → Unicode transformation → commitText()

Do not translate already-transformed Unicode mathematical characters.

Translation must operate on the user's readable source text.

8. Translation System
8.1 Translation Features

Settings must provide:

Translation ON/OFF
Source language
Auto-detect source language
Destination language
Manual translation button
Automatic translation mode
Configurable translation provider/API

The translation provider must be implemented through a clean abstraction so it can be changed without rewriting the keyboard.

Support configurable APIs such as:

LibreTranslate
Google Translate API
Other compatible translation providers

Do not hard-code a single provider throughout the application.

8.2 Automatic Translation

When enabled, the keyboard should automatically translate text before inserting it where technically practical.

Translation must not cause noticeable corruption or loss of user input.

If translation fails:

Preserve the original text.
Do not silently discard input.
Display a compact status/error message inside the keyboard.
Do not use intrusive Android alert dialogs.
8.3 Manual Translation

Provide a dedicated translation button allowing the user to translate the current text/input where possible.

The keyboard must handle:

empty input
selected text
current composing text
multiline text
translation failures
network failures
API errors
invalid responses
timeouts

9. Secure Input & Privacy

This is a critical requirement.

The keyboard must identify secure input fields using EditorInfo / Android input-type information.

For:

passwords
PINs
secure numeric fields
authentication fields
other explicitly secure input types

the keyboard must:

Disable automatic translation.
Disable automatic Unicode transformation.
Never send the input to a translation service.
Never persist the input.
Never log the input.
Never include the input in diagnostics.

The keyboard must behave as a normal secure keyboard in these fields.

9.1 No Keystroke Logging

Never permanently store:

typed text
passwords
PINs
messages
clipboard contents
translation input
translation output

Debug logs must contain diagnostic information but must never contain the user's actual typed content.

If diagnostic information requires representing text state, use safe metadata such as:

character count
input type
field type
operation name
error code

rather than the actual content.

10. Clipboard Compatibility

Unicode-styled text must be actual Unicode text.

Copy/paste must work between:

J~Net Keyboard
Android system clipboard
messaging apps
browsers
social media apps
notes applications
other keyboards

Do not rely on custom clipboard formats that require J~Net Keyboard.

11. Emoji & Symbols
11.1 Emoji

Insert standard Unicode emoji code points.

Do not convert emojis into images.

Emoji must remain unchanged when Unicode font transformation is applied unless there is a genuine valid Unicode transformation.
allo searching of emojis (like other keyboards have for finding emojis by words)

11.2 Symbols

Provide a dedicated symbols keyboard/page containing useful:

punctuation
mathematical symbols
currency symbols
arrows
brackets
common special characters
Unicode symbols

The symbols system should be searchable or categorised where practical.

12. Long-Press Behaviour

Implement sensible long-press behaviour.

At minimum:

Letter long-press → accented/alternative characters.
Backspace long-press → accelerated deletion.
Space long-press → cursor movement where supported.
Number/symbol long-press → secondary characters where appropriate.

Long-press behaviour must not interfere with normal key presses.

13. Settings

The keyboard must have a settings cog accessible directly from the keyboard.

Settings must include:

General
Dark/Light/System theme
UK/US keyboard layout
Number-row preference if applicable
Haptic feedback
Key sound if applicable
Keyboard behaviour preferences
Unicode Styles
Current style
Enable/disable automatic Unicode transformation
Font/style selector
Favourites
Preview
Translation
Enable/disable automatic translation
Source language
Auto-detect
Destination language
Translation provider
API configuration where required
Appearance
Dark mode
Light mode
Key size
Keyboard height where practical
Compact/standard layout where practical

Settings must persist using appropriate Android persistent storage.

14. Default Appearance

The default theme must be a professional dark theme.

The interface should be:

modern
compact
visually clean
centred
easy to use
high contrast
consistent
touch-friendly

Allow Light Mode and System/Automatic theme in settings.

Bottom controls must always account for:

navigation bars
gesture navigation
display cutouts
different screen sizes

No buttons or controls may be cut off.

15. Status & Error UI

Do not use intrusive AlertDialog popups for normal errors.

Use an unobtrusive status area within the keyboard/settings UI.

Examples:

Translation unavailable
Network connection required
Update check failed
Invalid translation response
Unicode style unavailable

Errors must have internal error codes.

Example format:

JNK-TR-001

The exact numbering scheme should be consistent throughout the application.

16. Persistent Diagnostics & Debugging

Implement comprehensive persistent diagnostics throughout the application.

Every unexpected failure, exception, validation failure, API failure, configuration problem, or unsupported state must generate:

unique error code
timestamp
component
operation
exception type where applicable
stack trace where applicable
useful diagnostic metadata
Android/API information where relevant

Do not log private user input.

Debugging infrastructure must remain in the final application/codebase.

Do not remove diagnostics simply because a particular bug has been fixed.

Diagnostic systems should make future problems easy to reproduce and identify.

Where appropriate, provide a developer/debug diagnostic screen in settings that can show safe diagnostic information.

17. Internet & Permissions

Use the minimum permissions required.

At minimum, internet access may be required for:

translation
GitHub update checks

Do NOT request unnecessary permissions such as:

contacts
location
microphone
camera
storage
accessibility
overlay
SMS
phone
notification access

unless a future feature genuinely requires one.

Do not attempt to bypass Android permission/security systems.

The keyboard's core functionality must work without internet.

Internet failures must never prevent the user from using the basic keyboard.

18. Update Checking

The About section must contain a Check for Updates button.

It must:

Query the GitHub API.
Retrieve the latest release.
Retrieve the full release tag.
Compare it with the installed version.
Clearly indicate whether an update is available.
Provide the latest release page when an update exists.

The installed application's version must match the GitHub release tag.

Do not rely on an abbreviated version number in the UI.

19. About Section

At the bottom of Settings, include an About section.

It must display:

Made by jnetai.com

Also display:

Full application version
GitHub release version/tag
Check for Updates button
Share App button

The Share App button must use Android's native share sheet.

The shared link should point to the appropriate GitHub release/project location rather than directly forcing an APK download.

20. Android Navigation & UI Compatibility

The application must correctly handle:

Android navigation bar
gesture navigation
display cutouts
different aspect ratios
small screens
large screens
portrait
landscape
Android system font scaling
dark mode
light mode

Never position essential controls using hard-coded screen coordinates.

Use proper Android layouts, density-independent dimensions, window insets, and responsive layouts.

21. Testing Requirements

Before declaring the project ready for testing, verify the keyboard against the supported Android range.

At minimum test:

Devices
Samsung Galaxy S8
Google Pixel 6
Google Pixel 7 or newer
Applications/Input Fields
Chrome/browser address/search fields
standard Android text fields
multiline text fields
messaging applications
social media applications
email applications
search boxes
normal login fields
password fields
PIN/secure fields

Verify:

normal typing
backspace
selection
cursor movement
copy/paste
emojis
symbols
Unicode styles
translation
switching applications
keyboard reopening
rotation
landscape
dark/light mode
settings persistence
update checking
sharing
offline operation

Secure fields must specifically be verified to ensure translation and Unicode transformation are disabled.

Other things to do:
allow keyboard remapping in settings (for if i want to move / swap / change a button) 
allow save / load to and from chat / text input box (like a clipboard but for the keyboard app itself i can use across apps that dont effect the clipboard

22. GitHub Repository & Workflow

The project must have its own dedicated GitHub repository.

All Android releases must be built using GitHub Actions.

Do not use a local Gradle build as the official release build.

The workflow must:

Check out the repository.
Set up the correct Java/Android build environment.
Restore/build dependencies.
Build the signed APK.
Run appropriate validation/tests.
Create the GitHub release.
Upload J~Net-Keyboard.apk.
Ensure the release artifact uses the permanent signing key.

Copy the final APK to the local project's /apk/ folder where the workflow/environment permits.

The GitHub repository must contain the workflow configuration required to reproduce future releases.

23. Permanent Application Signing

The application must use one permanent keystore for the lifetime of the application.

Every future release must use the same:

keystore
key alias
signing identity

This is mandatory because Android updates require the new APK to be signed by the same signing key.

Never:

generate a new keystore for a later version
overwrite the existing keystore
commit the keystore to GitHub
commit keystore passwords to GitHub
expose signing credentials in logs

Use GitHub Actions Secrets for signing credentials.

If the signing credentials already exist in:

/home/jay/Documents/Scripts/AI/openclaw/password-vault/

they may be used as the source when configuring the project/workflow, but credentials must never be copied into source files or committed to GitHub.

24. Versioning

Use a proper Android versioning system.

versionName must exactly match the GitHub release tag.
versionCode must increase for every release.
Never reuse a versionCode.
GitHub release tags must be meaningful and consistent.

Example:

v1.0.0

APK:

J~Net-Keyboard.apk

The About page should display:

Version 1.0.0

while retaining the exact GitHub tag internally for update comparison.

25. GitHub Release Link

When reporting the completed release, provide the latest release page, not a direct APK download URL.

Use the equivalent of:

https://github.com/USER/REPO/releases/latest

Do not link directly to:

J~Net-Keyboard.apk

This allows the user to see the current release information and download the appropriate artifact manually.

26. File & Backup Rules
Project Files

Save all changes to the appropriate project files.

changes.txt

Create:

/home/jay/Documents/Scripts/AI/OpenCode/Android-Keyboard/changes.txt

if it does not already exist.

After every implementation session/change, update changes.txt with:

date
version
files added
files modified
files removed, if any
important implementation changes
bug fixes
configuration changes
GitHub workflow changes
Backup Folders

Never modify anything inside Backup folders.

Backup folders may only be used as read-only references if required to recover from a mistake or compare previous working files.

Prompt/Instruction Files

Do not edit the supplied specification/instruction file itself unless explicitly instructed to do so.

27. Android Downloads Path

For Android device-side downloads, the standard shared Downloads directory is:

/storage/emulated/0/Download/

Android may display this to the user simply as Downloads.

Do not hard-code assumptions about the user's device-specific filesystem beyond this standard shared-storage path.

28. Code Quality

Use:

modern Android development practices
clean architecture
modular components
secure networking
proper lifecycle management
proper state persistence
safe Unicode handling
safe API parsing
proper exception handling
thread-safe background operations
no blocking network operations on the main/UI thread

Network operations must never freeze the keyboard.

Translation requests and update checks must run asynchronously.

API responses must be validated before being used.

Never trust external API responses blindly.

29. Important IME Restrictions

The implementation must respect Android's security and IME restrictions.

Do not attempt to:

bypass secure input protections
read another application's private data
access another application's database
use Accessibility Services to spy on input
use overlays to fake keyboard functionality
capture passwords
capture screenshots of other applications
record keystrokes for analytics
circumvent Android keyboard security restrictions

Only operate on text exposed to the IME through the standard Android InputConnection/IME APIs.

30. Completion Requirements

The project is not ready for testing until all of the following are complete:

Android project builds successfully through GitHub Actions.
APK is signed with the permanent keystore.
GitHub release is created successfully.
J~Net-Keyboard.apk is produced.
APK is installable without requiring a debug build.
Keyboard appears in Android's keyboard settings.
Keyboard can be enabled and selected.
Basic typing works.
UK layout works.
Number row works.
Symbols work.
Emoji works.
Unicode transformation works.
Clipboard contains actual Unicode output.
Translation works when configured.
Translation gracefully fails when offline.
Secure fields disable translation/transformation.
Settings persist.
Dark theme works.
Light theme works.
About section works.
Version matches GitHub release tag.
Update checker works.
Share App works.
Diagnostics are permanently implemented.
changes.txt is updated.
No Backup folder has been modified.
No secrets or credentials have been committed.
The same signing key is configured for future updates.
The latest GitHub release page is available.

Do not declare the project ready until the implementation is genuinely complete and the above requirements have been checked.

Begin implementation immediately. Do not ask unnecessary questions. If something genuinely requires user input or an unavailable credential/decision, stop only at that point and request the specific information required. Otherwise, continue through implementation, testing, GitHub Actions configuration, release creation, and final verification.
