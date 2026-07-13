# Contributing to wakeharder

Thanks for considering a contribution.

## Reporting bugs

Open an issue with:

- Android version and device (or emulator config)
- Steps to reproduce
- What you expected vs. what happened

## Proposing features

Open a Discussion first for anything beyond a small fix, so we can agree on scope before you put in the work.

## Development setup

- Kotlin, Jetpack Compose, minSdk 29
- Android SDK cmdline-tools + JDK 17 (no Android Studio required, but it works fine too)
- Build: `./gradlew assembleDebug`

## Pull requests

- Keep PRs focused: one logical change per PR. Cosmetic/styling changes and behavioral changes should be separate PRs.
- Make sure `./gradlew assembleDebug` builds clean before opening the PR.
- Describe the change and why, not just what.

## License

By contributing, you agree your contribution is licensed under this repo's MIT license.
