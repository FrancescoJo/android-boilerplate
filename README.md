# Deprecated

Since the author abandoned Android app development to against to Google's anti-free policy [Prevent APK sideloading](https://9to5google.com/2025/08/25/android-apps-developer-verification/), this repository is no longer maintained.

# AwesomeApp

An Android boilerplate project setup for Rapid Application Development. If you are interested,
please read [pros-and-cons](#pros-and-cons) before start.

## Project structure

| Module name     | Description                                                                                       |
| :---            | :---                                                                                              |
| app-main        | Contains UI and UI manipulation logic. Directly depends on `app-core`, `app-lib`, `lib-xxx`, etc. |
| app-core        | Business logic layer, abstracts business logic and `DataSource`.                                  |
| app-lib         | Library logic layer, independent to business logic                                                |
| lib-xxx         | Various 3rd party libraries and SDKs                                                              |

## Component design

Tried to follow the [Clean architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
as best as possible. Project structure follows as below:

```
    View (:app-main)
      |
  ViewModel (:app-main)
      |
   UseCase (:app-core)
      |
  Repository (:app-core)
      |
  DataSource (:app-core)
```

All DataSources and DTOs must be scoped with `internal` visibility for encapsulation.

## Roles and responsibilities for components:

 * View: Takes and refines user input, displays internal presentation as recognisible(View, Sound, Vibration, etc)
   output.

 * ViewModel: Acts as data provider for requests from view. It asks Use cases to prepare required data for user
   input, and publishes it to an established channel between View and ViewModel. In this project, RxJava acts as
   such data channel. A composition of various UseCases.

 * UseCase: A common business logic provider for ViewModels - which is a composition of various Repositories.
   This component takes two key responsibilities:

   1. Combines various data provided by multiple repositories
   2. Converts data from repositories(DTO) to *domain model*.

   In this example project, all domain models are exposed as kotlin `interface`s.

 * Repository: Data provider for UseCases. A composition of various DataSources. DataSources could be
   Network, SharedPreferences, Database, Filesystem, etc.

## Code quality management
This project is running plugins for code quality management.

[Android lint](https://developer.android.com/studio/write/lint)
[Detekt + ktLint](https://github.com/arturbosch/detekt)

## Pros and cons

This project configuration has several pros and cons. Basically this complexity for a simple
image list download and display app is an overkill indeed. This project is intended to be used 
at the beginning stage of enterprise level project - which usually be more than 3 developers are
working together.

### Pros

1. TESTABILITY
2. MODULARITY

### Cons

1. REQUIRES DEEP KNOWLEDGE ON GRADLE
2. LOTS OF FILES TO MAINTAIN
3. DIFFICULT TO UNDERSTAND PROJECT STRUCTURE AT A FIRST GLANCE
