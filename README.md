# Template

Template for a new project.

## Getting Started

- [ ] Create a new project on Github from this template
- [ ] Rename the app package `com.ch13mob.template` to your app package
- [ ] Rename `rootProject.name = "template"` in the `settings.gradle.kts` to your app name
- [ ] Rename `namespace = "com.ch13mob.template"` in the `app/build.gradle.kts` to your app package
- [ ] Rename `applicationId "com.ch13mob.template"` in the `app/build.gradle.kts` to your app package
- [ ] Rename `TemplateTheme` in ..core/designsystem/theme/Theme.kt to your app name
- [ ] On `string.xml`, set your application name
- [ ] Update the `README.md` file
- [ ] Clean and Rebuild the project

## [Detekt](https://github.com/detekt/detekt)
Auto correct code:

`./gradlew detekt --auto-correct`