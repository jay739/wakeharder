# wakeharder

Open-source Android alarm app: the alarm only disarms after you complete a task, either solving a math problem or scanning a pre-registered barcode/QR code.

Built with Kotlin and Jetpack Compose. minSdk 29 (Android 10+). No ads, no account, no network use.

## Status

Early development. The alarm core (Stage 1) is working and verified on a real device; dismiss tasks are next. See the build plan below.

## Install

### From releases

Each tagged release publishes a debug-signed APK on the [releases page](https://github.com/jay739/wakeharder/releases). Sideload it: copy to the phone, allow "install unknown apps" for your file manager, open the APK. Debug-signed builds are for testing; a store-ready signing setup is planned.

### Build from source

```bash
git clone https://github.com/jay739/wakeharder && cd wakeharder
./gradlew assembleDebug
# APK lands in app/build/outputs/apk/debug/
```

Or open the project in Android Studio and run it on a device.

## Permissions it asks for

| Permission               | Why                                                     |
| ------------------------ | ------------------------------------------------------- |
| `SCHEDULE_EXACT_ALARM`   | Fire at the exact minute, not when the OS feels like it |
| `RECEIVE_BOOT_COMPLETED` | Re-arm alarms after a reboot                            |
| `POST_NOTIFICATIONS`     | The ring notification and full-screen intent            |
| `FOREGROUND_SERVICE`     | Keep ringing reliably while the ring screen is up       |

## Build plan

- Stage 0: scaffold. Compose app, Gradle setup, package skeleton. (done)
- Stage 1: alarm core. Set alarm, fires via `AlarmManager`, full-screen ring activity, plain stop button, survives reboot. (done, device-verified)
- Stage 2: math dismiss task. Ring screen routes to a math challenge instead of a stop button.
- Stage 3: barcode/QR dismiss task. Register a target barcode; ring screen offers camera scan.
- Stage 4: reliability hardening. Battery-optimization exemption, watchdog re-arm, capped snooze.
- Stage 5: polish. Alarm list UI, repeat days, volume-ramp ringtone, settings.

## Tests

```bash
./gradlew test
```

Unit tests cover the alarm scheduling math (next-trigger computation with an injectable clock). CI runs them plus a debug build on every push and PR.

## Contributing and security

See [CONTRIBUTING.md](CONTRIBUTING.md). Report vulnerabilities privately per [SECURITY.md](SECURITY.md).

## License

MIT, see [LICENSE](LICENSE).
