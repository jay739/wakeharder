# wakeharder

Open-source Android alarm app: the alarm only disarms after completing a task (a math problem, or scanning a pre-registered barcode/QR code).

Built with Kotlin and Jetpack Compose, minSdk 29.

## Status

Early development. See the staged build plan below.

## Build plan

- **Stage 0 — Scaffold:** empty Compose app, Gradle setup, package skeleton. (done)
- **Stage 1 — Alarm core:** set alarm, fires via `AlarmManager`, full-screen ring activity, plain stop button, survives reboot.
- **Stage 2 — Math dismiss task:** ring screen routes to a math challenge instead of a stop button.
- **Stage 3 — Barcode/QR dismiss task:** register a target barcode, ring screen offers camera scan.
- **Stage 4 — Reliability hardening:** battery-optimization exemption, watchdog re-arm, capped snooze.
- **Stage 5 — Polish:** alarm list UI, repeat days, volume-ramp ringtone, settings.

## License

MIT
