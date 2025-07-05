# Gradle Issue Summary

## Issue
The project fails to compile due to unresolved references related to ARCore and other missing classes.

## Plan
1. Add the ARCore dependency to the `app/build.gradle.kts` file.
2. Create the missing `ArCoreManager.kt`, `Routes.kt`, `MainScreen.kt`, and `BuscarHuellaScreen.kt` files.
3. Create the `ParkeadorTheme.kt` file to define the application's theme.
4. Run the `check` command to verify that the project compiles successfully.
5. Merge the changes into the `main` branch.
