# Template

Template app to kickstart development of a new Android app. It
uses [JSONPlaceholder API](https://jsonplaceholder.typicode.com/) to fetch data and has a common set
of features that most apps have.

## Getting started

1. Click on the `Use this template` button at the top right of the repository to create a new
   repository based on this template.
2. Edit the `renameConfig` section in the `buildscripts/setup.gradle` file to use your
   own `newProjectName`, `newRootProjectName`, `newMaterialThemeName`, `newApplicationId`
   and `newDeeplinkHost` values.
3. Run the `./gradlew renameTemplate` command to rename all the files and replace the template
   values with
   the ones you specified in the previous step.
4. Run the `./gradlew clean` command to clean the project.
5. Click on the `Sync Project with Gradle Files` button in Android Studio.

## Features

- Login screen with email and password field validation
- Posts screen with a list of posts
- Post details screen
- Splash screen
- Deeplink setup, including checking the user auth state

  <details>
     
  <summary>Demo</summary>
  
  Deeplink | User isn't logged in
  
  <video src="https://github.com/ch13mob/template/assets/15021133/4728b522-bd3f-48c9-8bc0-05d7d811a7f7" controls="controls" style="max-width: 730px;"></video>
  

  Deeplink | User is logged in

  <video src="https://github.com/ch13mob/template/assets/15021133/0558a256-6e2e-4377-be6a-83353967df47" controls="controls" style="max-width: 730px;"></video>
  
</details>

## Architecture

The **Template** app follows
the [official architecture guidance](https://developer.android.com/topic/architecture)

## Project structure

The project is structured in the following way:

- `app` module contains the base template app
- `sample` module contains examples of common features used in Android apps

## Main libraries used in this project

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Preferences DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
- [Retrofit](https://github.com/square/retrofit)
- [Coil](https://coil-kt.github.io/coil/compose/)
- [Detekt](https://github.com/detekt/detekt)

## UI

The app was designed using [Material 3 guidelines](https://m3.material.io/).

<details>
  <summary>Screenshots</summary>

  <img src="https://github.com/ch13mob/template/assets/15021133/72ff23f8-8c6c-459e-a787-275bdacdc5d0" width="250">|<img src="https://github.com/ch13mob/template/assets/15021133/88a65572-92b8-4db7-83cf-81431c7abe72" width="250">
  <img src="https://github.com/ch13mob/template/assets/15021133/5d01b2ff-ab19-40d2-b9b9-127b767ab8c1" width="250">|<img src="https://github.com/ch13mob/template/assets/15021133/f74683ca-acca-4dea-875e-a3e8581cac8f" width="250">
  <img src="https://github.com/ch13mob/template/assets/15021133/34fb16db-c55f-4e87-99e5-91e7276b5ac4" width="250">|<img src="https://github.com/ch13mob/template/assets/15021133/f303da6c-3600-45d7-b92c-090886b78530" width="250">
  
</details>

## TODO

- [ ] Tests
- [ ] CI/CD
- [ ] Documentation
- [ ] Add more samples

## Inspired by

[NowInAndroid](https://github.com/android/nowinandroid)

[AndroidAppTemplate](https://github.com/AdamMc331/AndroidAppTemplate)

## Contributing

Contributions are welcome! Feel free to submit a PR or open an issue if you have any questions or
find a bug.

## License

**Template** is distributed under the terms of the Apache License (Version 2.0). See the
[license](LICENSE) for more information.
