# Bluetooth Permission Issue Summary

## Issue
The `MainActivity.kt` file uses `Manifest.permission.BLUETOOTH_SCAN` which requires API level 31, but the minimum SDK version is 30. This causes a lint warning.

## Plan
1.  Add the `@SuppressLint("InlinedApi")` annotation to the `MainActivity.kt` file to suppress the lint warning.
2.  Run the `check` command to verify that the project compiles successfully.
3.  Merge the changes into the `main` branch.