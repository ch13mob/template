# Template

Template for a new project.

## Getting Started

- [x] Create a new project on Github from this template
- [x] Rename the app package `com.ch13mob.template` to your app package
- [x] Rename `rootProject.name = "template"` in the `settings.gradle.kts` to your app name
- [x] Rename `namespace = "com.ch13mob.template"` in the `app/build.gradle.kts` to your app package
- [x] Rename `applicationId "com.ch13mob.template"` in the `app/build.gradle.kts` to your app package
- [x] Rename `TemplateTheme` in the `..core/designsystem/theme/Theme.kt` to your app name
- [x] On `string.xml`, set your application name
- [x] Update the `README.md` file
- [x] Clean and Rebuild the project

## [Detekt](https://github.com/detekt/detekt)
Auto correct code:

```shell
./gradlew detekt --auto-correct
```